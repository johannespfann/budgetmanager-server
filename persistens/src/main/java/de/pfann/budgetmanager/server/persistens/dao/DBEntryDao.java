package de.pfann.budgetmanager.server.persistens.dao;

import de.pfann.budgetmanager.server.persistens.model.CDBEntry;
import org.ektorp.CouchDbConnector;
import org.ektorp.support.CouchDbRepositorySupport;

public class DBEntryDao extends CouchDbRepositorySupport<CDBEntry> {

    protected DBEntryDao(CouchDbConnector db) {
        super(CDBEntry.class, db, true);
    }

}
