package de.pfann.budgetmanager.server.persistenscouchdb.dao;

import de.pfann.budgetmanager.server.persistenscouchdb.core.CouchDbConnectorFactory;

public class CDBStandingOrderDaoFactory {

    private CouchDbConnectorFactory couchDbConnectorFactory;

    private CDBStandingOrderDaoFactory(CouchDbConnectorFactory aConnectorFactory){
        couchDbConnectorFactory = aConnectorFactory;
    }
    public CDBStandingOrderDao createDao(String aDBName){
        return new CDBStandingOrderDao(couchDbConnectorFactory.createCouchDbConnector(aDBName));
    }

}
