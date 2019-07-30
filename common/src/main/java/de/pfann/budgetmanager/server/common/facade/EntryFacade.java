package de.pfann.budgetmanager.server.common.facade;

import de.pfann.budgetmanager.server.model.Account;
import de.pfann.budgetmanager.server.model.User;
import de.pfann.budgetmanager.server.model.Entry;

import java.util.List;

public interface EntryFacade {

    void save(Account aAccount, Entry aEntry);

    void delete(Account aAccount, String aHash);

    void update(Account aAccount, Entry aEntry);

    List<Entry> getEntries(Account aAccount);

    Entry getEntry(Account aAccount, String aHash);

}
