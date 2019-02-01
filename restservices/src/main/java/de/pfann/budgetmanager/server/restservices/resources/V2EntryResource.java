package de.pfann.budgetmanager.server.restservices.resources;

import de.pfann.budgetmanager.server.model.Entry;
import de.pfann.budgetmanager.server.restservices.resources.core.CrossOriginFilter;
import de.pfann.budgetmanager.server.restservices.resources.core.Logged;
import de.pfann.budgetmanager.server.restservices.resources.core.Secured;
import de.pfann.budgetmanager.server.restservices.resources.util.V2EntryJsonMapper;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("entries/")
public class V2EntryResource {


    private V2EntryResourceFacade entryResourceFacade;

    public V2EntryResource(V2EntryResourceFacade aEntryResourceFacade) {
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
        System.out.println("Owner: " + aOwner);
        System.out.println("AccountHash: " + aAccountHash);
        List<Entry> entries = entryResourceFacade.getEntries(aOwner, aAccountHash);
        String json = V2EntryJsonMapper.convertToJson(entries);
        return json;
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

        System.out.println("Owner: " + aOwner);
        System.out.println("AccountHash: " + aAccountHash);
        System.out.println("Body: " + aBody);
        Entry entry = V2EntryJsonMapper.convertToEntry(aBody);
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
        Entry entry = V2EntryJsonMapper.convertToEntry(aEntry);
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
