package de.pfann.budgetmanager.server.persistens.daos;

import de.pfann.budgetmanager.server.common.util.LogUtil;
import de.pfann.budgetmanager.server.persistens.model.AppUser;
import de.pfann.budgetmanager.server.persistens.model.Entry;
import de.pfann.budgetmanager.server.persistens.model.Tag;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class EntryFacade {


    private EntryDao entryDao;

    private AppUserDao userDao;

    private TagDao tagDao;


    public EntryFacade(){
        entryDao = EntryDao.create();
        userDao = AppUserDao.create();
        tagDao = TagDao.create();
    }

    public Set<Entry> getEntries(AppUser aUser) {
        return new HashSet<>(entryDao.getAllByUser(aUser));
    }

    public Set<Entry> getEntries(Tag aTag){
        return new HashSet<>(entryDao.getAllByTag(aTag));
    }

    public void persistEntry(Entry aEntry) {
        aEntry.setCreated_at(new Date());
        LogUtil.info(this.getClass(),"Persist: " + aEntry.getHash());

        AppUser user = null;
        try {
            user = userDao.getUserByNameOrEmail(aEntry.getAppUser().getName());
        } catch (NoUserFoundException e) {
            e.printStackTrace();
        }

        List<Tag> tagsfromUser = new ArrayList<>(tagDao.getAllByUser(user));
        List<Tag> tagsfromEntity = distinct(aEntry.getTags());
        List<Tag> preparedTagsToSave = new LinkedList<>();

        LogUtil.info(this.getClass(),"TagsfromUser      : " + tagsfromUser.size());
        LogUtil.info(this.getClass(),"TagsfromEntity    : " + tagsfromEntity.size());
        LogUtil.info(this.getClass(),"PreparedTagsToSave: " + preparedTagsToSave.size());

        for(Tag tag : tagsfromEntity){
            if(exists(tag, tagsfromUser)){
                // tag existiert bereits und muss nur noch in entity gehaengt werden

                Tag persistedTag = tagDao.getTag(user,tag.getName());

                LogUtil.info(this.getClass(),
                        "Tag "
                        + persistedTag.getName()
                        + " existiert mit Anzahl: "
                        + persistedTag.getCount());

                persistedTag.increaseCount();
                tagDao.save(persistedTag);
                preparedTagsToSave.add(persistedTag);

            }
            else {

                LogUtil.info(this.getClass(),"Tag " + tag.getName() + " existiert noch nicht!");
                // tag existiert noch nicht -> zuerst grundsaetzlich speichern, mit User verbinden und Entity
                Tag newTag = tag;
                newTag.setAppUser(user);
                newTag.increaseCount();
                tagDao.save(newTag);
                preparedTagsToSave.add(tag);
            }
        }

        aEntry.setTags(preparedTagsToSave);

        entryDao.save(aEntry);
    }

    private List<Tag> distinct(List<Tag> tags) {
        Set<Tag> uniqueSet = new HashSet<>(tags);
        return new ArrayList<>(uniqueSet);
    }

    public void deleteEntry(Entry aEntry){

        List<Tag> tags = aEntry.getTags();

        entryDao.delete(aEntry);

        for(Tag tag : tags){
            tag.descreaseCount();

            if(tag.getCount() == 0){
                tagDao.delete(tag);
            }

            if(tag.getCount() > 0){
                tagDao.save(tag);
            }

        }
    }

    public Entry getEntry(String aHash) {
        return entryDao.getEntryByHash(aHash).get(0);
    }

    public void update(Entry aEntry){
        Entry persistedEntry = getEntry(aEntry.getHash());

        LogUtil.info(this.getClass(),"Update Entry -> " + persistedEntry.getHash());
        persistedEntry.setAmount(aEntry.getAmount());
        persistedEntry.setMemo(aEntry.getMemo());

        LogUtil.info(this.getClass(),"# Look for tags change ...");

        // hier set
        List<Tag> persistedTags = persistedEntry.getTags();
        List<Tag> currentTags = distinct(aEntry.getTags());

        LogUtil.info(this.getClass()," -> persistedTags: " + persistedTags.size());
        LogUtil.info(this.getClass()," -> currentTags      : " + currentTags.size());

        // added Tags

        List<Tag> newTags = new LinkedList<>();

        for(Tag tag : currentTags){
            if(!exists(tag, persistedTags)){
                newTags.add(tag);
            }
        }

        LogUtil.info(this.getClass(),"Found  " + newTags.size() + " new tags");

        // deleted Tags

        List<Tag> deletedTags = new LinkedList<>();

        for(Tag tag : persistedTags){
            if(!exists(tag, currentTags)){
                deletedTags.add(tag);
            }
        }

        LogUtil.info(this.getClass(),"Found  " + deletedTags.size() + " tags for delete");

        // remove Tags

        for(Tag tag: deletedTags){
            LogUtil.info(this.getClass(),"Remove tag: " + tag.getName());
            persistedTags.remove(tag);
        }


        // persist tags with user if not exists

        AppUser user = new AppUser();
        try {
            user = userDao.getUserByNameOrEmail(aEntry.getAppUser().getEmail());

        } catch (NoUserFoundException e) {
            e.printStackTrace();
        }

        Set<Tag> persistedUserTags = tagDao.getAllByUser(aEntry.getAppUser());
        LogUtil.info(this.getClass(), "All tags for user: " + persistedUserTags.size());
        for(Tag tag : newTags){
            LogUtil.info(this.getClass(),"Bearbeite tag: " + tag.getName());
            if(!exists(tag,new LinkedList<>(persistedUserTags))){
                tag.setAppUser(user);
                LogUtil.info(this.getClass(), "- save tag: " + tag.getName());
                tag.increaseCount();
                tagDao.save(tag);
                persistedTags.add(tag);
            }
            else
            {
                LogUtil.info(this.getClass(),"- increment tag: " + tag.getName());
                Tag persistedTag = tagDao.getTag(user,tag.getName());
                persistedTag.increaseCount();
                tagDao.save(persistedTag);
                persistedTags.add(persistedTag);
            }
        }

        List<Tag> tagsToDelete = new LinkedList<>();

        // deincrement tags
        LogUtil.info(this.getClass(), "Behandel deleted tags ...");
        for(Tag tag: persistedUserTags){
            if(exists(tag, deletedTags)){
                tag.descreaseCount();
                tagDao.save(tag);
                if(tag.getCount() == 0){
                    tagsToDelete.add(tag);
                }

            }
        }

        persistedEntry.setTags(persistedTags);
        entryDao.save(persistedEntry);

        for(Tag tag :tagsToDelete){
            tagDao.delete(tag);
        }
    }

    private boolean exists(Tag aTag, List<Tag> tags){
        for(Tag tag : tags){
            if(aTag.getName().equals(tag.getName())){
                return true;
            }
        }
        return false;
    }
}