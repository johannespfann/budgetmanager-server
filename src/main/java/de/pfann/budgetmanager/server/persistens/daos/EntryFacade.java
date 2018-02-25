package de.pfann.budgetmanager.server.persistens.daos;

import de.pfann.budgetmanager.server.model.AppUser;
import de.pfann.budgetmanager.server.model.Entry;
import de.pfann.budgetmanager.server.model.Tag;
import org.omg.IOP.TAG_ALTERNATE_IIOP_ADDRESS;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EntryFacade {

    private CategoryDao categoryDao;

    private EntryDao entryDao;

    private AppUserDao userDao;

    public EntryFacade(){
        categoryDao = CategoryDao.create();
        entryDao = EntryDao.create();
        userDao = AppUserDao.create();
    }

    public Set<Entry> getEntries(AppUser aUser) {
        List<Entry> entries = entryDao.getAllByUser(aUser);
        System.out.println("Anzahl aller Entries:  " +  entries.size());

        Set<Entry> entrySet = new HashSet<>(entries);

        System.out.println("Anzahl aller Entries in Set " + entrySet.size());

        return entrySet;


    }

    public List<Entry> getEntries(Tag aTag){
        return entryDao.getAllByTag(aTag);
    }

    public void persistEntry(Entry aEntry) {
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
