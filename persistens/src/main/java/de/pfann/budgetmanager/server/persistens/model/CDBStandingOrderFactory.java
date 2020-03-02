package de.pfann.budgetmanager.server.persistens.model;

import de.pfann.budgetmanager.server.model.V2StandingOrder;

public class CDBStandingOrderFactory {

    public static CDBStandingOrder createCDBStandingOrder(V2StandingOrder aStandingOrder) {
        CDBStandingOrder cdbStandingOrder = new CDBStandingOrder();
        cdbStandingOrder.setHash(aStandingOrder.getHash());
        cdbStandingOrder.setUsername(aStandingOrder.getUsername());


        cdbStandingOrder.setRotation_strategy(aStandingOrder.getRotationStrategy());
        cdbStandingOrder.setStart_at(aStandingOrder.getStartAt());
        cdbStandingOrder.setEnd_at(aStandingOrder.getEndAt());
        cdbStandingOrder.setLast_executed(aStandingOrder.getLastExecuted());
        cdbStandingOrder.setLast_modified(aStandingOrder.getLastModified());

        cdbStandingOrder.setAmount(aStandingOrder.getAmount());
        cdbStandingOrder.setCurrency(aStandingOrder.getCurrency());
        cdbStandingOrder.setMemo(aStandingOrder.getMemo());
        cdbStandingOrder.setTags(aStandingOrder.getTags());

        return cdbStandingOrder;
    }

    public static CDBStandingOrder updateCDBStandingOrder(CDBStandingOrder aCdbStandingOrder, V2StandingOrder aStandingOrder) {

        CDBStandingOrder cdbStandingOrder = new CDBStandingOrder();

        cdbStandingOrder.setId(aCdbStandingOrder.getId());
        cdbStandingOrder.setRev(aCdbStandingOrder.getRev());

        cdbStandingOrder.setHash(aStandingOrder.getHash());
        cdbStandingOrder.setUsername(aStandingOrder.getUsername());

        cdbStandingOrder.setRotation_strategy(aStandingOrder.getRotationStrategy());
        cdbStandingOrder.setStart_at(aStandingOrder.getStartAt());
        cdbStandingOrder.setEnd_at(aStandingOrder.getEndAt());
        cdbStandingOrder.setLast_executed(aStandingOrder.getLastExecuted());
        cdbStandingOrder.setLast_modified(aStandingOrder.getLastModified());

        cdbStandingOrder.setAmount(aStandingOrder.getAmount());
        cdbStandingOrder.setCurrency(aStandingOrder.getCurrency());
        cdbStandingOrder.setMemo(aStandingOrder.getMemo());
        cdbStandingOrder.setTags(aStandingOrder.getTags());

        return cdbStandingOrder;
    }

    public static V2StandingOrder createStandingOrder(CDBStandingOrder aCdbStandingOrder) {
        V2StandingOrder standingOrder = new V2StandingOrder(
                aCdbStandingOrder.getHash(),
                aCdbStandingOrder.getUsername(),
                aCdbStandingOrder.getRotation_strategy(),
                aCdbStandingOrder.getStart_at(),
                aCdbStandingOrder.getEnd_at(),
                aCdbStandingOrder.getLast_executed(),
                aCdbStandingOrder.getLast_modified(),
                aCdbStandingOrder.getAmount(),
                aCdbStandingOrder.getCurrency(),
                aCdbStandingOrder.getMemo(),
                aCdbStandingOrder.getTags());
        return standingOrder;
    }
}
