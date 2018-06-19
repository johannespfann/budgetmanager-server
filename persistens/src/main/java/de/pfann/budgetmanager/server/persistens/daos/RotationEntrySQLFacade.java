package de.pfann.budgetmanager.server.persistens.daos;

import de.pfann.budgetmanager.server.common.facade.RotationEntryFacade;
import de.pfann.budgetmanager.server.common.model.AppUser;
import de.pfann.budgetmanager.server.common.model.RotationEntry;
import de.pfann.budgetmanager.server.common.model.TagTemplate;
import de.pfann.budgetmanager.server.common.util.DateUtil;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RotationEntrySQLFacade implements RotationEntryFacade {

    private RotationEntryDao roationEntryDao;

    private TagTemplateDao tagTemplateDao;

    public RotationEntrySQLFacade(){
        roationEntryDao = RotationEntryDao.create();
        tagTemplateDao = TagTemplateDao.create();
    }

    public RotationEntrySQLFacade(RotationEntryDao aDao){
        roationEntryDao = aDao;
    }

    @Override
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

    @Override
    public void update(RotationEntry aEntry){
        RotationEntry persistedEntry = roationEntryDao.getRotationEntryByHash(aEntry.getHash());

        // getallPersistedTags and delete them
        List<TagTemplate> persistedTags = tagTemplateDao.findAllByRotationEntry(persistedEntry);

        for(TagTemplate tag : persistedTags){
            tagTemplateDao.delete(tag);
        }

        persistedEntry.setAmount(aEntry.getAmount());
        persistedEntry.setMemo(aEntry.getMemo());
        persistedEntry.setRotation_strategy(aEntry.getRotation_strategy());
        persistedEntry.setStart_at(aEntry.getStart_at());
        persistedEntry.setLast_executed(aEntry.getLast_executed());
        persistedEntry.setEnd_at(aEntry.getEnd_at());

        roationEntryDao.save(persistedEntry);

        Set<TagTemplate> unquieTags = new HashSet<>(aEntry.getTags());
        for(TagTemplate tag : unquieTags){
            tag.setRotationEntry(persistedEntry);
            tagTemplateDao.save(tag);
        }
    }

    @Override
    public List<RotationEntry> getRotationEntries(AppUser aUser) {

        List<RotationEntry> entries =  roationEntryDao.getRotationEntries(aUser);

        for(RotationEntry entry : entries){
            List<TagTemplate> tags = tagTemplateDao.findAllByRotationEntry(entry);
            entry.setTags(tags);
        }

        return entries;

    }

    @Override
    public RotationEntry getRotationEntryByHash(String aHash){
        RotationEntry entry = roationEntryDao.getRotationEntryByHash(aHash);
        return entry;
    }

    @Override
    public void delete(RotationEntry aRotationEntry){
        List<TagTemplate> tags = tagTemplateDao.findAllByRotationEntry(aRotationEntry);

        for(TagTemplate tag : tags){
            tagTemplateDao.delete(tag);
        }

        roationEntryDao.delete(aRotationEntry);
    }

    // TODO wie mach ich die validierungsgeschichten ll
    // Wird noch ueberlegt
    @Override
    public void validateRotationEntry(RotationEntry aRotationEntry){

        // hash ist bei update dabei und bei save noch nicht ...
        aRotationEntry.getHash();

        // Entry
        aRotationEntry.getMemo();
        aRotationEntry.getAmount();
        // muss extra geladen werden
        aRotationEntry.getTags();
        aRotationEntry.getUser();

        // zeitgeschichten
        aRotationEntry.getStart_at();
        aRotationEntry.getEnd_at();
        aRotationEntry.getLast_executed();

    }
}
