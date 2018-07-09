package de.pfann.budgetmanager.server.common.facade;

import de.pfann.budgetmanager.server.common.model.AppUser;
import de.pfann.budgetmanager.server.common.model.StandingOrder;

import java.util.List;

public interface StandingOrderFacade {
    void save(StandingOrder aEntry);

    void update(StandingOrder aEntry);

    List<StandingOrder> getRotationEntries(AppUser aUser);

    StandingOrder getRotationEntryByHash(AppUser aAppUser, String aHash);

    void delete(StandingOrder aRotationEntry);

    // TODO wie mach ich die validierungsgeschichten ll
    // Wird noch ueberlegt
    void validateRotationEntry(StandingOrder aRotationEntry);
}
