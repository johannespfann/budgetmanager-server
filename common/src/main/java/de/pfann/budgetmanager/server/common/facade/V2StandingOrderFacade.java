package de.pfann.budgetmanager.server.common.facade;

import de.pfann.budgetmanager.server.model.Account;
import de.pfann.budgetmanager.server.model.V2StandingOrder;

import java.util.List;

public interface V2StandingOrderFacade {

    void persist(Account aAccount, V2StandingOrder aStandingOrder);

    void update(Account aAccount, V2StandingOrder aStandingOrder);

    void delete(Account aAccount, String aHash);

    List<V2StandingOrder> getStandingOrders(Account aAccount);

    V2StandingOrder getStandingOrder(Account aAccount, String aHash);

}
