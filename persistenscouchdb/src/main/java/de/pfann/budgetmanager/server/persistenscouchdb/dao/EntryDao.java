package de.pfann.budgetmanager.server.persistenscouchdb.dao;

import de.pfann.budgetmanager.server.persistenscouchdb.model.CDBEntry;
import org.ektorp.CouchDbConnector;
import org.ektorp.support.CouchDbRepositorySupport;

public class EntryDao extends CouchDbRepositorySupport<CDBEntry> {

    public EntryDao(CouchDbConnector db) {
        super(CDBEntry.class, db,true);
    }


}
