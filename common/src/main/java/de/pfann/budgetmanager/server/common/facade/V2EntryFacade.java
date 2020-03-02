package de.pfann.budgetmanager.server.common.facade;

import de.pfann.budgetmanager.server.model.Account;
import de.pfann.budgetmanager.server.model.V2Entry;

import java.util.List;

public interface V2EntryFacade {

    void save(Account aAccount, V2Entry aEntry);

    void delete(Account aAccount, String aHash);

    void update(Account aAccount, V2Entry aEntry);

    List<V2Entry> getEntries(Account aAccount);

    V2Entry getEntry(Account aAccount, String aHash);

}
