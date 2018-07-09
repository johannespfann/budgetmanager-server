package de.pfann.budgetmanager.server.persistens.daos;

import de.pfann.budgetmanager.server.common.facade.RotationEntryFacade;
import de.pfann.budgetmanager.server.common.model.AppUser;
import de.pfann.budgetmanager.server.common.model.RotationEntry;
import de.pfann.budgetmanager.server.common.model.Tag;
import de.pfann.budgetmanager.server.common.util.DateUtil;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class RotationEntrySQLFacade implements RotationEntryFacade {

    private RotationEntryDao roationEntryDao;


    public RotationEntrySQLFacade(){
        roationEntryDao = RotationEntryDao.create();
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

        Set<Tag> uniqueTags = new HashSet<>(aEntry.getTags());

        for(Tag tag : uniqueTags){
            //tag.setRotationEntry(aEntry);
        }

    }

    @Override
    public void update(RotationEntry aEntry){
        RotationEntry persistedEntry = roationEntryDao.getRotationEntryByHash(aEntry.getHash());

    }

    @Override
    public List<RotationEntry> getRotationEntries(AppUser aUser) {

        List<RotationEntry> entries =  roationEntryDao.getRotationEntries(aUser);

        for(RotationEntry entry : entries){
        
        }

        return entries;

    }

    @Override
    public RotationEntry getRotationEntryByHash(AppUser aAppUser, String aHash){
        RotationEntry entry = roationEntryDao.getRotationEntryByHash(aHash);
        return entry;
    }

    @Override
    public void delete(RotationEntry aRotationEntry){
        List<Tag> tags = new LinkedList<>();

        for(Tag tag : tags){
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
