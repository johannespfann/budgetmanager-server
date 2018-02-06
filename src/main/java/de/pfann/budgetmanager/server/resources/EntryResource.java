package de.pfann.budgetmanager.server.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.pfann.budgetmanager.server.model.AppUser;
import de.pfann.budgetmanager.server.model.Entry;
import de.pfann.budgetmanager.server.persistens.daos.*;
import de.pfann.budgetmanager.server.resources.core.ModifyCrossOrigin;
import de.pfann.budgetmanager.server.util.LogUtil;
import jdk.nashorn.internal.runtime.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("entries/")
public class EntryResource {

    private AppUserFacade userFacade;

    private CategoryFacade categoryFacade;

    private EntryFacade entryFacade;

    private ObjectMapper mapper;

    public EntryResource(){
        categoryFacade = new CategoryFacade();
        userFacade = new AppUserFacade();
        entryFacade = new EntryFacade();
        mapper = new ObjectMapper();
    }

    @GET
    @Logger
    @ModifyCrossOrigin
    @Produces(MediaType.APPLICATION_JSON)
    @Path("all/{accessor")
    public Response getEntries(
            @PathParam("accessor") String aAccessor){

        LogUtil.info(this.getClass(),"Accessor:" + aAccessor);

        AppUser user = userFacade.getUserByNameOrEmail(aAccessor);

        List<Entry> entries = entryFacade.getEntries(user);

        LogUtil.info(this.getClass(),"Accessor:" + aAccessor);

        String entriesJSON = "[ ]";
        try {
            entriesJSON = mapper.writeValueAsString(entries);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return Response.ok().entity(entriesJSON).build();
    }
}
