package de.pfann.budgetmanager.server.restservices.resources;

import de.pfann.budgetmanager.server.common.model.StandingOrder;
import de.pfann.budgetmanager.server.restservices.resources.core.CrossOriginFilter;
import de.pfann.budgetmanager.server.restservices.resources.core.Logged;
import de.pfann.budgetmanager.server.restservices.resources.core.Secured;
import de.pfann.budgetmanager.server.restservices.resources.util.RotationEntryJsonMapper;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("jobs/")
public class RotationEntryResource {

    private RotationEntryResourceFacade rotationEntryResourceFacade;

    public RotationEntryResource(RotationEntryResourceFacade aRotationEntryResourceFacade){
        rotationEntryResourceFacade = aRotationEntryResourceFacade;
    }

    @GET
    @Logged
    @Secured
    @CrossOriginFilter
    @Produces(MediaType.APPLICATION_JSON)
    @Path("owner/{owner}/all")
    public String getRotationEntries(
            @PathParam("owner") String aOwner
            ){
        List<StandingOrder> rotationEntries = rotationEntryResourceFacade.getRotationEntries(aOwner);
        String json = RotationEntryJsonMapper.convertToJson(rotationEntries);
        return json;
    }

    @POST
    @Logged
    @Secured
    @CrossOriginFilter
    @Path("owner/{owner}/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public void addRotationEntry(
            @PathParam("owner") String aOwner,
            String aRotationEntry){
        StandingOrder entry = RotationEntryJsonMapper.convertToEntry(aRotationEntry);
        rotationEntryResourceFacade.addRotationEntry(aOwner,entry);
    }

    @DELETE
    @Logged
    @Secured
    @CrossOriginFilter
    @Path("owner/{owner}/delete/{hash}")
    public void deleteRotationEntry(
            @PathParam("owner") String aOwner,
            @PathParam("hash") String aHash){
        rotationEntryResourceFacade.deleteRotationEntry(aOwner,aHash);
    }

    @PATCH
    @Logged
    @Secured
    @CrossOriginFilter
    @Path("owner/{owner}/update")
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateRotationEntry(
            @PathParam("owner") String aOwner,
            String aRotationEntry){
        StandingOrder entry = RotationEntryJsonMapper.convertToEntry(aRotationEntry);
        rotationEntryResourceFacade.updateRotationEntry(aOwner,entry);
    }
}
