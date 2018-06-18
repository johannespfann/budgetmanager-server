package de.pfann.budgetmanager.server.restservices.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.pfann.budgetmanager.server.common.model.AppUser;
import de.pfann.budgetmanager.server.common.model.Entry;
import de.pfann.budgetmanager.server.persistens.daos.AppUserSQLFacade;
import de.pfann.budgetmanager.server.persistens.daos.EntrySQLFacade;
import de.pfann.budgetmanager.server.restservices.resources.core.CrossOriginFilter;
import de.pfann.budgetmanager.server.restservices.resources.core.Logged;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Set;

@Path("entries/")
public class EntryResource {

    private AppUserSQLFacade userFacade;

    private EntrySQLFacade entryFacade;

    private ObjectMapper mapper;

    public EntryResource(){
        userFacade = new AppUserSQLFacade();
        entryFacade = new EntrySQLFacade();
        mapper = new ObjectMapper();
    }

    @GET
    @Logged
    @CrossOriginFilter
    @Produces(MediaType.APPLICATION_JSON)
    @Path("owner/{owner}/all")
    public Set<Entry> getEntries(
            @PathParam("owner") String aOwner){
        AppUser user = userFacade.getUserByNameOrEmail(aOwner);
        Set<Entry> entries = entryFacade.getEntries(user);
        return entries;
    }

    @POST
    @Logged
    @CrossOriginFilter
    @Path("owner/{owner}/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public void addEntry(
            @PathParam("owner") String aOwner,
            Entry aEntry){
        AppUser user = userFacade.getUserByNameOrEmail(aOwner);
        aEntry.setAppUser(user);
        entryFacade.persistEntry(aEntry);

    }

    @PATCH
    @Logged
    @CrossOriginFilter
    @Path("owner/{owner}/update")
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateEntry(
            @PathParam("owner") String aOwner,
            Entry aEntry){
        AppUser user = userFacade.getUserByNameOrEmail(aOwner);
        aEntry.setAppUser(user);

        entryFacade.update(aEntry);
    }


    @DELETE
    @Logged
    @CrossOriginFilter
    @Path("owner/{owner}/delete/{hash}")
    public void deleteEntry(
            @PathParam("owner") String aOwner,
            @PathParam("hash") String aHash){

        try {
            AppUser user = userFacade.getUserByNameOrEmail(aOwner);
            // TODO compare owner and entry-user
            Entry entry = entryFacade.getEntry(aHash);

            entryFacade.deleteEntry(entry);
        }catch (Exception e){
            e.printStackTrace();
        }

    }


}
