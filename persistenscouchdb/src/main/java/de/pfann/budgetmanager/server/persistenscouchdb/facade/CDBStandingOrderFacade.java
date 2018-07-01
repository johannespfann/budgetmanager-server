package de.pfann.budgetmanager.server.persistenscouchdb.facade;

import de.pfann.budgetmanager.server.common.facade.RotationEntryFacade;
import de.pfann.budgetmanager.server.common.model.AppUser;
import de.pfann.budgetmanager.server.common.model.RotationEntry;
import org.ektorp.CouchDbInstance;
import org.ektorp.impl.ObjectMapperFactory;

import java.util.List;

public class CDBStandingOrderFacade implements RotationEntryFacade{


    private CouchDbInstance couchDbInstance;
    private ObjectMapperFactory objectMapperFactory;

    public CDBStandingOrderFacade(CouchDbInstance aCouchInstance, ObjectMapperFactory aObjectMapperFactory){
        couchDbInstance = aCouchInstance;
        objectMapperFactory = aObjectMapperFactory;
    }

    @Override
    public void save(RotationEntry aEntry) {

    }

    @Override
    public void update(RotationEntry aEntry) {

    }

    @Override
    public List<RotationEntry> getRotationEntries(AppUser aUser) {
        return null;
    }

    @Override
    public RotationEntry getRotationEntryByHash(String aHash) {
        return null;
    }

    @Override
    public void delete(RotationEntry aRotationEntry) {

    }

    @Override
    public void validateRotationEntry(RotationEntry aRotationEntry) {

    }
}
