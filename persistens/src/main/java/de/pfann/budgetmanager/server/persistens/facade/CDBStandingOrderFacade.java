package de.pfann.budgetmanager.server.persistens.facade;

import de.pfann.budgetmanager.server.common.facade.StandingOrderFacade;
import de.pfann.budgetmanager.server.model.Account;
import de.pfann.budgetmanager.server.model.StandingOrder;
import de.pfann.budgetmanager.server.persistens.dao.CDBStandingOrderDao;
import de.pfann.budgetmanager.server.persistens.dao.CDBStandingOrderDaoFactory;
import de.pfann.budgetmanager.server.persistens.model.CDBKonto;
import de.pfann.budgetmanager.server.persistens.util.CDBKontoDatabaseId;
import de.pfann.budgetmanager.server.persistens.util.CDBStandingOrderId;

import java.util.List;

public class CDBStandingOrderFacade implements StandingOrderFacade {

    private CDBStandingOrderDaoFactory standingOrderFactory;

    public CDBStandingOrderFacade(final CDBStandingOrderDaoFactory aFactory) {
        standingOrderFactory = aFactory;
    }

    @Override
    public void persist(Account aAccount, StandingOrder aStandingOrder) {
        CDBStandingOrderDao cdbStandingOrderDao = createStandingOrderDao(aAccount);
        CDBStandingOrderId standingOrderId = CDBStandingOrderId.createBuilder()
                .withHash(aStandingOrder.getHash())
                .withUsername(aAccount.getOwner())
                .build();
        aStandingOrder.setId(standingOrderId.toString());
        cdbStandingOrderDao.add(aStandingOrder);
    }

    @Override
    public void update(Account aAccount, StandingOrder aStandingOrder) {
        CDBStandingOrderDao cdbStandingOrderDao = createStandingOrderDao(aAccount);
        StandingOrder standingOrder = getStandingOrder(aAccount, aStandingOrder.getHash());
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
        CDBStandingOrderDao standingOrderDao = createStandingOrderDao(aAccount);
        standingOrderDao.remove(getStandingOrder(aAccount, aHash));
    }

    @Override
    public List<StandingOrder> getStandingOrders(Account aAccount) {
        CDBStandingOrderDao standingOrderDao = createStandingOrderDao(aAccount);
        return standingOrderDao.getAll();
    }

    @Override
    public StandingOrder getStandingOrder(Account aAccount, String aHash) {
        CDBStandingOrderDao standingOrderDao = createStandingOrderDao(aAccount);
        CDBStandingOrderId standingOrderId = CDBStandingOrderId.createBuilder()
                .withHash(aHash)
                .withUsername(aAccount.getOwner())
                .build();
        return standingOrderDao.get(standingOrderId.toString());
    }

    private CDBStandingOrderDao createStandingOrderDao(Account aAccount) {
        CDBKontoDatabaseId kontoDatabaseId = CDBKontoDatabaseId.builder()
                .withUsername(aAccount.getOwner())
                .withKontoHash(aAccount.getHash())
                .withObjectTyp(CDBKonto.ORDER_KONTO)
                .build();
        return standingOrderFactory.createDao(kontoDatabaseId.toString());
    }
}
