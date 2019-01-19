package de.pfann.budgetmanager.server.persistenscouchdb.facade;

import de.pfann.budgetmanager.server.common.facade.AccountFacade;
import de.pfann.budgetmanager.server.model.Account;
import de.pfann.budgetmanager.server.model.User;
import de.pfann.budgetmanager.server.persistenscouchdb.dao.UserDao;
import de.pfann.budgetmanager.server.persistenscouchdb.util.CDBUserId;

import java.util.LinkedList;
import java.util.List;

public class CDBAccountFacade implements AccountFacade {

    private UserDao userDao;

    public CDBAccountFacade(UserDao aUserDao) {
        userDao = aUserDao;
    }


    @Override
    public List<Account> getKontos(String aIdentifier) {
        List<Account> kontos = new LinkedList<>();
        User user = createUser(aIdentifier);

        List<Account> ownKontos = user.getKontos();
        List<Account> foreignKontos = user.getForeignKontos();

        kontos.addAll(ownKontos);
        kontos.addAll(foreignKontos);

        return kontos;
    }

    @Override
    public void addAccount(String aOwner, Account aAccount) {
        User user = createUser(aOwner);
        user.getKontos().add(aAccount);
        userDao.update(user);
    }


    private User createUser(String aUserName) {
        CDBUserId userId = CDBUserId.create(aUserName);
        User user = userDao.get(userId.toString());
        return user;
    }
}
