package de.pfann.budgetmanager.server.core.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.pfann.budgetmanager.server.persistens.model.AppUser;
import de.pfann.budgetmanager.server.persistens.model.Category;
import de.pfann.budgetmanager.server.persistens.daos.AppUserFacade;
import de.pfann.budgetmanager.server.persistens.daos.CategoryFacade;
import de.pfann.budgetmanager.server.core.resources.core.Logged;
import de.pfann.budgetmanager.server.core.resources.core.AllowCrossOrigin;
import de.pfann.budgetmanager.server.common.util.LogUtil;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
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
    @AllowCrossOrigin
    @Path("owner/{accessor}/all")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Category> getCategories(
            @PathParam("accessor") String aAccessor){
        return categoryFacade.getAllByUser(userFacade.getUserByNameOrEmail(aAccessor));
    }

    @DELETE
    @Logged
    @AllowCrossOrigin
    @Path("delete/{deletehash}/replace/{alterhash}")
    public void delete(
            @PathParam("deletehash") String aDeleteHash,
            @PathParam("alterhash") String aAlternativHash){


        LogUtil.info(this.getClass(), "Hash: " + aDeleteHash);
        LogUtil.info(this.getClass(), "Hash: " + aAlternativHash);

        Category categoryToDelete = categoryFacade.getCategory(aDeleteHash);
        Category categoryToReplace = categoryFacade.getCategory(aAlternativHash);

        LogUtil.info(this.getClass(), "Found: " + categoryToDelete.getName());
        LogUtil.info(this.getClass(), "Found: " + categoryToReplace.getName());

        categoryFacade.deleteCategory(categoryToDelete,categoryToReplace);

    }

    @POST
    @Logged
    @AllowCrossOrigin
    @Path("owner/{accessor}/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public void add(
            @PathParam("accessor") String aAccessor, Category aCategory){

        LogUtil.info(this.getClass(), "Accessor: " + aAccessor);
        LogUtil.info(this.getClass(), "Body: " + aCategory);

        AppUser user = userFacade.getUserByNameOrEmail(aAccessor);

        LogUtil.info(this.getClass(),"Found User: " + user.getName());
        LogUtil.info(this.getClass(),"Set User to category");

        aCategory.setAppUser(user);

        LogUtil.info(this.getClass(),"Save Category");
        categoryFacade.addCategory(aCategory);

    }

    @PATCH
    @Logged
    @AllowCrossOrigin
    @Path("update")
    @Consumes(MediaType.APPLICATION_JSON)
    public void update(
            Category aCategory){
        Category persistedCategory = categoryFacade.getCategory(aCategory.getHash());
        persistedCategory.setName(aCategory.getName());
        categoryFacade.updateCategoryName(persistedCategory);

    }

    @GET
    @Logged
    @AllowCrossOrigin
    @Path("owner/{accessor}/default")
    @Produces(MediaType.APPLICATION_JSON)
    public Category getDefaultCategory(
            @PathParam("accessor") String aAccessor){
        AppUser user = userFacade.getUserByNameOrEmail(aAccessor);
        return categoryFacade.getDefaultCategory(user);
    }


}
