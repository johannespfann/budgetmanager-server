package de.pfann.budgetmanager.server.restservices.resources;

import de.pfann.budgetmanager.server.common.facade.AccountFacade;
import de.pfann.budgetmanager.server.common.facade.UserFacade;
import de.pfann.budgetmanager.server.model.Account;

import java.util.List;

public class AccountResourceFacade {

    private AccountFacade accountFacade;
    private UserFacade userFacade;

    public AccountResourceFacade(AccountFacade aAccountFacade, UserFacade aUserFacade) {
        accountFacade = aAccountFacade;
        userFacade = aUserFacade;
    }

    public List<Account> getAccounts(String aIdentifier) {
        return accountFacade.getKontos(aIdentifier);
    }


    public void addAccount(String aOwner, Account aAccount) {
        accountFacade.addAccount(aOwner, aAccount);
    }
}
