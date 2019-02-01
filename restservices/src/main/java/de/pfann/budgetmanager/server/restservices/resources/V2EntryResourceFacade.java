package de.pfann.budgetmanager.server.restservices.resources;

import de.pfann.budgetmanager.server.common.facade.AccountFacade;
import de.pfann.budgetmanager.server.common.facade.Entry2Facade;
import de.pfann.budgetmanager.server.model.Account;
import de.pfann.budgetmanager.server.model.Entry;

import java.util.List;

public class V2EntryResourceFacade {

    private AccountFacade accountFacade;
    private Entry2Facade entryFacade;

    public V2EntryResourceFacade(AccountFacade aAccountFacade, Entry2Facade aEntryFacade){
        accountFacade = aAccountFacade;
        entryFacade = aEntryFacade;
    }

    public List<Entry> getEntries(String aOwner, String aAccountHash){
        try{
            Account account = accountFacade.getAccount(aOwner, aAccountHash);
            List<Entry> entries = entryFacade.getEntries(account);
            return entries;
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    public void addEntry(String aOwner, String aAccountHash, Entry aEntry){
        try{
            Account account = accountFacade.getAccount(aOwner, aAccountHash);
            entryFacade.save(account,aEntry);
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    public void updateEntry(String aOwner, String aAccountHash, Entry aEntry){
        try{
            Account account = accountFacade.getAccount(aOwner, aAccountHash);
            entryFacade.update(account,aEntry);
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    public void deleteEntry(String aOwner, String aAccountHash, String aEntryHash){
        try{
            Account account = accountFacade.getAccount(aOwner, aAccountHash);
            // TODO compare owner and entry-user
            entryFacade.delete(account, aEntryHash);
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }


}
