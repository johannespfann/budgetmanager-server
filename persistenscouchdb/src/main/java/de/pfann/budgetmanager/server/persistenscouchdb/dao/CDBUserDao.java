package de.pfann.budgetmanager.server.persistenscouchdb.dao;

import de.pfann.budgetmanager.server.model.User;
import org.ektorp.CouchDbConnector;
import org.ektorp.support.CouchDbRepositorySupport;

public class CDBUserDao extends CouchDbRepositorySupport<User> {

    protected CDBUserDao(CouchDbConnector db) {
        super(User.class, db, true);
    }
}
