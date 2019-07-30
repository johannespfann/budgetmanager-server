package de.pfann.budgetmanager.server.common.facade;

import de.pfann.budgetmanager.server.model.Account;
import de.pfann.budgetmanager.server.model.StandingOrder;

import java.util.List;

public interface StandingOrderFacade {

    void persist(Account aAccount, StandingOrder aStandingOrder);

    void update(Account aAccount, StandingOrder aStandingOrder);

    void delete(Account aAccount, String aHash);

    List<StandingOrder> getStandingOrders(Account aAccount);

    StandingOrder getStandingOrder(Account aAccount, String aHash);

}
