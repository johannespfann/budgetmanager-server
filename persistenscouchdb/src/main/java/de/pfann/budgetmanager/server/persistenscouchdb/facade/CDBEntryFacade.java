package de.pfann.budgetmanager.server.persistenscouchdb.facade;

import de.pfann.budgetmanager.server.common.facade.EntryFacade;
import de.pfann.budgetmanager.server.common.model.AppUser;
import de.pfann.budgetmanager.server.common.model.Entry;
import de.pfann.budgetmanager.server.common.model.Tag;
import org.ektorp.CouchDbInstance;
import org.ektorp.impl.ObjectMapperFactory;

import java.util.List;
import java.util.Set;

public class CDBEntryFacade implements EntryFacade {

    private CouchDbInstance couchDbInstance;
    private ObjectMapperFactory objectMapperFactory;

    public CDBEntryFacade(CouchDbInstance aCouchInstance, ObjectMapperFactory aObjectMapperFactory){
        couchDbInstance = aCouchInstance;
        objectMapperFactory = aObjectMapperFactory;
    }

    @Override
    public List<Entry> getEntries(AppUser aUser) {
        return null;
    }

    @Override
    public List<Entry> getEntries(Tag aTag) {
        return null;
    }

    @Override
    public void persistEntry(Entry aEntry) {

    }

    @Override
    public void deleteEntry(Entry aEntry) {

    }

    @Override
    public Entry getEntry(String aHash) {
        return null;
    }

    @Override
    public void update(Entry aEntry) {

    }
}
