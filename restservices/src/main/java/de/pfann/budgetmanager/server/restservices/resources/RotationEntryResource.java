package de.pfann.budgetmanager.server.restservices.resources;

import de.pfann.budgetmanager.server.common.model.AppUser;
import de.pfann.budgetmanager.server.common.model.RotationEntry;
import de.pfann.budgetmanager.server.common.util.LogUtil;
import de.pfann.budgetmanager.server.persistens.daos.AppUserSQLFacade;
import de.pfann.budgetmanager.server.persistens.daos.RotationEntrySQLFacade;
import de.pfann.budgetmanager.server.restservices.resources.core.CrossOriginFilter;
import de.pfann.budgetmanager.server.restservices.resources.core.Logged;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("jobs/")
public class RotationEntryResource {


    private AppUserSQLFacade userFacade;

    private RotationEntrySQLFacade rotationEntryFacade;


    public RotationEntryResource(){
        userFacade = new AppUserSQLFacade();
        rotationEntryFacade = new RotationEntrySQLFacade();
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
        rotationEntryFacade.save(aRotationEntry);
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
        LogUtil.info(this.getClass(),"User: " + aOwner);

        RotationEntry rotationEntry = rotationEntryFacade.getRotationEntryByHash(aHash);
        LogUtil.info(this.getClass(), "asdfs");
        LogUtil.info(this.getClass(),"get entry: " + rotationEntry.getHash());

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
