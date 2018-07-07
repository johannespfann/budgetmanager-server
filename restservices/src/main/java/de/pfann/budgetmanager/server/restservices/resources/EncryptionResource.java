package de.pfann.budgetmanager.server.restservices.resources;

import de.pfann.budgetmanager.server.restservices.resources.core.CrossOriginFilter;
import de.pfann.budgetmanager.server.restservices.resources.core.Logged;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("encryption/")
public class EncryptionResource   {

    private EncryptionResourceFacade encryptionResourceFacade;

    public EncryptionResource(EncryptionResourceFacade aEncryptionResourceFacade){
        encryptionResourceFacade = aEncryptionResourceFacade;
    }

    @GET
    @Logged
    @CrossOriginFilter
    @Path("owner/{owner}/isencrypted")
    public boolean isEncrypted(@PathParam("owner") String aOwner){
        return encryptionResourceFacade.isEncrypted(aOwner);
    }

    @GET
    @Logged
    @CrossOriginFilter
    @Produces(MediaType.APPLICATION_JSON)
    @Path("owner/{owner}/getencrypttext")
    public String getEncryptText(@PathParam("owner") String aOwner){
        String encryptionText = encryptionResourceFacade.getEncryptionText(aOwner);
        String json = "{ \"text\" : \"" + encryptionText + "\"}";
        return json;
    }

    @POST
    @Logged
    @CrossOriginFilter
    @Path("owner/{owner}/setencrypttext")
    public void setEncryptText(@PathParam("owner") String aOwner, String aBody){
        encryptionResourceFacade.setEncryptionText(aOwner,aBody);
    }
}
