package de.pfann.budgetmanager.server.persistenscouchdb.dao;

import de.pfann.budgetmanager.server.model.Entry;
import org.ektorp.CouchDbConnector;
import org.ektorp.support.CouchDbRepositorySupport;

public class V2CDBEntryDao extends CouchDbRepositorySupport<Entry> {

    protected V2CDBEntryDao(CouchDbConnector db) {
        super(Entry.class, db,true);
    }

}
