package de.pfann.budgetmanager.server.persistenscouchdb.dao;

import de.pfann.budgetmanager.server.persistenscouchdb.core.CouchDbConnectorFactory;
import org.ektorp.CouchDbConnector;

public class CDBKontoDatabaseFactory {

    private CouchDbConnectorFactory factory;

    public CDBKontoDatabaseFactory(CouchDbConnectorFactory aFactory){
        factory = aFactory;
    }

    public void createDBIfExists(String aName){
        CouchDbConnector connector = factory.createCouchDbConnector(aName);
        connector.createDatabaseIfNotExists();
    }

}
