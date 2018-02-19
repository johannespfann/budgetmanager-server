package de.pfann.budgetmanager.server.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.pfann.budgetmanager.server.model.AppUser;
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

    private ObjectMapper mapper;

    public EntryResource(){
        userFacade = new AppUserFacade();
        entryFacade = new EntryFacade();
        mapper = new ObjectMapper();
    }

    @GET
    @Logged
    @AllowCrossOrigin
    @Produces(MediaType.APPLICATION_JSON)
    @Path("owner/{owner}/all")
    public List<Entry> getEntries(
            @PathParam("owner") String aOwner){

        System.out.println("Start getEntry");
        AppUser user = userFacade.getUserByNameOrEmail(aOwner);

        List<Entry> entries = entryFacade.getEntries(user);

        entries.forEach( data -> {
            System.out.println(data.getHash());
        });


        return entries;
    }

    @POST
    @Logged
    @AllowCrossOrigin
    @Path("add/owner/{owner}")
    //@Consumes(MediaType.APPLICATION_JSON)
    public void addEntry(
            @PathParam("owner") String aAccessor,
            String aEntry){

        //String entryJSON = getEntry(aEntry);

        System.out.println(aEntry);
        AppUser user = userFacade.getUserByNameOrEmail(aAccessor);

        /*Entry entry = new Entry();

        try {
            entry = mapper.readValue(entryJSON,Entry.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        entry.setAppUser(user);

        entryFacade.addEntry(entry);

        return Response.ok()
                .build();
                */
    }

    private String getEntry(String aBody) {
        return aBody;
    }
}
