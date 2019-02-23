package de.pfann.budgetmanager.server.persistenscouchdb.facade;

import de.pfann.budgetmanager.server.common.facade.StandingOrder2Facade;
import de.pfann.budgetmanager.server.model.Account;
import de.pfann.budgetmanager.server.model.StandingOrder;
import de.pfann.budgetmanager.server.persistenscouchdb.dao.V2CDBStandingOrderDao;
import de.pfann.budgetmanager.server.persistenscouchdb.dao.V2CDBStandingOrderDaoFactory;
import de.pfann.budgetmanager.server.persistenscouchdb.model.CDBKonto;
import de.pfann.budgetmanager.server.persistenscouchdb.util.CDBKontoDatabaseId;
import de.pfann.budgetmanager.server.persistenscouchdb.util.CDBStandingOrderId;

import java.util.List;

public class V2CDBStandingOrderFacade implements StandingOrder2Facade {

    private V2CDBStandingOrderDaoFactory standingOrderFactory;

    public V2CDBStandingOrderFacade(final V2CDBStandingOrderDaoFactory aFactory) {
        standingOrderFactory = aFactory;
    }

    @Override
    public void persist(Account aAccount, StandingOrder aStandingOrder) {
        V2CDBStandingOrderDao cdbStandingOrderDao = createStandingOrderDao(aAccount);
        CDBStandingOrderId standingOrderId = CDBStandingOrderId.createBuilder()
                .withHash(aStandingOrder.getHash())
                .withUsername(aAccount.getOwner())
                .build();
        aStandingOrder.setId(standingOrderId.toString());
        cdbStandingOrderDao.add(aStandingOrder);
    }

    @Override
    public void update(Account aAccount, StandingOrder aStandingOrder) {
        V2CDBStandingOrderDao cdbStandingOrderDao = createStandingOrderDao(aAccount);
        StandingOrder standingOrder = getStandingOrder(aAccount,aStandingOrder.getHash());
        StandingOrder newStandingOrder = new StandingOrder(
                aStandingOrder.getHash(),
                aStandingOrder.getUsername(),
                aStandingOrder.getData());
        newStandingOrder.setId(standingOrder.getId());
        newStandingOrder.setRev(standingOrder.getRev());
        cdbStandingOrderDao.update(newStandingOrder);
    }

    @Override
    public void delete(Account aAccount, String aHash) {
        V2CDBStandingOrderDao standingOrderDao = createStandingOrderDao(aAccount);
        standingOrderDao.remove(getStandingOrder(aAccount,aHash));
    }

    @Override
    public List<StandingOrder> getStandingOrders(Account aAccount) {
        V2CDBStandingOrderDao standingOrderDao = createStandingOrderDao(aAccount);
        return standingOrderDao.getAll();
    }

    @Override
    public StandingOrder getStandingOrder(Account aAccount, String aHash) {
        V2CDBStandingOrderDao standingOrderDao = createStandingOrderDao(aAccount);
        CDBStandingOrderId standingOrderId = CDBStandingOrderId.createBuilder()
                .withHash(aHash)
                .withUsername(aAccount.getOwner())
                .build();
        return standingOrderDao.get(standingOrderId.toString());
    }

    private V2CDBStandingOrderDao createStandingOrderDao(Account aAccount) {
        CDBKontoDatabaseId kontoDatabaseId = CDBKontoDatabaseId.builder()
                .withUsername(aAccount.getOwner())
                .withKontoHash(aAccount.getHash())
                .withObjectTyp(CDBKonto.ORDER_KONTO)
                .build();
        return standingOrderFactory.createDao(kontoDatabaseId.toString());
    }
}
