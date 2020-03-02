package de.pfann.budgetmanager.server.persistens.dao;

import de.pfann.budgetmanager.server.persistens.core.DbConnectorFactory;

public class DBStandingOrderDaoFactory {

    private DbConnectorFactory dbConnectorFactory;

    public DBStandingOrderDaoFactory(DbConnectorFactory aConnectorFactory) {
        dbConnectorFactory = aConnectorFactory;
    }

    public DBStandingOrderDao createDao(String aDBName) {
        return new DBStandingOrderDao(dbConnectorFactory.createCouchDbConnector(aDBName));
    }

}
