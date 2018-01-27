package de.pfann.budgetmanager.server.resources;

import de.pfann.budgetmanager.server.model.AppUser;
import de.pfann.budgetmanager.server.model.Category;
import de.pfann.budgetmanager.server.persistens.daos.AppUserDao;
import de.pfann.budgetmanager.server.persistens.daos.CategoryDao;
import de.pfann.budgetmanager.server.persistens.daos.NoUserFoundException;
import de.pfann.budgetmanager.server.resources.core.Logged;
import de.pfann.budgetmanager.server.resources.core.ModifyCrossOrigin;
import de.pfann.budgetmanager.server.util.LogUtil;
import org.codehaus.jackson.map.ObjectMapper;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;

@Path("category/")
public class CategoryResource {

    private static final String AUTHORIZATION_KEY = "Authorization";


    private ObjectMapper mapper;

    private AppUserDao userDao;

    private CategoryDao categoryDao;

    public CategoryResource(){
        userDao = AppUserDao.create();
        categoryDao = CategoryDao.create();
        mapper = new ObjectMapper();
    }

    @GET
    @Logged
    @ModifyCrossOrigin
    @Path("all/{accessor}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getCategories(
            @PathParam("accessor") String aAccessor){

        LogUtil.info(this.getClass(),"Init accessor");
        LogUtil.info(this.getClass(),"- " + aAccessor);
        AppUser user = null;

        try {
            user = AppUserDao.create().getUserByNameOrEmail(aAccessor);
        } catch (NoUserFoundException e) {
            e.printStackTrace();
            return Response.serverError()
                    .build();
        }

        List<Category> categories = categoryDao.getAllByUser(user);

        Category category = categories.get(0);
        category.setAppUser(user);
        String result = "{}";
        try {
            result = mapper.writeValueAsString(category);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Response.ok()
                .entity(result)
                .build();
    }
}
