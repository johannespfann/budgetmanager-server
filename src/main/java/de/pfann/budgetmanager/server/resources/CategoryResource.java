package de.pfann.budgetmanager.server.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.pfann.budgetmanager.server.model.AppUser;
import de.pfann.budgetmanager.server.model.Category;
import de.pfann.budgetmanager.server.persistens.daos.AppUserFacade;
import de.pfann.budgetmanager.server.persistens.daos.CategoryFacade;
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

        LogUtil.info(this.getClass(), "User: " + aAccessor);
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
    @Path("delete/{deletehash}/replace/{alterhash}")
    public Response delete(
            @PathParam("deletehash") String aDeleteHash,
            @PathParam("alterhash") String aAlternativHash){


        LogUtil.info(this.getClass(), "Hash: " + aDeleteHash);
        LogUtil.info(this.getClass(), "Hash: " + aAlternativHash);

        Category categoryToDelete = categoryFacade.getCategory(aDeleteHash);
        Category categoryToReplace = categoryFacade.getCategory(aAlternativHash);

        LogUtil.info(this.getClass(), "Found: " + categoryToDelete.getName());
        LogUtil.info(this.getClass(), "Found: " + categoryToReplace.getName());

        categoryFacade.deleteCategory(categoryToDelete,categoryToReplace);

        return Response.noContent()
                .build();
    }

    @POST
    @Logged
    @ModifyCrossOrigin
    @Path("add/{accessor}")
    @Consumes(MediaType.TEXT_PLAIN)
    public Response add(
            @PathParam("accessor") String aAccessor, String aBody){

        LogUtil.info(this.getClass(), "Accessor: " + aAccessor);
        LogUtil.info(this.getClass(), "Body: " + aBody);


        AppUser user = userFacade.getUserByNameOrEmail(aAccessor);

        LogUtil.info(this.getClass(),"Found User: " + user.getName());

        String categoryJson = getCategory(aBody);

        LogUtil.info(this.getClass(),"CategoryJson: " + categoryJson);
        Category category;

        try {
            category = mapper.readValue(aBody, Category.class);
            LogUtil.info(this.getClass(),"Map category: " + category.getHash() + " and " + category.getName());
        } catch (IOException e) {
            LogUtil.info(this.getClass(),e.getMessage());
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .build();
        }

        LogUtil.info(this.getClass(),"Set User to category");
        category.setAppUser(user);

        LogUtil.info(this.getClass(),"Save Category");
        categoryFacade.addCategory(category);

        return Response.ok().entity("")
                .build();
    }

    private String getCategory(String aBody) {
        return aBody;
    }

    @POST
    @Logged
    @ModifyCrossOrigin
    @Path("update/{hash}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public Response update(
            @PathParam("hash") String aHash,
            String aBody){

        LogUtil.info(this.getClass(),"Get Body: " + aBody);

        String categoryJson = getCategory(aBody);
        Category updatedCategory = new Category();

        try {
            updatedCategory = mapper.readValue(categoryJson,Category.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Category category = categoryFacade.getCategory(aHash);
        category.setName(updatedCategory.getName());

        categoryFacade.updateCategoryName(category);

        return Response.noContent().build();
    }

    @GET
    @Logged
    @ModifyCrossOrigin
    @Path("default/{accessor}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDefaultCategory(
            @PathParam("accessor") String aAccessor){

        AppUser user = userFacade.getUserByNameOrEmail(aAccessor);

        Category category = categoryFacade.getDefaultCategory(user);

        String categoryString = null;
        try {
            categoryString = mapper.writeValueAsString(category);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return Response.ok().entity(categoryString).build();
    }


}
