package de.pfann.budgetmanager.server.persistenscouchdb.dao;

import de.pfann.budgetmanager.server.persistenscouchdb.core.CouchDbConnectorFactory;

public class CDBRunDoaFactory {

    private CouchDbConnectorFactory couchDbConnectorFactory;

    public CDBRunDoaFactory(CouchDbConnectorFactory aConnectorFactory){
        couchDbConnectorFactory = aConnectorFactory;
    }

    public CDBRunDao createDao(String aName){
        return new CDBRunDao(couchDbConnectorFactory.createCouchDbConnector(aName));
    }
}
