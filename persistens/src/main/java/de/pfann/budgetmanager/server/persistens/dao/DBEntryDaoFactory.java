package de.pfann.budgetmanager.server.persistens.dao;

import de.pfann.budgetmanager.server.persistens.core.DbConnectorFactory;

public class DBEntryDaoFactory {

    private DbConnectorFactory dbConnectorFactory;

    public DBEntryDaoFactory(DbConnectorFactory aConnectorFactory) {
        dbConnectorFactory = aConnectorFactory;
    }

    public DBEntryDao createDao(String aName) {
        return new DBEntryDao(dbConnectorFactory.createCouchDbConnector(aName));
    }
}
