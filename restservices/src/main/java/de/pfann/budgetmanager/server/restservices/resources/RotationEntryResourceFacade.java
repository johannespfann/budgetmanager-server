package de.pfann.budgetmanager.server.restservices.resources;

import de.pfann.budgetmanager.server.common.facade.AppUserFacade;
import de.pfann.budgetmanager.server.common.facade.RotationEntryFacade;
import de.pfann.budgetmanager.server.common.model.AppUser;
import de.pfann.budgetmanager.server.common.model.RotationEntry;
import de.pfann.budgetmanager.server.common.util.LogUtil;

import java.util.List;

public class RotationEntryResourceFacade {

    private AppUserFacade userFacade;
    private RotationEntryFacade rotationEntryFacade;

    public RotationEntryResourceFacade(AppUserFacade aAppUserFacade, RotationEntryFacade aRotationEntryFacade){
        userFacade = aAppUserFacade;
        rotationEntryFacade = aRotationEntryFacade;
    }

    public List<RotationEntry> getRotationEntries(
            String aOwner
    ){

        AppUser user = userFacade.getUserByNameOrEmail(aOwner);
        List<RotationEntry> rotationEntries = this.rotationEntryFacade.getRotationEntries(user);
        return rotationEntries;
    }

    public void addRotationEntry(
            String aOwner,
            RotationEntry aRotationEntry){
        AppUser user = userFacade.getUserByNameOrEmail(aOwner);
        aRotationEntry.setUser(user);
        rotationEntryFacade.save(aRotationEntry);
    }

    public void deleteRotationEntry(
            String aOwner,
           String aHash){

        AppUser user = userFacade.getUserByNameOrEmail(aOwner);
        LogUtil.info(this.getClass(),"Hash: " + aHash);
        LogUtil.info(this.getClass(),"User: " + aOwner);

        RotationEntry rotationEntry = rotationEntryFacade.getRotationEntryByHash(aHash);
        LogUtil.info(this.getClass(), "asdfs");
        LogUtil.info(this.getClass(),"get entry: " + rotationEntry.getHash());

        rotationEntryFacade.delete(rotationEntry);

        LogUtil.info(this.getClass(),"Deleted entry: " + aHash);

    }

    public void updateRotationEntry(
            String aOwner,
            RotationEntry aRotationEntry){

        AppUser user = userFacade.getUserByNameOrEmail(aOwner);
        aRotationEntry.setUser(user);

        LogUtil.info(this.getClass(),"Owner: " + user);
        LogUtil.info(this.getClass(),"Roten: " + aRotationEntry);
        rotationEntryFacade.update(aRotationEntry);

        LogUtil.info(this.getClass(),"Updated rotationEntry " + aRotationEntry);
    }

}
