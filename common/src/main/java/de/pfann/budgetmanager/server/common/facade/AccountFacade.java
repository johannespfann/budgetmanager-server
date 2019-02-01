package de.pfann.budgetmanager.server.common.facade;

import de.pfann.budgetmanager.server.model.Account;
import de.pfann.budgetmanager.server.model.User;

import java.util.List;

public interface AccountFacade {

    List<Account> getKontos(String aUsername);

    Account getAccount(String aUsername, String aAccountHash);

    void addAccount(String aOwner, Account aAccount);

    void deleteAccount(String aOwner, String aAccountHash);
}
