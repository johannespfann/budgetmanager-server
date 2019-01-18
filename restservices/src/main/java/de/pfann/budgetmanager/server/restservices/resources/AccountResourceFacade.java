package de.pfann.budgetmanager.server.restservices.resources;

import de.pfann.budgetmanager.server.common.facade.AccountFacade;
import de.pfann.budgetmanager.server.model.Account;

import java.util.List;

public class AccountResourceFacade {

    private AccountFacade accountFacade;

    public AccountResourceFacade(AccountFacade aAccountFacade) {
        accountFacade = aAccountFacade;
    }

    List<Account> getAccounts(String aIdentifier) {
        return accountFacade.getKontos(aIdentifier);
    }


}
