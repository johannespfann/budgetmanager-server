package de.pfann.budgetmanager.server.restservices.resources;

import de.pfann.budgetmanager.server.common.facade.AppUserFacade;
import de.pfann.budgetmanager.server.common.facade.EntryFacade;
import de.pfann.budgetmanager.server.common.model.AppUser;
import de.pfann.budgetmanager.server.common.model.Entry;

import java.util.LinkedList;
import java.util.List;

public class EntryResourceFacade {

    private AppUserFacade userFacade;
    private EntryFacade entryFacade;

    public EntryResourceFacade(AppUserFacade aAppUserFacade, EntryFacade aEntryFacade){
        userFacade = aAppUserFacade;
        entryFacade = aEntryFacade;
    }

    public List<Entry> getEntries(String aOwner){
        try{
            AppUser user = userFacade.getUserByNameOrEmail(aOwner);
            List<Entry> entries = entryFacade.getEntries(user);
            return entries;
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
            Entry entry = entryFacade.getEntry(user, aHash);
            entryFacade.deleteEntry(user, entry);
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }


}
