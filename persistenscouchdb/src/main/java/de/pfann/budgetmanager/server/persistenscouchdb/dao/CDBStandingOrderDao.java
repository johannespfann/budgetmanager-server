package de.pfann.budgetmanager.server.persistenscouchdb.dao;

import de.pfann.budgetmanager.server.persistenscouchdb.model.CDBStandingOrder;
import org.ektorp.CouchDbConnector;
import org.ektorp.support.CouchDbRepositorySupport;

public class CDBStandingOrderDao extends CouchDbRepositorySupport<CDBStandingOrder> {

    protected CDBStandingOrderDao(CouchDbConnector db) {
        super(CDBStandingOrder.class, db, true);
    }

}
