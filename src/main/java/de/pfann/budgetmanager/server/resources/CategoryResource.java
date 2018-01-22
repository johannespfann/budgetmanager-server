package de.pfann.budgetmanager.server.resources;

import de.pfann.budgetmanager.server.model.AppUser;
import de.pfann.budgetmanager.server.model.Category;
import de.pfann.budgetmanager.server.persistens.daos.AppUserDao;
import de.pfann.budgetmanager.server.persistens.daos.CategoryDao;
import de.pfann.budgetmanager.server.persistens.daos.NoUserFoundException;
import de.pfann.budgetmanager.server.resources.core.Logged;
import de.pfann.budgetmanager.server.resources.core.ModifyCrossOrigin;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("category/")
public class CategoryResource {

    private static final String AUTHORIZATION_KEY = "Authorization";

    private AppUserDao userDao;

    private CategoryDao categoryDao;

    public CategoryResource(){
        userDao = AppUserDao.create();
        categoryDao = CategoryDao.create();
    }

    @GET
    @Logged
    @ModifyCrossOrigin
    @Path("all/{accessor}")
    public Response getCategories(
            @Context HttpHeaders aHeaders,
            @QueryParam("accessor") String aAccessor){

        String accessToken = getAccessToken(aHeaders);

        AppUser user = null;

        try {
            user = AppUserDao.create().getUserByNameOrEmail(aAccessor);
        } catch (NoUserFoundException e) {
            e.printStackTrace();
            return Response.serverError()
                    .build();
        }

        List<Category> categories = categoryDao.getAllByUser(user);

        // TODO map to json

        return Response.ok()
                .entity(null)
                .build();
    }

    private String getAccessToken(HttpHeaders aHeaders) {
        return aHeaders.getHeaderString(AUTHORIZATION_KEY);
    }
}
