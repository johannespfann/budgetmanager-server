package de.pfann.budgetmanager.server.restservices.resources;

import de.pfann.budgetmanager.server.common.facade.AppUserFacade;
import de.pfann.budgetmanager.server.common.facade.StandingOrderFacade;
import de.pfann.budgetmanager.server.common.model.AppUser;

public class StandingOrderJobResourceFacade {

    private AppUserFacade userFacade;
    private StandingOrderFacade standingOrderFacade;

    public StandingOrderJobResourceFacade(AppUserFacade aUserFacade, StandingOrderFacade aStandingOrderFacade){
        userFacade = aUserFacade;
        standingOrderFacade = aStandingOrderFacade;
    }

    public void invokeStandingOrderJobs(String aOwner) {
        AppUser user = userFacade.getUserByNameOrEmail(aOwner);



    }
}
