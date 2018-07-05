package de.pfann.budgetmanager.server.persistenscouchdb.dao;

import de.pfann.budgetmanager.server.persistenscouchdb.model.CDBStandigOrderEntry;
import org.ektorp.CouchDbConnector;
import org.ektorp.support.CouchDbRepositorySupport;

public class CDBStandingOrderDao extends CouchDbRepositorySupport<CDBStandigOrderEntry> {

    protected CDBStandingOrderDao(CouchDbConnector db) {
        super(CDBStandigOrderEntry.class, db, true);
    }

}
