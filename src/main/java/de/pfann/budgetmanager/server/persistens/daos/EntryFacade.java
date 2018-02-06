package de.pfann.budgetmanager.server.persistens.daos;

import de.pfann.budgetmanager.server.model.AppUser;
import de.pfann.budgetmanager.server.model.Entry;

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
        entryDao.save(aEntry);
    }

    public void deleteEntry(Entry aEntry){
        entryDao.delete(aEntry);
    }
}
