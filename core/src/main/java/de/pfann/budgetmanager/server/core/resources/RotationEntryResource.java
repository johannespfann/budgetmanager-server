package de.pfann.budgetmanager.server.core.resources;

import de.pfann.budgetmanager.server.core.resources.core.CrossOriginFilter;
import de.pfann.budgetmanager.server.persistens.model.AppUser;
import de.pfann.budgetmanager.server.persistens.daos.AppUserFacade;
import de.pfann.budgetmanager.server.core.resources.core.Logged;
import de.pfann.budgetmanager.server.persistens.model.RotationEntry;
import de.pfann.budgetmanager.server.persistens.daos.RotationEntryFacade;
import de.pfann.budgetmanager.server.common.util.LogUtil;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("jobs/")
public class RotationEntryResource {


    private AppUserFacade userFacade;

    private RotationEntryFacade rotationEntryFacade;


    public RotationEntryResource(){
        userFacade = new AppUserFacade();
        rotationEntryFacade = new RotationEntryFacade();
    }

    @GET
    @Logged
    @CrossOriginFilter
    @Produces(MediaType.APPLICATION_JSON)
    @Path("owner/{owner}/all")
    public List<RotationEntry> getRotationEntries(
            @PathParam("owner") String aOwner
            ){

        AppUser user = userFacade.getUserByNameOrEmail(aOwner);
        List<RotationEntry> rotationEntries = this.rotationEntryFacade.getRotationEntries(user);
        return rotationEntries;
    }

    @POST
    @Logged
    @CrossOriginFilter
    @Path("owner/{owner}/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public void addRotationEntry(
            @PathParam("owner") String aOwner,
            RotationEntry aRotationEntry){
        AppUser user = userFacade.getUserByNameOrEmail(aOwner);

        aRotationEntry.setUser(user);

        LogUtil.info(this.getClass(),"Owner: " + user);
        LogUtil.info(this.getClass(),"Roten: " + aRotationEntry);
        rotationEntryFacade.save(aRotationEntry);

        LogUtil.info(this.getClass(),"Saved rotationEntry " + aRotationEntry);


    }

    @DELETE
    @Logged
    @CrossOriginFilter
    @Path("owner/{owner}/delete/{hash}")
    public void deleteRotationEntry(
            @PathParam("owner") String aOwner,
            @PathParam("hash") String aHash){

        AppUser user = userFacade.getUserByNameOrEmail(aOwner);
        LogUtil.info(this.getClass(),"Hash: " + aHash);

        RotationEntry rotationEntry = rotationEntryFacade.getRotationEntryByHash(aHash);
        LogUtil.info(this.getClass(),"get entry: " + rotationEntry);

        rotationEntryFacade.delete(rotationEntry);

        LogUtil.info(this.getClass(),"Deleted entry: " + aHash);

    }

    @PATCH
    @Logged
    @CrossOriginFilter
    @Path("owner/{owner}/update")
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateRotationEntry(
            @PathParam("owner") String aOwner,
            RotationEntry aRotationEntry){

        AppUser user = userFacade.getUserByNameOrEmail(aOwner);
        aRotationEntry.setUser(user);

        LogUtil.info(this.getClass(),"Owner: " + user);
        LogUtil.info(this.getClass(),"Roten: " + aRotationEntry);
        rotationEntryFacade.update(aRotationEntry);

        LogUtil.info(this.getClass(),"Updated rotationEntry " + aRotationEntry);
    }

}