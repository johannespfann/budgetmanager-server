package de.pfann.budgetmanager.server.restservices.resources;

import de.pfann.budgetmanager.server.common.model.AppUser;
import de.pfann.budgetmanager.server.common.model.TagStatistic;
import de.pfann.budgetmanager.server.persistens.daos.AppUserSQLFacade;
import de.pfann.budgetmanager.server.persistens.daos.TagStatisticSQLFacade;
import de.pfann.budgetmanager.server.restservices.resources.core.CrossOriginFilter;
import de.pfann.budgetmanager.server.restservices.resources.core.Logged;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;


@Path("tagstatistic/")
public class TagStatisticResource {


    private final TagStatisticSQLFacade tagStatisticFacade;
    private final AppUserSQLFacade userFacade;

    public TagStatisticResource(){
        tagStatisticFacade = new TagStatisticSQLFacade();
        userFacade = new AppUserSQLFacade();
    }

    @GET
    @Logged
    @CrossOriginFilter
    @Path("owner/{owner}/all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<TagStatistic> getAllTagStatitics(
            @PathParam("owner") String aOwner){
        AppUser user = userFacade.getUserByNameOrEmail(aOwner);
        return tagStatisticFacade.getAllByUser(user);
    }

    @POST
    @Logged
    @CrossOriginFilter
    @Path("owner/{owner}/persist")
    @Consumes(MediaType.APPLICATION_JSON)
    public void persistStatistics(
            @PathParam("owner") String aOwner,
            List<TagStatistic> aTagStatistics){
        AppUser user = userFacade.getUserByNameOrEmail(aOwner);

        tagStatisticFacade.persist(aTagStatistics,user);
    }
}
