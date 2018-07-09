package de.pfann.budgetmanager.server.common.facade;

import de.pfann.budgetmanager.server.common.model.AppUser;
import de.pfann.budgetmanager.server.common.model.RotationEntry;

import java.util.List;

public interface StandingOrderFacade {
    void save(RotationEntry aEntry);

    void update(RotationEntry aEntry);

    List<RotationEntry> getRotationEntries(AppUser aUser);

    RotationEntry getRotationEntryByHash(AppUser aAppUser, String aHash);

    void delete(RotationEntry aRotationEntry);

    // TODO wie mach ich die validierungsgeschichten ll
    // Wird noch ueberlegt
    void validateRotationEntry(RotationEntry aRotationEntry);
}
