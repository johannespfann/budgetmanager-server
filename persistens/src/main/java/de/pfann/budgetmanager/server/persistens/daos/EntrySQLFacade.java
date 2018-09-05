package de.pfann.budgetmanager.server.persistens.daos;

import de.pfann.budgetmanager.server.common.facade.EntryFacade;
import de.pfann.budgetmanager.server.common.model.AppUser;
import de.pfann.budgetmanager.server.common.model.Entry;
import de.pfann.budgetmanager.server.common.model.Tag;

import de.pfann.server.logging.core.LogUtil;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.*;

public class EntrySQLFacade implements EntryFacade {

    private EntryDao entryDao;

    private AppUserDao userDao;

    private TagDao tagDao;

    public EntrySQLFacade(){
        entryDao = EntryDao.create();
        userDao = AppUserDao.create();
        tagDao = TagDao.create();
    }

    @Override
    public List<Entry> getEntries(AppUser aUser) {
        // TODO was set before ...
        return entryDao.getAllByUser(aUser);
    }

    @Override
    public void persistEntry(Entry aEntry) {
        if(aEntry.getCreated_at() == null) {
            aEntry.setCreated_at(new Date());
        }
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

    @Override
    public void deleteEntry(AppUser aUser, Entry aEntry){

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

    @Override
    public Entry getEntry(AppUser aUser, String aHash) {
        throw new NotImplementedException();
    }


    @Override
    public void update(Entry aEntry){
        throw new NotImplementedException();
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