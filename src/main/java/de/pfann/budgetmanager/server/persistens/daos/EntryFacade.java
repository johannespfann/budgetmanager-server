package de.pfann.budgetmanager.server.persistens.daos;

import de.pfann.budgetmanager.server.model.AppUser;
import de.pfann.budgetmanager.server.model.Category;
import de.pfann.budgetmanager.server.model.Entry;
import de.pfann.budgetmanager.server.model.Tag;
import org.omg.IOP.TAG_ALTERNATE_IIOP_ADDRESS;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class EntryFacade {

    private CategoryDao categoryDao;

    private EntryDao entryDao;

    private AppUserDao userDao;

    private TagDao tagDao;


    public EntryFacade(){
        categoryDao = CategoryDao.create();
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

        // TODO update tags with user and delete TagFacade
        entryDao.save(aEntry);
    }

    public void deleteEntry(Entry aEntry){
        entryDao.delete(aEntry);
    }

    public Entry getEntry(String aHash) {
        return entryDao.getEntryByHash(aHash).get(0);
    }

    public void update(Entry aEntry){
        Entry persistedEntry = getEntry(aEntry.getHash());

        persistedEntry.setAmount(aEntry.getAmount());
        persistedEntry.setMemo(aEntry.getMemo());

        if(persistedEntry.getCategory().getHash() != aEntry.getCategory().getHash()){
            Category newCategory = categoryDao.getCategory(aEntry.getHash()).get(0);
            persistedEntry.setCategory(newCategory);
        }

        List<Tag> persistedTags = persistedEntry.getTags();
        List<Tag> updatedTags = aEntry.getTags();

        // added Tags

        List<Tag> newTags = new LinkedList<>();

        for(Tag tag : updatedTags){
            if(!exists(tag, persistedTags)){
                newTags.add(tag);
            }
        }

        // deleted Tags

        List<Tag> deletedTags = new LinkedList<>();

        for(Tag tag : persistedTags){
            if(!exists(tag, updatedTags)){
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
            }
        }

        // add new tags

        for(Tag tag: newTags){
            persistedTags.add(tag);
        }

        persistedEntry.setTags(persistedTags);
        entryDao.save(persistedEntry);
    }

    private boolean exists(Tag aTag, List<Tag> tags){
        for(Tag tag : tags){
            if(aTag.getName() == tag.getName()){
                return true;
            }
        }
        return false;
    }


}
