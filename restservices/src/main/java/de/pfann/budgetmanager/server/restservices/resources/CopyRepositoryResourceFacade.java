package de.pfann.budgetmanager.server.restservices.resources;

import de.pfann.budgetmanager.server.common.facade.AccountFacade;
import de.pfann.budgetmanager.server.common.facade.V2EntryFacade;
import de.pfann.budgetmanager.server.common.facade.V2StandingOrderFacade;
import de.pfann.budgetmanager.server.model.Account;
import de.pfann.budgetmanager.server.model.V2Entry;
import de.pfann.budgetmanager.server.model.V2StandingOrder;

import java.util.List;

public class CopyRepositoryResourceFacade {

    private AccountFacade accountFacade;
    private V2EntryFacade entryFacade;
    private V2StandingOrderFacade standingOrderFacade;

    public CopyRepositoryResourceFacade(
            AccountFacade aAccountFacade,
            V2EntryFacade aEntryFacade,
            V2StandingOrderFacade aStandingOrderFacade) {
        accountFacade = aAccountFacade;
        entryFacade = aEntryFacade;
        standingOrderFacade = aStandingOrderFacade;
    }

    public void addEntries(String aOwner, String aAccountHash, List<V2Entry> aEntries) {
        try {
            Account account = accountFacade.getAccount(aOwner, aAccountHash);

            for (V2Entry entry : aEntries) {
                entry.setUsername(aOwner);
                System.out.println(entry);
                entryFacade.save(account, entry);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void addStandingOrders(String aOwner, String aAccountHash, List<V2StandingOrder> aStandingOrder) {
        try {
            Account account = accountFacade.getAccount(aOwner, aAccountHash);
            for (V2StandingOrder standingOrder : aStandingOrder) {
                standingOrder.setUsername(aOwner);
                System.out.println(standingOrder);
                standingOrderFacade.persist(account, standingOrder);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
