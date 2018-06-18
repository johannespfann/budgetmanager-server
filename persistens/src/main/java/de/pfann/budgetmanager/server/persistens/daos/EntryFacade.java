package de.pfann.budgetmanager.server.persistens.daos;

import de.pfann.budgetmanager.server.common.util.LogUtil;
import de.pfann.budgetmanager.server.persistens.model.AppUser;
import de.pfann.budgetmanager.server.persistens.model.Entry;
import de.pfann.budgetmanager.server.persistens.model.Tag;

import java.util.*;

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
        if(aEntry.getCreated_at() == null) {
            aEntry.setCreated_at(new Date());
        }

        AppUser user = null;
        try {
            user = userDao.getUserByNameOrEmail(aEntry.getAppUser().getName());
        } catch (NoUserFoundException e) {
            e.printStackTrace();
        }

        List<Tag> tagsfromUser = new ArrayList<>(tagDao.getAllByUser(user));
        List<Tag> tagsfromEntity = distinct(aEntry.getTags());
        List<Tag> preparedTagsToSave = new LinkedList<>();



        for(Tag tag : tagsfromEntity){
            if(exists(tag, tagsfromUser)){
                Tag persistedTag = tagDao.getTag(user,tag.getName());
                tagDao.save(persistedTag);
                preparedTagsToSave.add(persistedTag);

            }
            else {
                Tag newTag = tag;
                newTag.setAppUser(user);
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

        persistedEntry.setAmount(aEntry.getAmount());
        persistedEntry.setMemo(aEntry.getMemo());



        // hier set
        List<Tag> persistedTags = persistedEntry.getTags();
        List<Tag> currentTags = distinct(aEntry.getTags());

        // added Tags

        List<Tag> newTags = new LinkedList<>();

        for(Tag tag : currentTags){
            if(!exists(tag, persistedTags)){
                newTags.add(tag);
            }
        }


        // deleted Tags

        List<Tag> deletedTags = new LinkedList<>();

        for(Tag tag : persistedTags){
            if(!exists(tag, currentTags)){
                deletedTags.add(tag);
            }
        }


        // remove Tags

        for(Tag tag: deletedTags){
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
        for(Tag tag : newTags){
            if(!exists(tag,new LinkedList<>(persistedUserTags))){
                tag.setAppUser(user);
                tagDao.save(tag);
                persistedTags.add(tag);
            }
            else
            {
                Tag persistedTag = tagDao.getTag(user,tag.getName());
                tagDao.save(persistedTag);
                persistedTags.add(persistedTag);
            }
        }

        List<Tag> tagsToDelete = new LinkedList<>();

        // deincrement tags
        for(Tag tag: persistedUserTags){
            if(exists(tag, deletedTags)){
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