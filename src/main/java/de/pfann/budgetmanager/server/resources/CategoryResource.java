package de.pfann.budgetmanager.server.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.pfann.budgetmanager.server.model.AppUser;
import de.pfann.budgetmanager.server.model.Category;
import de.pfann.budgetmanager.server.persistens.daos.AppUserDao;
import de.pfann.budgetmanager.server.persistens.daos.CategoryDao;
import de.pfann.budgetmanager.server.persistens.daos.NoUserFoundException;
import de.pfann.budgetmanager.server.resources.core.Logged;
import de.pfann.budgetmanager.server.resources.core.ModifyCrossOrigin;
import de.pfann.budgetmanager.server.util.LogUtil;


import javax.ws.rs.*;
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

        AppUser user = null;

        try {
            user = AppUserDao.create().getUserByNameOrEmail(aAccessor);
        } catch (NoUserFoundException e) {
            e.printStackTrace();
            return Response.serverError()
                    .build();
        }

        String result = "{}";

        List<Category> categories = categoryDao.getAllByUser(user);
        try {

            result = mapper.writeValueAsString(categories);
        } catch (IOException e) {
            e.printStackTrace();
            Response.serverError()
                    .build();
        }

        return Response.ok()
                .entity(result)
                .build();
    }
}
