package de.pfann.budgetmanager.server.persistenscouchdb.dao;

import de.pfann.budgetmanager.server.persistenscouchdb.model.CDBEntry;
import org.ektorp.CouchDbConnector;
import org.ektorp.support.CouchDbRepositorySupport;

public class CDBEntryDao extends CouchDbRepositorySupport<CDBEntry> {

    protected CDBEntryDao(CouchDbConnector db) {
        super(CDBEntry.class, db,true);
    }


}
