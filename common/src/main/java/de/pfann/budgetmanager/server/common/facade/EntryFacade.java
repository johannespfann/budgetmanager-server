package de.pfann.budgetmanager.server.common.facade;

import de.pfann.budgetmanager.server.common.model.AppUser;
import de.pfann.budgetmanager.server.common.model.Entry;
import de.pfann.budgetmanager.server.common.model.Tag;

import java.util.Set;

public interface EntryFacade {
    Set<Entry> getEntries(AppUser aUser);

    Set<Entry> getEntries(Tag aTag);

    void persistEntry(Entry aEntry);

    void deleteEntry(Entry aEntry);

    Entry getEntry(String aHash);

    void update(Entry aEntry);
}