package de.pfann.budgetmanager.server.restservices.resources;

import de.pfann.budgetmanager.server.model.StandingOrder;
import de.pfann.budgetmanager.server.restservices.resources.core.CrossOriginFilter;
import de.pfann.budgetmanager.server.restservices.resources.core.Logged;
import de.pfann.budgetmanager.server.restservices.resources.core.Secured;
import de.pfann.budgetmanager.server.restservices.resources.util.StandingOrderJsonMapper;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("jobs/")
public class V2StandingOrderResource {

    private V2StandingOrderResourceFacade facade;

    public V2StandingOrderResource(V2StandingOrderResourceFacade aFacade) {
        facade = aFacade;
    }

    @GET
    @Logged
    //@Secured
    @CrossOriginFilter
    @Produces(MediaType.APPLICATION_JSON)
    @Path("owner/{owner}/account/{account}/all")
    public String getRotationEntries(
            @PathParam("owner") String aOwner,
            @PathParam("account") String aAccountHash){
        List<StandingOrder> rotationEntries = facade.getRotationEntries(aOwner, aAccountHash);
        String json = StandingOrderJsonMapper.convertToJson(rotationEntries);
        return json;
    }

    @POST
    @Logged
    @Secured
    @CrossOriginFilter
    @Path("owner/{owner}/account/{account}/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public void addRotationEntry(
            @PathParam("owner") String aOwner,
            @PathParam("account") String aAccountHash,
            String aRotationEntry){
        StandingOrder entry = StandingOrderJsonMapper.convertToEntry(aRotationEntry);
        facade.addRotationEntry(aOwner,aAccountHash, entry);
    }

    @POST
    @Logged
    @Secured
    @CrossOriginFilter
    @Path("owner/{owner}/account/{account}/add/list")
    @Consumes(MediaType.APPLICATION_JSON)
    public void addRotationEntries(
            @PathParam("owner") String aOwner,
            @PathParam("account") String aAccountHash,
            String aRotationEntries){
        List<StandingOrder> entries = StandingOrderJsonMapper.convertToEntries(aRotationEntries);
        facade.addRotationEntries(aOwner,aAccountHash, entries);
    }

    @DELETE
    @Logged
    @Secured
    @CrossOriginFilter
    @Path("owner/{owner}/account/{account}/delete/{hash}")
    public void deleteRotationEntry(
            @PathParam("owner") String aOwner,
            @PathParam("account") String aAccountHash,
            @PathParam("hash") String aHash){
        facade.deleteRotationEntry(aOwner,aAccountHash, aHash);
    }

    @PATCH
    @Logged
    @Secured
    @CrossOriginFilter
    @Path("owner/{owner}/account/{account}/update")
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateRotationEntry(
            @PathParam("owner") String aOwner,
            @PathParam("account") String aAccountHash,
            String aRotationEntry){
        StandingOrder entry = StandingOrderJsonMapper.convertToEntry(aRotationEntry);
        facade.updateRotationEntry(aOwner,aAccountHash, entry);
    }

}
