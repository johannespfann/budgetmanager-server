package de.pfann.budgetmanager.server.restservices.resources;

import de.pfann.budgetmanager.server.common.facade.AppUserFacade;
import de.pfann.budgetmanager.server.common.facade.StandingOrderFacade;
import de.pfann.budgetmanager.server.common.model.AppUser;
import de.pfann.budgetmanager.server.common.model.StandingOrder;
import de.pfann.budgetmanager.server.common.util.DateUtil;
import de.pfann.budgetmanager.server.common.util.LogUtil;
import de.pfann.budgetmanager.server.jobengine.rotationjobs.RotationEntryExecuter;

import java.time.LocalDateTime;
import java.util.List;

public class RotationEntryResourceFacade {

    private AppUserFacade userFacade;
    private StandingOrderFacade rotationEntryFacade;
    private RotationEntryExecuter rotationEntryExecuter;

    public RotationEntryResourceFacade(AppUserFacade aAppUserFacade, StandingOrderFacade aRotationEntryFacade, RotationEntryExecuter aExecutor){
        userFacade = aAppUserFacade;
        rotationEntryFacade = aRotationEntryFacade;
        rotationEntryExecuter = aExecutor;
    }

    public List<StandingOrder> getRotationEntries(String aOwner){
        AppUser user = userFacade.getUserByNameOrEmail(aOwner);
        System.out.println("found userobject with name " + user.getName());
        List<StandingOrder> rotationEntries = this.rotationEntryFacade.getRotationEntries(user);
        return rotationEntries;
    }

    public void addRotationEntry(
            String aOwner,
            StandingOrder aRotationEntry){
        AppUser user = userFacade.getUserByNameOrEmail(aOwner);
        aRotationEntry.setUser(user);
        rotationEntryFacade.save(aRotationEntry);
        StandingOrder standingOrder = rotationEntryFacade.getRotationEntryByHash(user,aRotationEntry.getHash());

        rotationEntryExecuter.executeRotationEntry(LocalDateTime.now(),standingOrder);
    }

    public void deleteRotationEntry(
            String aOwner,
           String aHash){

        AppUser user = userFacade.getUserByNameOrEmail(aOwner);
        LogUtil.info(this.getClass(),"Hash: " + aHash);
        LogUtil.info(this.getClass(),"User: " + aOwner);

        StandingOrder rotationEntry = rotationEntryFacade.getRotationEntryByHash(user, aHash);
        LogUtil.info(this.getClass(), "asdfs");
        LogUtil.info(this.getClass(),"get entry: " + rotationEntry.getHash());

        rotationEntryFacade.delete(rotationEntry);

        LogUtil.info(this.getClass(),"Deleted entry: " + aHash);

    }

    public void updateRotationEntry(
            String aOwner,
            StandingOrder aRotationEntry){

        AppUser user = userFacade.getUserByNameOrEmail(aOwner);
        aRotationEntry.setUser(user);

        LogUtil.info(this.getClass(),"Owner: " + user);
        LogUtil.info(this.getClass(),"Roten: " + aRotationEntry);
        rotationEntryFacade.update(aRotationEntry);

        LogUtil.info(this.getClass(),"Updated rotationEntry " + aRotationEntry);
    }

}
