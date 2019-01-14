package de.pfann.budgetmanager.server.common.facade;

import de.pfann.budgetmanager.server.model.User;
import de.pfann.budgetmanager.server.model.StandingOrder;

import java.util.List;

public interface StandingOrder2Facade {


    void save(StandingOrder aEntry);

    void update(StandingOrder aEntry);

    void delete(StandingOrder aRotationEntry);

    List<StandingOrder> getStandingOrders(User aUser);

    StandingOrder getStandingOrderByHash(User aAppUser, String aHash);

}
