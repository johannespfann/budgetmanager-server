package de.pfann.budgetmanager.server.persistenscouchdb.dao;

import de.pfann.budgetmanager.server.persistenscouchdb.model.CDBUser;
import org.ektorp.CouchDbConnector;
import org.ektorp.support.CouchDbRepositorySupport;

public class CDBUserDao extends CouchDbRepositorySupport<CDBUser> {

    protected CDBUserDao(CouchDbConnector db) {
        super(CDBUser.class, db, true);
    }

}
