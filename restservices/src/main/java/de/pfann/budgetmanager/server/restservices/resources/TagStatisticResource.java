package de.pfann.budgetmanager.server.restservices.resources;

import de.pfann.budgetmanager.server.common.model.TagStatistic;
import de.pfann.budgetmanager.server.restservices.resources.core.CrossOriginFilter;
import de.pfann.budgetmanager.server.restservices.resources.core.Logged;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;


@Path("tagstatistic/")
public class TagStatisticResource {

    private TagStatisticResourceFacade tagStatisticResourceFacade;

    public TagStatisticResource(TagStatisticResourceFacade aTagStatisticResourceFacade){
        tagStatisticResourceFacade = aTagStatisticResourceFacade;
    }

    @GET
    @Logged
    @CrossOriginFilter
    @Path("owner/{owner}/all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<TagStatistic> getAllTagStatitics(
            @PathParam("owner") String aOwner){
        return tagStatisticResourceFacade.getAllTagStatistics(aOwner);
    }

    @POST
    @Logged
    @CrossOriginFilter
    @Path("owner/{owner}/persist")
    @Consumes(MediaType.APPLICATION_JSON)
    public void persistStatistics(
            @PathParam("owner") String aOwner,
            List<TagStatistic> aTagStatistics){
        tagStatisticResourceFacade.persistStatistics(aOwner,aTagStatistics);
    }
}
