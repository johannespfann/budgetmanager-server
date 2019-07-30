package de.pfann.budgetmanager.server.persistenscouchdb.facade;

import de.pfann.budgetmanager.server.common.facade.AccountFacade;
import de.pfann.budgetmanager.server.model.Account;
import de.pfann.budgetmanager.server.model.User;
import de.pfann.budgetmanager.server.persistenscouchdb.dao.CDBUserDao;
import de.pfann.budgetmanager.server.persistenscouchdb.util.CDBUserId;

import java.util.LinkedList;
import java.util.List;

public class CDBAccountFacade implements AccountFacade {

    private CDBUserDao CDBUserDao;

    public CDBAccountFacade(CDBUserDao aCDBUserDao) {
        CDBUserDao = aCDBUserDao;
    }


    @Override
    public List<Account> getKontos(String aIdentifier) {
        List<Account> kontos = new LinkedList<>();
        User user = findUser(aIdentifier);

        List<Account> ownKontos = user.getKontos();
        List<Account> foreignKontos = user.getForeignKontos();

        kontos.addAll(ownKontos);
        kontos.addAll(foreignKontos);

        return kontos;
    }

    @Override
    public Account getAccount(String aUsername, String aAccountHash) {
        List<Account> kontos = new LinkedList<>();
        User user = findUser(aUsername);

        for(Account account : user.getKontos()) {
            if(account.getHash().equals(aAccountHash)){
                return account;
            }
        }

        throw new NoAccountFoundException();
    }

    @Override
    public void addAccount(String aOwner, Account aAccount) {
        User user = findUser(aOwner);
        user.getKontos().add(aAccount);
        CDBUserDao.update(user);
    }

    @Override
    public void deleteAccount(String aOwner, String aAccountHash) {
        User user = findUser(aOwner);

        List<Account> accounts = user.getKontos();

        if(accountExists(accounts,aAccountHash)){
            accounts.remove(getAccountByHash(accounts,aAccountHash));
            // TODO delete Account in foreign user

            user.setKontos(accounts);
            CDBUserDao.update(user);

            return;
        }

        throw new RuntimeException("Cannot delete Account");
    }


    private boolean accountExists(List<Account> aAccounts, String aAccountHash) {
        for(Account account : aAccounts) {
            if(account.getHash().equals(aAccountHash)){
                return true;
            }
        }
        return false;
    }

    private Account getAccountByHash(List<Account> aAccounts, String aAccountHash) {
        for(Account account : aAccounts) {
            if(account.getHash().equals(aAccountHash)){
                return account;
            }
        }
        throw new RuntimeException("Dont found account with hash:" + aAccountHash);
    }



    private User findUser(String aUserName) {
        CDBUserId userId = CDBUserId.create(aUserName);
        User user = CDBUserDao.get(userId.toString());
        return user;
    }
}
