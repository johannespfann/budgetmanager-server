package de.pfann.budgetmanager.server.persistenscouchdb.dao;

import de.pfann.budgetmanager.server.persistenscouchdb.core.CouchDbConnectorFactory;
import de.pfann.budgetmanager.server.persistenscouchdb.util.CDBSystemDatabaseId;

public class CDBRunDoaFactory {

    private CouchDbConnectorFactory couchDbConnectorFactory;

    public CDBRunDoaFactory(CouchDbConnectorFactory aConnectorFactory){
        couchDbConnectorFactory = aConnectorFactory;
    }

    public CDBRunDao createDao(){
        String dbName = CDBSystemDatabaseId.createId();
        return new CDBRunDao(couchDbConnectorFactory.createCouchDbConnector(dbName));
    }
}
