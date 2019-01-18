package de.pfann.budgetmanager.server.common.facade;

import de.pfann.budgetmanager.server.model.Account;

import java.util.List;

public interface AccountFacade {

    List<Account> getKontos(String aUsername);

}
