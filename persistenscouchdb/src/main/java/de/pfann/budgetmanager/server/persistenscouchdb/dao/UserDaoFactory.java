package de.pfann.budgetmanager.server.persistenscouchdb.dao;

import de.pfann.budgetmanager.server.persistenscouchdb.core.CouchDbConnectorFactory;

public class UserDaoFactory {

    private CouchDbConnectorFactory couchDbConnectorFactory;

    public UserDaoFactory(CouchDbConnectorFactory aConnectorFactory){
        couchDbConnectorFactory = aConnectorFactory;
    }

    public CDBUserDao createDao(){
        return new CDBUserDao(couchDbConnectorFactory.createCouchDbConnector("user"));
    }

}
