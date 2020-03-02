package de.pfann.budgetmanager.server.persistens.facade;

import de.pfann.budgetmanager.server.common.facade.V2StandingOrderFacade;
import de.pfann.budgetmanager.server.model.Account;
import de.pfann.budgetmanager.server.model.V2StandingOrder;
import de.pfann.budgetmanager.server.persistens.dao.DBStandingOrderDao;
import de.pfann.budgetmanager.server.persistens.dao.DBStandingOrderDaoFactory;
import de.pfann.budgetmanager.server.persistens.model.CDBKonto;
import de.pfann.budgetmanager.server.persistens.model.CDBStandingOrder;
import de.pfann.budgetmanager.server.persistens.model.CDBStandingOrderFactory;
import de.pfann.budgetmanager.server.persistens.util.CDBKontoDatabaseId;
import de.pfann.budgetmanager.server.persistens.util.CDBStandingOrderId;

import java.util.LinkedList;
import java.util.List;

public class DBStandingOrderFacade implements V2StandingOrderFacade {

    private DBStandingOrderDaoFactory standingOrderFactory;

    public DBStandingOrderFacade(final DBStandingOrderDaoFactory aFactory) {
        standingOrderFactory = aFactory;
    }

    @Override
    public void persist(Account aAccount, V2StandingOrder aStandingOrder) {
        DBStandingOrderDao DBStandingOrderDao = createStandingOrderDao(aAccount);
        CDBStandingOrderId standingOrderId = CDBStandingOrderId.createBuilder()
                .withHash(aStandingOrder.getHash())
                .withUsername(aAccount.getOwner())
                .build();
        CDBStandingOrder newCDBStandingOrder = CDBStandingOrderFactory.createCDBStandingOrder(aStandingOrder);
        newCDBStandingOrder.setId(standingOrderId.toString());
        DBStandingOrderDao.add(newCDBStandingOrder);
    }

    @Override
    public void update(Account aAccount, V2StandingOrder aStandingOrder) {
        DBStandingOrderDao DBStandingOrderDao = createStandingOrderDao(aAccount);
        CDBStandingOrder oldStandingOrder = getCDBStandingOrder(aAccount, aStandingOrder.getHash());
        CDBStandingOrder newStandingOrder = CDBStandingOrderFactory.updateCDBStandingOrder(oldStandingOrder, aStandingOrder);
        DBStandingOrderDao.update(newStandingOrder);
    }

    @Override
    public void delete(Account aAccount, String aHash) {
        DBStandingOrderDao standingOrderDao = createStandingOrderDao(aAccount);
        CDBStandingOrder cdbStandingOrder = getCDBStandingOrder(aAccount, aHash);
        standingOrderDao.remove(cdbStandingOrder);
    }

    @Override
    public List<V2StandingOrder> getStandingOrders(Account aAccount) {
        DBStandingOrderDao standingOrderDao = createStandingOrderDao(aAccount);
        List<V2StandingOrder> v2StandingOrders = new LinkedList<>();
        for (CDBStandingOrder cdbStandingOrder : standingOrderDao.getAll()) {
            V2StandingOrder v2StandingOrder = CDBStandingOrderFactory.createStandingOrder(cdbStandingOrder);
            v2StandingOrders.add(v2StandingOrder);
        }
        return v2StandingOrders;
    }

    @Override
    public V2StandingOrder getStandingOrder(Account aAccount, String aHash) {
        DBStandingOrderDao standingOrderDao = createStandingOrderDao(aAccount);
        CDBStandingOrderId standingOrderId = CDBStandingOrderId.createBuilder()
                .withHash(aHash)
                .withUsername(aAccount.getOwner())
                .build();
        V2StandingOrder v2StandingOrder = CDBStandingOrderFactory.createStandingOrder(
                standingOrderDao.get(standingOrderId.toString()));
        return v2StandingOrder;
    }

    public CDBStandingOrder getCDBStandingOrder(Account aAccount, String aHash) {
        DBStandingOrderDao standingOrderDao = createStandingOrderDao(aAccount);
        CDBStandingOrderId standingOrderId = CDBStandingOrderId.createBuilder()
                .withHash(aHash)
                .withUsername(aAccount.getOwner())
                .build();
        return standingOrderDao.get(standingOrderId.toString());
    }

    private DBStandingOrderDao createStandingOrderDao(Account aAccount) {
        CDBKontoDatabaseId kontoDatabaseId = CDBKontoDatabaseId.builder()
                .withUsername(aAccount.getOwner())
                .withKontoHash(aAccount.getHash())
                .withObjectTyp(CDBKonto.ORDER_KONTO)
                .build();
        return standingOrderFactory.createDao(kontoDatabaseId.toString());
    }

}
