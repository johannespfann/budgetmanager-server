package de.pfann.budgetmanager.server.restservices.resources;

import de.pfann.budgetmanager.server.restservices.resources.core.CrossOriginFilter;
import de.pfann.budgetmanager.server.restservices.resources.core.Logged;
import de.pfann.budgetmanager.server.restservices.resources.core.Secured;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

public class StandingOrderJobResource {

    private StandingOrderJobResourceFacade standingOrderJobResourceFacade;

    public StandingOrderJobResource(StandingOrderJobResourceFacade aStandingOrderJobResourceFacade){
        standingOrderJobResourceFacade = aStandingOrderJobResourceFacade;
    }

    @POST
    @Logged
    @Secured
    @CrossOriginFilter
    @Path("owner/{owner}/invoke/all")
    public void invokeStandingOrderJobs(@PathParam("owner") String aOwner){
        standingOrderJobResourceFacade.invokeStandingOrderJobs(aOwner);
    }

}
