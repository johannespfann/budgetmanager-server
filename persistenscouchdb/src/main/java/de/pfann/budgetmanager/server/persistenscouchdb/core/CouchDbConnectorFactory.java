package de.pfann.budgetmanager.server.persistenscouchdb.core;

import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.impl.ObjectMapperFactory;
import org.ektorp.impl.StdCouchDbConnector;

public class CouchDbConnectorFactory {

    private CouchDbInstance couchDbInstance;
    private String applicationPrefix;
    private ObjectMapperFactory objectMapperFactory;

    public CouchDbConnectorFactory(CouchDbInstance aCouchInstance, String aApplicationPrefix,ObjectMapperFactory aObjectMapperFactory){
        couchDbInstance = aCouchInstance;
        objectMapperFactory = aObjectMapperFactory;
        applicationPrefix = aApplicationPrefix;
    }

    public CouchDbConnector createCouchDbConnector(String aDbName){
        return new StdCouchDbConnector(applicationPrefix + "_" + aDbName,couchDbInstance, objectMapperFactory);
    }

}
