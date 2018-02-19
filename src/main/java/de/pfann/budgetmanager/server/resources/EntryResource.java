package de.pfann.budgetmanager.server.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.pfann.budgetmanager.server.model.AppUser;
import de.pfann.budgetmanager.server.model.Category;
import de.pfann.budgetmanager.server.model.Entry;
import de.pfann.budgetmanager.server.persistens.daos.*;
import de.pfann.budgetmanager.server.resources.core.Logged;
import de.pfann.budgetmanager.server.resources.core.AllowCrossOrigin;
import de.pfann.budgetmanager.server.util.LogUtil;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;

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
    @AllowCrossOrigin
    @Produces(MediaType.APPLICATION_JSON)
    @Path("owner/{owner}/all")
    public List<Entry> getEntries(
            @PathParam("owner") String aOwner){
        AppUser user = userFacade.getUserByNameOrEmail(aOwner);
        List<Entry> entries = entryFacade.getEntries(user);
        return entries;
    }

    @POST
    @Logged
    @AllowCrossOrigin
    @Path("owner/{owner}/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public void addEntry(
            @PathParam("owner") String aOwner,
            Entry aEntry){
        AppUser user = userFacade.getUserByNameOrEmail(aOwner);

        Category category = categoryFacade.getCategory(aEntry.getCategory().getHash());

        aEntry.setAppUser(user);
        aEntry.setCategory(category);

        entryFacade.addEntry(aEntry);

    }

    private String getEntry(String aBody) {
        return aBody;
    }
}
