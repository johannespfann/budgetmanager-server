package de.pfann.budgetmanager.server.common.facade;

import de.pfann.budgetmanager.server.common.model.AppUser;
import de.pfann.budgetmanager.server.common.model.Entry;
import de.pfann.budgetmanager.server.common.model.Tag;

import java.util.List;
import java.util.Set;

public interface EntryFacade {

    List<Entry> getEntries(AppUser aUser);

    void persistEntry(Entry aEntry);

    void deleteEntry(AppUser aAppuser, Entry aEntry);

    Entry getEntry(AppUser aUser, String aHash);

    void update(Entry aEntry);
}
