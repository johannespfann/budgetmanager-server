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
        System.out.println(user);
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

                if(tagRuleAlreadyExists(account.getTagrules(), aTagRule)){
                    System.out.println("tagrule was duplicated");
                    return;
                }

                account.getTagrules().add(aTagRule);
            }
        }

        CDBUserDao.update(user);
    }

    private boolean tagRuleAlreadyExists(List<TagRule> tagrules, TagRule aTagRule) {
        return tagrules.stream()
                .filter( tagRule -> aTagRule.getWhenTag().trim().equals(tagRule.getWhenTag().trim()))
                .findAny()
                .isPresent();
    }

    @Override
    public void deleteTagRule(String aOwner, String aAccountHash, String aWhenTagName) {
        User user = findUser(aOwner);
        List<Account> accounts = user.getKontos();

        if(!accountExists(accounts,aAccountHash)) {
            throw new RuntimeException("No account found - can not delete tagrule");
        }


        List<TagRule> newTagRules = getAccountByHash(accounts, aAccountHash)
                .getTagrules()
                .stream()
                .filter( tagRule -> {
                    if(!tagRule.getWhenTag().trim().equals(aWhenTagName.trim())){
                        return true;
                    }
                    return false;
                })
                .collect(Collectors.toList());



        System.out.println("all tagrules without deleted ones: " + newTagRules);

        Account oldAccount = getAccountByHash(accounts, aAccountHash);

        System.out.println("Old Account " + oldAccount);

        System.out.println("Delete tagRules: " + aWhenTagName);
        Account newAccount = Account.copyAccount(oldAccount)
                .withTagRules(newTagRules)
                .build();

        System.out.println("Added new array of tags: " + newTagRules);

        System.out.println("New Account " + newAccount);

        System.out.println(aAccountHash);

        List<Account> otherAccounts = user.getKontos()
                .stream()
                .filter( acc -> {
                    System.out.println("Compare: " + acc.getHash());
                    System.out.println("with   : " + aAccountHash);
                    return !(acc.getHash().equals(aAccountHash));
                })
                .collect(Collectors.toList());

        System.out.println("After filtering");
        System.out.println(otherAccounts);

        otherAccounts.add(newAccount);

        System.out.println(otherAccounts);

        user.setKontos(otherAccounts);


        CDBUserDao.update(user);

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
