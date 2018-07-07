package de.pfann.budgetmanager.server.restservices.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.pfann.budgetmanager.server.common.model.Entry;
import de.pfann.budgetmanager.server.restservices.resources.core.CrossOriginFilter;
import de.pfann.budgetmanager.server.restservices.resources.core.Logged;
import de.pfann.budgetmanager.server.restservices.resources.util.EntryJsonMapper;

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
    public String getEntries(
            @PathParam("owner") String aOwner){
        System.out.println(aOwner);
        List<Entry> entries = entryResourceFacade.getEntries(aOwner);
        return EntryJsonMapper.convertToJson(entries);
    }

    @POST
    @Logged
    @CrossOriginFilter
    @Path("owner/{owner}/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public void addEntry(
            @PathParam("owner") String aOwner,
            String aBody){
        Entry entry = EntryJsonMapper.convertToEntry(aBody);
        entryResourceFacade.addEntry(aOwner,entry);
    }

    @PATCH
    @Logged
    @CrossOriginFilter
    @Path("owner/{owner}/update")
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateEntry(
            @PathParam("owner") String aOwner,
            String aEntry){
        Entry entry = EntryJsonMapper.convertToEntry(aEntry);
        entryResourceFacade.updateEntry(aOwner,entry);
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
