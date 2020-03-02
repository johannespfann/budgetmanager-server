package de.pfann.budgetmanager.server.restservices.resources;

import de.pfann.budgetmanager.server.model.V2Entry;
import de.pfann.budgetmanager.server.model.V2StandingOrder;
import de.pfann.budgetmanager.server.restservices.resources.core.CrossOriginFilter;
import de.pfann.budgetmanager.server.restservices.resources.core.Logged;
import de.pfann.budgetmanager.server.restservices.resources.core.Secured;
import de.pfann.budgetmanager.server.restservices.resources.util.EntryJsonMapper;
import de.pfann.budgetmanager.server.restservices.resources.util.StandingOrderJsonMapper;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("backup/")
public class CopyRepositoryResource {

    private CopyRepositoryResourceFacade facade;

    public CopyRepositoryResource(CopyRepositoryResourceFacade aFacade) {
        facade = aFacade;
    }

    @POST
    @Logged
    @Secured
    @CrossOriginFilter
    @Path("owner/{owner}/account/{account}/copy/entries")
    @Consumes(MediaType.APPLICATION_JSON)
    public void addEntries(
            @PathParam("owner") String aOwner,
            @PathParam("account") String aAccountHash,
            String aBody) {
        System.out.println(aBody);
        List<V2Entry> entries = EntryJsonMapper.convertToV2Entries(aBody);
        facade.addEntries(aOwner, aAccountHash, entries);
    }


    @POST
    @Logged
    @Secured
    @CrossOriginFilter
    @Path("owner/{owner}/account/{account}/copy/standingorders")
    @Consumes(MediaType.APPLICATION_JSON)
    public void addRotationEntries(
            @PathParam("owner") String aOwner,
            @PathParam("account") String aAccountHash,
            String aRotationEntries) {
        System.out.println(aRotationEntries);
        List<V2StandingOrder> entries = StandingOrderJsonMapper.convertToV2Entries(aRotationEntries);

        facade.addStandingOrders(aOwner, aAccountHash, entries);
    }

}


/*
@Path("system/")
public class HealthResource {

    @GET
    @Logged
    @CrossOriginFilter
    @Path("health")
    @Produces(MediaType.TEXT_PLAIN)
    public String isAlive()  {
        return "is alive";
    }

}

 */