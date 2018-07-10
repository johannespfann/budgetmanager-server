package de.pfann.budgetmanager.server.restservices.resources;

import de.pfann.budgetmanager.server.common.model.TagStatistic;
import de.pfann.budgetmanager.server.restservices.resources.core.CrossOriginFilter;
import de.pfann.budgetmanager.server.restservices.resources.core.Logged;
import de.pfann.budgetmanager.server.restservices.resources.core.Secured;
import de.pfann.budgetmanager.server.restservices.resources.util.TagStatisticJsonMapper;

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
    @Secured
    @CrossOriginFilter
    @Path("owner/{owner}/all")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllTagStatitics(
            @PathParam("owner") String aOwner){
        List<TagStatistic> tagStatistics =  tagStatisticResourceFacade.getAllTagStatistics(aOwner);
        return TagStatisticJsonMapper.convertToJson(tagStatistics);
    }

    @POST
    @Logged
    @Secured
    @CrossOriginFilter
    @Path("owner/{owner}/persist")
    @Consumes(MediaType.APPLICATION_JSON)
    public void persistStatistics(
            @PathParam("owner") String aOwner,
            String aTagStatistics){
        List<TagStatistic> tagStatistics = TagStatisticJsonMapper.convertToEntries(aTagStatistics);
        tagStatisticResourceFacade.persistStatistics(aOwner,tagStatistics);
    }
}
