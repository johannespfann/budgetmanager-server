package de.pfann.budgetmanager.server.restservices.resources;

import de.pfann.budgetmanager.server.common.facade.AccountFacade;
import de.pfann.budgetmanager.server.common.facade.EntryFacade;
import de.pfann.budgetmanager.server.common.facade.StandingOrderFacade;
import de.pfann.budgetmanager.server.common.facade.UserFacade;
import de.pfann.budgetmanager.server.model.Account;
import de.pfann.budgetmanager.server.model.Entry;
import de.pfann.budgetmanager.server.model.StandingOrder;
import de.pfann.budgetmanager.server.model.User;

import java.util.List;

public class AdminResourceFacade {

    private UserFacade userFacade;
    private AccountFacade accountFacade;
    private EntryFacade entryFacade;
    private StandingOrderFacade standingOrderFacade;

    public AdminResourceFacade(UserFacade aUserFacade, AccountFacade aAccountFacade, EntryFacade aEntryFacade, StandingOrderFacade aStandingOrderFacade) {
        userFacade = aUserFacade;
        accountFacade = aAccountFacade;
        entryFacade = aEntryFacade;
        standingOrderFacade = aStandingOrderFacade;
    }

    public List<User> allUsers() {
        return userFacade.getAllUser();
    }

    public List<Account> allAccountsByUser(String aOwner) {
        return accountFacade.getKontos(aOwner);
    }

    public List<Entry> allEntriesByUserAndAccount(String aOwner, String aAccount) {
        try {
            Account account = accountFacade.getAccount(aOwner, aAccount);
            List<Entry> entries = entryFacade.getEntries(account);
            return entries;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public List<StandingOrder> allStandingOrdersByUserAndAccount(String aOwner, String aAccount) {
        try {
            Account account = accountFacade.getAccount(aOwner, aAccount);
            return standingOrderFacade.getStandingOrders(account);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
