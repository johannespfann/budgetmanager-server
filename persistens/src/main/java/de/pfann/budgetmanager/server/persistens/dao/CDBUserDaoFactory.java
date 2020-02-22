package de.pfann.budgetmanager.server.persistens.dao;

import de.pfann.budgetmanager.server.persistens.core.CouchDbConnectorFactory;

public class CDBUserDaoFactory {

    private CouchDbConnectorFactory couchDbConnectorFactory;

    public CDBUserDaoFactory(CouchDbConnectorFactory aConnectorFactory) {
        couchDbConnectorFactory = aConnectorFactory;
    }

    public CDBUserDao createDao() {
        return new CDBUserDao(couchDbConnectorFactory.createCouchDbConnector("user"));
    }

}
