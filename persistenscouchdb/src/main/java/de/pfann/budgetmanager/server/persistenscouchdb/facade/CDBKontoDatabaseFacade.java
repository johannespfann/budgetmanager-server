package de.pfann.budgetmanager.server.persistenscouchdb.facade;

import de.pfann.budgetmanager.server.persistenscouchdb.core.CouchDbConnectorFactory;
import de.pfann.budgetmanager.server.persistenscouchdb.dao.CDBUserDaoFactory;
import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;

public class CDBKontoDatabaseFacade {

    private CouchDbConnectorFactory factory;
    private CouchDbInstance instance;

    public CDBKontoDatabaseFacade(CouchDbConnectorFactory aFactory, CouchDbInstance aInstance){
        factory = aFactory;
        instance = aInstance;
    }

    public void createDBIfExists(String aName){
        CouchDbConnector connector = factory.createCouchDbConnector(aName);
        connector.createDatabaseIfNotExists();
    }

    public void deleteDBIfExists(String aName) {
        instance.deleteDatabase(aName);
    }

}
