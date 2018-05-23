package de.pfann.budgetmanager.server.persistens.daos;

import de.pfann.budgetmanager.server.common.util.DateUtil;
import de.pfann.budgetmanager.server.common.util.LogUtil;
import de.pfann.budgetmanager.server.persistens.model.AppUser;
import de.pfann.budgetmanager.server.persistens.model.RotationEntry;
import de.pfann.budgetmanager.server.persistens.model.TagTemplate;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RotationEntryFacade {

    private RotationEntryDao roationEntryDao;

    private TagTemplateDao tagTemplateDao;

    public RotationEntryFacade(){
        roationEntryDao = RotationEntryDao.create();
        tagTemplateDao = TagTemplateDao.create();
    }

    public RotationEntryFacade(RotationEntryDao aDao){
        roationEntryDao = aDao;
    }

    public void save(RotationEntry aEntry){

        if(aEntry.getStart_at() == null){
            aEntry.setStart_at(DateUtil.getMinimumDate());
        }
        if(aEntry.getEnd_at() == null){
            aEntry.setEnd_at(DateUtil.getMaximumDate());
        }
        if(aEntry.getLast_executed() == null){
            aEntry.setLast_executed(DateUtil.getMinimumDate());
        }

        roationEntryDao.save(aEntry);

        Set<TagTemplate> uniqueTags = new HashSet<>(aEntry.getTags());

        for(TagTemplate tag : uniqueTags){
            tag.setRotationEntry(aEntry);
            tagTemplateDao.save(tag);
        }
    }

    public void update(RotationEntry aEntry){
        LogUtil.info(this.getClass(),"Update RotationEntry");
        RotationEntry persistedEntry = roationEntryDao.getRotationEntryByHash(aEntry.getHash());

        LogUtil.info(this.getClass(),"Get all existing tags");
        // getallPersistedTags and delete them
        List<TagTemplate> persistedTags = tagTemplateDao.findAllByRotationEntry(persistedEntry);

        LogUtil.info(this.getClass(),"List all Tags to delete");
        for(TagTemplate tag : persistedTags){
            LogUtil.info(this.getClass()," - delete " + tag);
            tagTemplateDao.delete(tag);
        }

        persistedEntry.setAmount(aEntry.getAmount());
        persistedEntry.setMemo(aEntry.getMemo());
        persistedEntry.setRotation_strategy(aEntry.getRotation_strategy());
        persistedEntry.setStart_at(aEntry.getStart_at());
        persistedEntry.setLast_executed(aEntry.getLast_executed());
        persistedEntry.setEnd_at(aEntry.getEnd_at());
        // TODO copy constructor
        LogUtil.info(this.getClass(),"income: " + aEntry);
        //LogUtil.info(this.getClass(),"merged: " + persistedEntry);

        roationEntryDao.save(persistedEntry);

        Set<TagTemplate> unquieTags = new HashSet<>(aEntry.getTags());
        for(TagTemplate tag : unquieTags){
            LogUtil.info(this.getClass(),"Save new Tag: " + tag);
            tag.setRotationEntry(persistedEntry);
            tagTemplateDao.save(tag);
        }
    }

    public List<RotationEntry> getRotationEntries(AppUser aUser) {

        List<RotationEntry> entries =  roationEntryDao.getRotationEntries(aUser);

        for(RotationEntry entry : entries){
            List<TagTemplate> tags = tagTemplateDao.findAllByRotationEntry(entry);
            entry.setTags(tags);
        }

        return entries;

    }

    public RotationEntry getRotationEntryByHash(String aHash){
        LogUtil.info(this.getClass(), "getRotationEntry");
        RotationEntry entry = roationEntryDao.getRotationEntryByHash(aHash);
        LogUtil.info(this.getClass(), "before Found ");
        //LogUtil.info(this.getClass(), "Found -> " + entry.toString());
        LogUtil.info(this.getClass(), "after Found ");
        return entry;
    }

    public void delete(RotationEntry aRotationEntry){
        LogUtil.info(this.getClass(),"Delete rotationEntry: " + aRotationEntry.getHash());
        List<TagTemplate> tags = tagTemplateDao.findAllByRotationEntry(aRotationEntry);

        for(TagTemplate tag : tags){
            tagTemplateDao.delete(tag);
        }

        roationEntryDao.delete(aRotationEntry);
    }
}
