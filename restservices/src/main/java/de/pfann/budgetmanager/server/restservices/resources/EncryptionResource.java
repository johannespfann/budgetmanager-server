package de.pfann.budgetmanager.server.restservices.resources;

import de.pfann.budgetmanager.server.common.model.AppUser;
import de.pfann.budgetmanager.server.common.util.LogUtil;
import de.pfann.budgetmanager.server.persistens.daos.AppUserSQLFacade;
import de.pfann.budgetmanager.server.restservices.resources.core.CrossOriginFilter;
import de.pfann.budgetmanager.server.restservices.resources.core.Logged;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("encryption/")
public class EncryptionResource {

    private AppUserSQLFacade userFacade;

    public EncryptionResource(){
        userFacade = new AppUserSQLFacade();
    }

    @GET
    @Logged
    @CrossOriginFilter
    @Path("owner/{owner}/isencrypted")
    public boolean isEncrypted(@PathParam("owner") String aOwner){
        AppUser user = userFacade.getUserByNameOrEmail(aOwner);
        return user.isEncrypted();
    }

    @GET
    @Logged
    @CrossOriginFilter
    @Produces(MediaType.APPLICATION_JSON)
    @Path("owner/{owner}/getencrypttext")
    public String getEncryptText(@PathParam("owner") String aOwner){
        AppUser user = userFacade.getUserByNameOrEmail(aOwner);
        String json = "{ \"text\" : \"" + user.getEncryptTest() + "\"}";
        return json;
    }

    @POST
    @Logged
    @CrossOriginFilter
    @Path("owner/{owner}/setencrypttext")
    public void setEncryptText(@PathParam("owner") String aOwner, String aBody){
        AppUser user = userFacade.getUserByNameOrEmail(aOwner);

        LogUtil.info(this.getClass(),"Set text: " + aBody);
        user.setEncryptTest(aBody);
        user.setEncrypted(true);

        userFacade.updateUser(user);
    }
}
