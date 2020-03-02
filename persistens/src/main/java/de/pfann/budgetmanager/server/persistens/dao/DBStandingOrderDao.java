package de.pfann.budgetmanager.server.persistens.dao;

import de.pfann.budgetmanager.server.persistens.model.CDBStandingOrder;
import org.ektorp.CouchDbConnector;
import org.ektorp.support.CouchDbRepositorySupport;

public class DBStandingOrderDao extends CouchDbRepositorySupport<CDBStandingOrder> {

    protected DBStandingOrderDao(CouchDbConnector db) {
        super(CDBStandingOrder.class, db, true);
    }

}
