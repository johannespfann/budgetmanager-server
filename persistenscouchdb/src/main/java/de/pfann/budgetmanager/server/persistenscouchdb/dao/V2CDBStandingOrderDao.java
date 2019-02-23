package de.pfann.budgetmanager.server.persistenscouchdb.dao;

import de.pfann.budgetmanager.server.model.StandingOrder;
import org.ektorp.CouchDbConnector;
import org.ektorp.support.CouchDbRepositorySupport;

public class V2CDBStandingOrderDao extends CouchDbRepositorySupport<StandingOrder> {

    protected V2CDBStandingOrderDao(CouchDbConnector db) {
        super(StandingOrder.class, db, true);
    }

}
