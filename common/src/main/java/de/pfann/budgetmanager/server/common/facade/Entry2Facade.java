package de.pfann.budgetmanager.server.common.facade;

import de.pfann.budgetmanager.server.model.User;
import de.pfann.budgetmanager.server.model.Entry;

import java.util.List;

public interface Entry2Facade {

    void save(Entry aEntry);

    void delete(User aAppuser, Entry aEntry);

    void update(Entry aEntry);

    List<Entry> getEntries(User aUser);

    Entry getEntry(User aUser, String aHash);

}
