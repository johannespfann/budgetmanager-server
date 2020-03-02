package de.pfann.budgetmanager.server.restservices.resources;

import de.pfann.budgetmanager.server.restservices.resources.core.CrossOriginFilter;
import de.pfann.budgetmanager.server.restservices.resources.core.Logged;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("system/")
public class HealthResource {

    @GET
    @Logged
    @CrossOriginFilter
    @Path("health")
    @Produces(MediaType.TEXT_PLAIN)
    public String isAlive() {
        return "is alive";
    }

}
