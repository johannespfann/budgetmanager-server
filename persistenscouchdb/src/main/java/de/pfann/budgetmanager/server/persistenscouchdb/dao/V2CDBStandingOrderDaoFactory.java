package de.pfann.budgetmanager.server.persistenscouchdb.dao;

import de.pfann.budgetmanager.server.persistenscouchdb.core.CouchDbConnectorFactory;

public class V2CDBStandingOrderDaoFactory {

    private CouchDbConnectorFactory couchDbConnectorFactory;

    public V2CDBStandingOrderDaoFactory(CouchDbConnectorFactory aConnectorFactory){
        couchDbConnectorFactory = aConnectorFactory;
    }
    public V2CDBStandingOrderDao createDao(String aDBName){
        return new V2CDBStandingOrderDao(couchDbConnectorFactory.createCouchDbConnector(aDBName));
    }

}
