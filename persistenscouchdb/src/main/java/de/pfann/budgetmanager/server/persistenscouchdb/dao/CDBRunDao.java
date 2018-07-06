package de.pfann.budgetmanager.server.persistenscouchdb.dao;

import de.pfann.budgetmanager.server.persistenscouchdb.model.CDBRun;

import org.ektorp.CouchDbConnector;
import org.ektorp.support.CouchDbRepositorySupport;

public class CDBRunDao extends CouchDbRepositorySupport<CDBRun> {

    protected CDBRunDao(CouchDbConnector db) {
        super(CDBRun.class, db, true);
    }

}
