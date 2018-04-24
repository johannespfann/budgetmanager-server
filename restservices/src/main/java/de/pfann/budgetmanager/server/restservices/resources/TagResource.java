package de.pfann.budgetmanager.server.restservices.resources;


import de.pfann.budgetmanager.server.persistens.daos.AppUserFacade;
import de.pfann.budgetmanager.server.persistens.daos.TagFacade;
import de.pfann.budgetmanager.server.persistens.model.AppUser;
import de.pfann.budgetmanager.server.persistens.model.Tag;
import de.pfann.budgetmanager.server.restservices.resources.core.CrossOriginFilter;
import de.pfann.budgetmanager.server.restservices.resources.core.Logged;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Set;

@Path("tags/")
public class TagResource {

    private AppUserFacade userFacade;
    private TagFacade tagFacade;

    public TagResource(){
        userFacade = new AppUserFacade();
        tagFacade = new TagFacade();
    }

    @GET
    @Logged
    @CrossOriginFilter
    @Path("owner/{owner}/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Set<Tag> getTagsByUser(
            @PathParam("owner") String aOwner){
        AppUser user = userFacade.getUserByNameOrEmail(aOwner);
        return tagFacade.getTags(user);
    }

}
