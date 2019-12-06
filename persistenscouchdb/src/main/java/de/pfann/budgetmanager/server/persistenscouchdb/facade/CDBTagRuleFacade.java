package de.pfann.budgetmanager.server.persistenscouchdb.facade;

import de.pfann.budgetmanager.server.common.facade.TagRuleFacade;
import de.pfann.budgetmanager.server.model.Account;
import de.pfann.budgetmanager.server.model.TagRule;
import de.pfann.budgetmanager.server.model.User;
import de.pfann.budgetmanager.server.persistenscouchdb.dao.CDBUserDao;
import de.pfann.budgetmanager.server.persistenscouchdb.util.CDBUserId;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class CDBTagRuleFacade implements TagRuleFacade {

    private CDBUserDao CDBUserDao;

    public CDBTagRuleFacade(CDBUserDao aCDBUserDao) {
        CDBUserDao = aCDBUserDao;
    }

    @Override
    public List<TagRule> getTagRules(String aOwner, String aAccountHash) {
        List<TagRule> tagRules = new LinkedList<>();
        User user = findUser(aOwner);
        for(Account account : user.getKontos()) {
            if(account.getHash().equals(aAccountHash)){
                return account.getTagrules();
            }
        }
        return tagRules;
    }

    @Override
    public void addTagRule(String aOwner, String aAccountHash, TagRule aTagRule) {
        User user = findUser(aOwner);

        for(Account account : user.getKontos()) {
            if(account.getHash().equals(aAccountHash)){
                account.getTagrules().add(aTagRule);
            }
        }

        CDBUserDao.update(user);
    }

    @Override
    public void deleteTagRule(String aOwner, String aAccountHash, String aThenTagName) {
        User user = findUser(aOwner);
        List<Account> accounts = user.getKontos();

        if(accountExists(accounts,aAccountHash)){


            List<TagRule> newTagRules = getAccountByHash(accounts, aAccountHash)
                    .getTagrules()
                    .stream()
                    .filter( tagRule -> { return !tagRule.getWhenTag().contentEquals(aThenTagName); })
                    .collect(Collectors.toList());

            Account oldAccount = getAccountByHash(accounts, aAccountHash);


            Account newAccount = Account.copyAccount(oldAccount).withTagRules(newTagRules).build();

            // TODO update alles accounts

            CDBUserDao.update(user);

            return;
        }



        throw new RuntimeException("Cannot delete Tagrule");

    }

    @Override
    public void updateTagRule(String aOwner, String aAccountHash, TagRule aTagRule) {

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
