package de.pfann.budgetmanager.server.persistenscouchdb.core;

import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.impl.ObjectMapperFactory;
import org.ektorp.impl.StdCouchDbConnector;

public class CouchDbConnectorFactory {

    private CouchDbInstance couchDbInstance;
    private ObjectMapperFactory objectMapperFactory;

    public CouchDbConnectorFactory(CouchDbInstance aCouchInstance, ObjectMapperFactory aObjectMapperFactory){
        couchDbInstance = aCouchInstance;
        objectMapperFactory = aObjectMapperFactory;
    }

    public CouchDbConnector createCouchDbConnector(String aDbName){
        return new StdCouchDbConnector(aDbName,couchDbInstance, objectMapperFactory);
    }

}
