package de.pfann.budgetmanager.server.persistens.daos;

import de.pfann.budgetmanager.server.common.model.AppUser;
import de.pfann.budgetmanager.server.common.model.StandingOrder;
import de.pfann.budgetmanager.server.common.model.Tag;
import de.pfann.budgetmanager.server.common.util.DateUtil;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class RotationEntrySQLFacade implements StandingOrderFacade {

    private RotationEntryDao roationEntryDao;


    public RotationEntrySQLFacade(){
        roationEntryDao = RotationEntryDao.create();
    }

    public RotationEntrySQLFacade(RotationEntryDao aDao){
        roationEntryDao = aDao;
    }

    @Override
    public void save(StandingOrder aEntry){

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
    public void update(StandingOrder aEntry){
        StandingOrder persistedEntry = roationEntryDao.getRotationEntryByHash(aEntry.getHash());

    }

    @Override
    public List<StandingOrder> getRotationEntries(AppUser aUser) {

        List<StandingOrder> entries =  roationEntryDao.getRotationEntries(aUser);

        for(StandingOrder entry : entries){
        
        }

        return entries;

    }

    @Override
    public StandingOrder getRotationEntryByHash(AppUser aAppUser, String aHash){
        StandingOrder entry = roationEntryDao.getRotationEntryByHash(aHash);
        return entry;
    }

    @Override
    public void delete(StandingOrder aRotationEntry){
        List<Tag> tags = new LinkedList<>();

        for(Tag tag : tags){
        }

        roationEntryDao.delete(aRotationEntry);
    }

    // TODO wie mach ich die validierungsgeschichten ll
    // Wird noch ueberlegt
    @Override
    public void validateRotationEntry(StandingOrder aRotationEntry){

        // hash ist bei update dabei und bei persist noch nicht ...
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
