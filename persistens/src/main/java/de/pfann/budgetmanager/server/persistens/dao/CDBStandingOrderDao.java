package de.pfann.budgetmanager.server.persistens.dao;

import de.pfann.budgetmanager.server.model.StandingOrder;
import org.ektorp.CouchDbConnector;
import org.ektorp.support.CouchDbRepositorySupport;

public class CDBStandingOrderDao extends CouchDbRepositorySupport<StandingOrder> {

    protected CDBStandingOrderDao(CouchDbConnector db) {
        super(StandingOrder.class, db, true);
    }

}
