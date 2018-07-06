package de.pfann.budgetmanager.server.persistenscouchdb.dao;

import de.pfann.budgetmanager.server.persistenscouchdb.core.CouchDbConnectorFactory;

public class CDBEntryDaoFactory {

    private CouchDbConnectorFactory couchDbConnectorFactory;

    public CDBEntryDaoFactory(CouchDbConnectorFactory aConnectorFactory){
        couchDbConnectorFactory = aConnectorFactory;
    }

    public CDBEntryDao createDao(String aName){
        return new CDBEntryDao(couchDbConnectorFactory.createCouchDbConnector(aName));
    }

}
