package de.pfann.budgetmanager.server.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.pfann.budgetmanager.server.model.AppUser;
import de.pfann.budgetmanager.server.model.Category;
import de.pfann.budgetmanager.server.model.Entry;
import de.pfann.budgetmanager.server.model.Tag;
import de.pfann.budgetmanager.server.persistens.daos.*;
import de.pfann.budgetmanager.server.resources.core.Logged;
import de.pfann.budgetmanager.server.resources.core.AllowCrossOrigin;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Set;

@Path("entries/")
public class EntryResource {

    private AppUserFacade userFacade;

    private EntryFacade entryFacade;

    private CategoryFacade categoryFacade;

    private TagFacade tagFacade;

    private ObjectMapper mapper;

    public EntryResource(){
        userFacade = new AppUserFacade();
        entryFacade = new EntryFacade();
        categoryFacade = new CategoryFacade();
        tagFacade = new TagFacade();
        mapper = new ObjectMapper();
    }

    @GET
    @Logged
    @AllowCrossOrigin
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

        tagFacade.updateTagsWithUser(user,aEntry.getTags());

        List<Tag> persistedTagObjects = tagFacade.getPersistedTagObjects(user,aEntry.getTags());

        aEntry.setTags(persistedTagObjects);

        entryFacade.persistEntry(aEntry);

    }

    @DELETE
    @Logged
    @AllowCrossOrigin
    @Path("owner/{owner}/delete/{hash}")
    public void deleteEntry(
            @PathParam("owner") String aOwner,
            @PathParam("hash") String aHash){

        AppUser user = userFacade.getUserByNameOrEmail(aOwner);
        // TODO compare owner and entry-user
        Entry entry = entryFacade.getEntry(aHash);

        entryFacade.deleteEntry(entry);
    }


    private String getEntry(String aBody) {
        return aBody;
    }
}
