package de.pfann.budgetmanager.server.persistens.dao;

import de.pfann.budgetmanager.server.model.Entry;
import org.ektorp.CouchDbConnector;
import org.ektorp.support.CouchDbRepositorySupport;

public class CDBEntryDao extends CouchDbRepositorySupport<Entry> {

    protected CDBEntryDao(CouchDbConnector db) {
        super(Entry.class, db, true);
    }

}
