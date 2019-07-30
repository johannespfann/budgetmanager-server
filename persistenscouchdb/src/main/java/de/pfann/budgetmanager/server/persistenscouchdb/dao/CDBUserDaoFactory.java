package de.pfann.budgetmanager.server.persistenscouchdb.dao;

import de.pfann.budgetmanager.server.persistenscouchdb.core.CouchDbConnectorFactory;

public class CDBUserDaoFactory {

    private CouchDbConnectorFactory couchDbConnectorFactory;

    public CDBUserDaoFactory(CouchDbConnectorFactory aConnectorFactory){
        couchDbConnectorFactory = aConnectorFactory;
    }

    public CDBUserDao createDao(){
        return new CDBUserDao(couchDbConnectorFactory.createCouchDbConnector("user"));
    }

}
