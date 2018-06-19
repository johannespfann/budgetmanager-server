package de.pfann.budgetmanager.server.restservices.resources;

import de.pfann.budgetmanager.server.common.model.AppUser;
import de.pfann.budgetmanager.server.common.model.Entry;
import de.pfann.budgetmanager.server.persistens.daos.AppUserSQLFacade;
import de.pfann.budgetmanager.server.persistens.daos.EntrySQLFacade;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class EntryResourceFacade {

    private AppUserSQLFacade userFacade;

    private EntrySQLFacade entryFacade;

    public EntryResourceFacade(){
        userFacade = new AppUserSQLFacade();
        entryFacade = new EntrySQLFacade();
    }

    public List<Entry> getEntries(String aOwner){
        try{
            AppUser user = userFacade.getUserByNameOrEmail(aOwner);
            Set<Entry> entries = entryFacade.getEntries(user);

            List<Entry> convertEntries = new LinkedList<>();

            for(Entry entry : entries){
                convertEntries.add(entry);
            }

            return convertEntries;
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    public void addEntry(String aOwner, Entry aEntry){
        try{
            AppUser user = userFacade.getUserByNameOrEmail(aOwner);
            aEntry.setAppUser(user);
            entryFacade.persistEntry(aEntry);
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    public void updateEntry(String aOwner, Entry aEntry){
        try{
            AppUser user = userFacade.getUserByNameOrEmail(aOwner);
            aEntry.setAppUser(user);

            entryFacade.update(aEntry);
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    public void deleteEntry(String aOwner, String aHash){
        try{
            AppUser user = userFacade.getUserByNameOrEmail(aOwner);
            // TODO compare owner and entry-user
            Entry entry = entryFacade.getEntry(aHash);
            entryFacade.deleteEntry(entry);
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }


}
