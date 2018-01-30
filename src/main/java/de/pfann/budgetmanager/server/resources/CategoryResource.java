package de.pfann.budgetmanager.server.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.pfann.budgetmanager.server.model.AppUser;
import de.pfann.budgetmanager.server.model.Category;
import de.pfann.budgetmanager.server.persistens.daos.AppUserFacade;
import de.pfann.budgetmanager.server.persistens.daos.CategoryFacade;
import de.pfann.budgetmanager.server.persistens.daos.AppUserDao;
import de.pfann.budgetmanager.server.persistens.daos.CategoryDao;
import de.pfann.budgetmanager.server.persistens.daos.NoUserFoundException;
import de.pfann.budgetmanager.server.resources.core.Logged;
import de.pfann.budgetmanager.server.resources.core.ModifyCrossOrigin;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;

@Path("category/")
public class CategoryResource {

    private ObjectMapper mapper;

    private CategoryFacade categoryFacade;

    private AppUserFacade userFacade;

    public CategoryResource(){
        categoryFacade = new CategoryFacade();
        userFacade = new AppUserFacade();
        mapper = new ObjectMapper();
    }

    @GET
    @Logged
    @ModifyCrossOrigin
    @Path("all/{accessor}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getCategories(
            @PathParam("accessor") String aAccessor){

        AppUser user = userFacade.getUserByNameOrEmail(aAccessor);

        String result = "{}";

        List<Category> categories = categoryFacade.getAllByUser(user);
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

    @DELETE
    @Logged
    @ModifyCrossOrigin
    @Path("delete/{deletehash}/alternativ/{alterhash}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response delete(
            @PathParam("deletehash") String aDeleteHash,
            @PathParam("alterhash") String AlternativHash){

                return null;
    }

    @PUT
    @Logged
    @ModifyCrossOrigin
    @Path("add/{accessor}")
    private Response add(
            @PathParam("accessor") String aAccessor, String aBody){


                return null;
    }

    @POST
    @Path("updateUser/{hash}")
    public Response update(
            @PathParam("hash") String aHash){

            Category category = categoryFacade.getCategory(aHash);



        return null;
    }


}
