package de.pfann.budgetmanager.server.restservices.resources;

import de.pfann.budgetmanager.server.common.facade.AccountFacade;
import de.pfann.budgetmanager.server.common.facade.EntryFacade;
import de.pfann.budgetmanager.server.common.util.DateUtil;
import de.pfann.budgetmanager.server.model.Account;
import de.pfann.budgetmanager.server.model.Entry;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class EntryResourceFacade {

    private AccountFacade accountFacade;
    private EntryFacade entryFacade;

    public EntryResourceFacade(AccountFacade aAccountFacade, EntryFacade aEntryFacade){
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


    public void addEntries(String aOwner, String aAccountHash, List<Entry> aEntries) {
        try{
            Account account = accountFacade.getAccount(aOwner, aAccountHash);

            for(Entry entry: aEntries) {
                entryFacade.save(account, entry);
            }
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    public List<Entry> getLastSixMonthEntries(String aOwner, String aAccountHash) {
        try{

            LocalDate localDate = LocalDate.now().minusMonths(6);
            Account account = accountFacade.getAccount(aOwner, aAccountHash);

            System.out.println("Today is   : " + LocalDate.now());
            System.out.println("For 6 Month: " + localDate);

            List<Entry> entries = entryFacade.getEntries(account).stream()
                    .filter( entry -> entry.getCreatedAt().after(DateUtil.asDate(localDate)))
                    .collect(Collectors.toList());

            System.out.println("Last6Month: " + entries.size());
            return entries;
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }
}
