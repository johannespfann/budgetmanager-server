package de.pfann.budgetmanager.server.persistens.daos;

import de.pfann.budgetmanager.server.model.AppUser;
import de.pfann.budgetmanager.server.model.Entry;

import java.util.Date;
import java.util.List;

public class EntryFacade {

    private CategoryDao categoryDao;

    private EntryDao entryDao;

    private AppUserDao userDao;

    public EntryFacade(){
        categoryDao = CategoryDao.create();
        entryDao = EntryDao.create();
        userDao = AppUserDao.create();
    }

    public List<Entry> getEntries(AppUser aUser) {
        return entryDao.getAllByUser(aUser);
    }

    public void addEntry(Entry aEntry) {
        aEntry.setCreated_at(new Date());
        entryDao.save(aEntry);
    }

    public void deleteEntry(Entry aEntry){
        entryDao.delete(aEntry);
    }

    public Entry getEntry(String aHash) {
        return entryDao.getEntryByHash(aHash).get(0);
    }
}
