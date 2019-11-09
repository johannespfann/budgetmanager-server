package de.pfann.budgetmanager.server.restservices.resources;

import de.pfann.budgetmanager.server.model.Entry;
import de.pfann.budgetmanager.server.restservices.resources.core.CrossOriginFilter;
import de.pfann.budgetmanager.server.restservices.resources.core.Logged;
import de.pfann.budgetmanager.server.restservices.resources.core.Secured;
import de.pfann.budgetmanager.server.restservices.resources.util.EntryJsonMapper;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("entries/")
public class EntryResource {


    private EntryResourceFacade entryResourceFacade;

    public EntryResource(EntryResourceFacade aEntryResourceFacade) {
        entryResourceFacade = aEntryResourceFacade;
    }

    @GET
    @Logged
    @CrossOriginFilter
    @Produces(MediaType.APPLICATION_JSON)
    @Path("owner/{owner}/account/{account}/all")
    public String getEntries(
            @PathParam("owner") String aOwner,
            @PathParam("account") String aAccountHash) {
        List<Entry> entries = entryResourceFacade.getEntries(aOwner, aAccountHash);
        String json = EntryJsonMapper.convertToJson(entries);
        return json;
    }

    @POST
    @Logged
    @Secured
    @CrossOriginFilter
    @Path("owner/{owner}/account/{account}/add/list")
    @Consumes(MediaType.APPLICATION_JSON)
    public void addEntries(
            @PathParam("owner") String aOwner,
            @PathParam("account") String aAccountHash,
            String aBody) {
        List<Entry> entries = EntryJsonMapper.convertToEntries(aBody);
        entryResourceFacade.addEntries(aOwner, aAccountHash, entries);
    }

    @POST
    @Logged
    @Secured
    @CrossOriginFilter
    @Path("owner/{owner}/account/{account}/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public void addEntry(
            @PathParam("owner") String aOwner,
            @PathParam("account") String aAccountHash,
            String aBody) {
        Entry entry = EntryJsonMapper.convertToEntry(aBody);
        entryResourceFacade.addEntry(aOwner, aAccountHash, entry);
    }

    @PATCH
    @Logged
    @Secured
    @CrossOriginFilter
    @Path("owner/{owner}/account/{account}/update")
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateEntry(
            @PathParam("owner") String aOwner,
            @PathParam("account") String aAccountHash,
            String aEntry) {
        Entry entry = EntryJsonMapper.convertToEntry(aEntry);
        entryResourceFacade.updateEntry(aOwner, aAccountHash, entry);
    }

    @DELETE
    @Logged
    @Secured
    @CrossOriginFilter
    @Path("owner/{owner}/account/{account}/delete/{hash}")
    public void deleteEntry(
            @PathParam("owner") String aOwner,
            @PathParam("account") String aAccountHash,
            @PathParam("hash") String aHash) {
        entryResourceFacade.deleteEntry(aOwner, aAccountHash, aHash);
    }

}
