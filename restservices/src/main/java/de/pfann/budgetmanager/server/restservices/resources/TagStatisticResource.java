package de.pfann.budgetmanager.server.restservices.resources;

import de.pfann.budgetmanager.server.common.util.LogUtil;
import de.pfann.budgetmanager.server.persistens.daos.AppUserFacade;
import de.pfann.budgetmanager.server.persistens.daos.TagStatisticFacade;
import de.pfann.budgetmanager.server.persistens.model.AppUser;
import de.pfann.budgetmanager.server.persistens.model.TagStatistic;
import de.pfann.budgetmanager.server.restservices.resources.core.CrossOriginFilter;
import de.pfann.budgetmanager.server.restservices.resources.core.Logged;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;


@Path("tagstatistic/")
public class TagStatisticResource {


    private final TagStatisticFacade tagStatisticFacade;
    private final AppUserFacade userFacade;

    public TagStatisticResource(){
        tagStatisticFacade = new TagStatisticFacade();
        userFacade = new AppUserFacade();
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
        LogUtil.info(this.getClass(),aTagStatistics.size() + "");
        AppUser user = userFacade.getUserByNameOrEmail(aOwner);

        for(TagStatistic tag : aTagStatistics){
            System.out.println(tag.getName());
            System.out.println(tag.getWeight());
        }

        tagStatisticFacade.persist(aTagStatistics,user);
    }
}
