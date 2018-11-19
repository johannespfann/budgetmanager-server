package de.pfann.budgetmanager.server.restservices.resources;

import de.pfann.budgetmanager.server.common.facade.AppUserFacade;
import de.pfann.budgetmanager.server.common.facade.StandingOrderFacade;
import de.pfann.budgetmanager.server.common.model.AppUser;
import de.pfann.budgetmanager.server.common.model.StandingOrder;
import de.pfann.budgetmanager.server.jobengine.rotationjobs.RotationEntryExecuter;

import java.time.LocalDateTime;
import java.util.List;

public class StandingOrderResourceFacade {

    private AppUserFacade userFacade;
    private StandingOrderFacade rotationEntryFacade;
    private RotationEntryExecuter rotationEntryExecuter;

    public StandingOrderResourceFacade(AppUserFacade aAppUserFacade, StandingOrderFacade aRotationEntryFacade, RotationEntryExecuter aExecutor){
        userFacade = aAppUserFacade;
        rotationEntryFacade = aRotationEntryFacade;
        rotationEntryExecuter = aExecutor;
    }

    public List<StandingOrder> getRotationEntries(String aOwner){
        AppUser user = userFacade.getUserByNameOrEmail(aOwner);
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
        StandingOrder rotationEntry = rotationEntryFacade.getRotationEntryByHash(user, aHash);
        rotationEntryFacade.delete(rotationEntry);
    }

    public void updateRotationEntry(
            String aOwner,
            StandingOrder aRotationEntry){
        AppUser user = userFacade.getUserByNameOrEmail(aOwner);
        aRotationEntry.setUser(user);
        rotationEntryFacade.update(aRotationEntry);
    }

}
