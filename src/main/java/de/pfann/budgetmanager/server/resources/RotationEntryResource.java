package de.pfann.budgetmanager.server.resources;

import de.pfann.budgetmanager.server.model.AppUser;
import de.pfann.budgetmanager.server.model.RotationEntry;
import de.pfann.budgetmanager.server.persistens.daos.AppUserFacade;
import de.pfann.budgetmanager.server.persistens.daos.RotationEntryFacade;
import de.pfann.budgetmanager.server.resources.core.AllowCrossOrigin;
import de.pfann.budgetmanager.server.resources.core.Logged;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("rotationentries/")
public class RotationEntryResource {

    private AppUserFacade userFacade;

    private RotationEntryFacade rotationEntryFacade;


    public RotationEntryResource(){
        userFacade = new AppUserFacade();
        rotationEntryFacade = new RotationEntryFacade();
    }

    @GET
    @Logged
    @AllowCrossOrigin
    @Produces(MediaType.APPLICATION_JSON)
    @Path("owner/{owner}/all")
    public List<RotationEntry> getRotationEntries(
            @PathParam("owner") String aOwner){
        AppUser user = userFacade.getUserByNameOrEmail(aOwner);
        List<RotationEntry> rotationEntries = rotationEntryFacade.getRotationEntries(user);
        return rotationEntries;
    }

    @POST
    @Logged
    @AllowCrossOrigin
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("owner/{owner}/add")
    public void addRotationEntry(
            @PathParam("owner") String aOwner,
            RotationEntry aEntry) {
        // TODO
    }

    @PATCH
    @Logged
    @AllowCrossOrigin
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("owner/{owner}/update")
    public void updateRotationEntry(
            @PathParam("owner") String aOwner,
            RotationEntry aEntry) {
        // TODO
    }

    @DELETE
    @Logged
    @AllowCrossOrigin
    @Path("owner/{owner}/delete/{hash}")
    public void deleteRotationEntry(
            @PathParam("owner") String aOwner,
            @PathParam("hash") String aHash){
        // TODO
    }



}
