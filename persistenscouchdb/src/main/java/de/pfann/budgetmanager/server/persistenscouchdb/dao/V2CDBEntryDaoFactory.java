package de.pfann.budgetmanager.server.persistenscouchdb.dao;

import de.pfann.budgetmanager.server.persistenscouchdb.core.CouchDbConnectorFactory;

public class V2CDBEntryDaoFactory {

    private CouchDbConnectorFactory couchDbConnectorFactory;

    public V2CDBEntryDaoFactory(CouchDbConnectorFactory aConnectorFactory){
        couchDbConnectorFactory = aConnectorFactory;
    }

    public V2CDBEntryDao createDao(String aName){
        return new V2CDBEntryDao(couchDbConnectorFactory.createCouchDbConnector(aName));
    }
}
