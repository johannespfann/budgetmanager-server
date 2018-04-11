package de.pfann.budgetmanager.server.core.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.pfann.budgetmanager.server.core.resources.core.CrossOriginFilter;
import de.pfann.budgetmanager.server.persistens.model.AppUser;
import de.pfann.budgetmanager.server.persistens.model.Entry;
import de.pfann.budgetmanager.server.persistens.daos.*;
import de.pfann.budgetmanager.server.core.resources.core.Logged;
import de.pfann.budgetmanager.server.common.util.LogUtil;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Set;

@Path("entries/")
public class EntryResource {

    private AppUserFacade userFacade;

    private EntryFacade entryFacade;

    private CategoryFacade categoryFacade;

    private ObjectMapper mapper;

    public EntryResource(){
        userFacade = new AppUserFacade();
        entryFacade = new EntryFacade();
        categoryFacade = new CategoryFacade();
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
        System.out.println(aEntry.getHash());
        System.out.println("Taganzahl: " + aEntry.getTags().size());

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

        LogUtil.info(this.getClass(),"Entry: ");
        LogUtil.info(this.getClass()," - " + aEntry.getHash());
        LogUtil.info(this.getClass()," - " + aEntry.getMemo());
        LogUtil.info(this.getClass()," - " + aEntry.getAmount());
        LogUtil.info(this.getClass()," - " + aEntry.getCreated_at());
        LogUtil.info(this.getClass()," - " + aEntry.getCategory().getName());


        entryFacade.update(aEntry);
    }


    @DELETE
    @Logged
    @CrossOriginFilter
    @Path("owner/{owner}/delete/{hash}")
    public void deleteEntry(
            @PathParam("owner") String aOwner,
            @PathParam("hash") String aHash){

        AppUser user = userFacade.getUserByNameOrEmail(aOwner);
        // TODO compare owner and entry-user
        Entry entry = entryFacade.getEntry(aHash);

        entryFacade.deleteEntry(entry);
    }


}
