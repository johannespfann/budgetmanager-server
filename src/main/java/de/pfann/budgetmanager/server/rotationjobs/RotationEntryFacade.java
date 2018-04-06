package de.pfann.budgetmanager.server.rotationjobs;

import de.pfann.budgetmanager.server.model.AppUser;
import de.pfann.budgetmanager.server.model.Category;
import de.pfann.budgetmanager.server.persistens.daos.CategoryDao;
import de.pfann.budgetmanager.server.rotationjobs.RotationEntry;
import de.pfann.budgetmanager.server.rotationjobs.RotationEntryDao;

import java.util.List;

public class RotationEntryFacade {

    private RotationEntryDao roationEntryDao;

    private CategoryDao categoryDao;

    public RotationEntryFacade(){
        roationEntryDao = RotationEntryDao.create();
        categoryDao = CategoryDao.create();
    }

    public RotationEntryFacade(RotationEntryDao aDao){
        roationEntryDao = aDao;
    }

    public void save(RotationEntry aEntry){
        Category category = categoryDao.getCategory(aEntry.getCategory().getHash()).get(0);
        aEntry.setCategory(category);
        roationEntryDao.save(aEntry);
    }

    public void update(RotationEntry aEntry){
        RotationEntry persistedEntry = roationEntryDao.getRotationEntryByHash(aEntry.getHash());
        Category category = categoryDao.getCategory(aEntry.getCategory().getHash()).get(0);

        // map all values
        // hash
        persistedEntry.setAmount(aEntry.getAmount());
        persistedEntry.setMemo(aEntry.getMemo());
        persistedEntry.setCategory(category);
        persistedEntry.setTags(aEntry.getTags());
        persistedEntry.setRotation_strategy(aEntry.getRotation_strategy());
        persistedEntry.setStart_at(aEntry.getStart_at());
        persistedEntry.setLast_executed(aEntry.getLast_executed());
        persistedEntry.setEnd_at(aEntry.getEnd_at());
        // TODO copy constructor


        roationEntryDao.save(persistedEntry);
    }

    public List<RotationEntry> getRotationEntries(AppUser aUser) {
        return roationEntryDao.getRotationEntries(aUser);
    }

    public RotationEntry getRotationEntryByHash(String aHash){
        return roationEntryDao.getRotationEntryByHash(aHash);
    }

    public void delete(RotationEntry aRotationEntry){
        roationEntryDao.delete(aRotationEntry);
    }
}
