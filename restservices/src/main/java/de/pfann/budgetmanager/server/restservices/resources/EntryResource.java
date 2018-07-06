package de.pfann.budgetmanager.server.restservices.resources;

import de.pfann.budgetmanager.server.common.model.Entry;
import de.pfann.budgetmanager.server.restservices.resources.core.CrossOriginFilter;
import de.pfann.budgetmanager.server.restservices.resources.core.Logged;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("entries/")
public class EntryResource {

    private EntryResourceFacade entryResourceFacade;

    public EntryResource(EntryResourceFacade aEntryResourceFacade){
        entryResourceFacade = aEntryResourceFacade;
    }

    @GET
    @Logged
    @CrossOriginFilter
    @Produces(MediaType.APPLICATION_JSON)
    @Path("owner/{owner}/all")
    public List<Entry> getEntries(
            @PathParam("owner") String aOwner){
        return entryResourceFacade.getEntries(aOwner);
    }

    @POST
    @Logged
    @CrossOriginFilter
    @Path("owner/{owner}/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public void addEntry(
            @PathParam("owner") String aOwner,
            Entry aEntry){
        entryResourceFacade.addEntry(aOwner,aEntry);
    }

    @PATCH
    @Logged
    @CrossOriginFilter
    @Path("owner/{owner}/update")
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateEntry(
            @PathParam("owner") String aOwner,
            Entry aEntry){
        entryResourceFacade.updateEntry(aOwner,aEntry);
    }


    @DELETE
    @Logged
    @CrossOriginFilter
    @Path("owner/{owner}/delete/{hash}")
    public void deleteEntry(
            @PathParam("owner") String aOwner,
            @PathParam("hash") String aHash){
        entryResourceFacade.deleteEntry(aOwner,aHash);
    }

}
