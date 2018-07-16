package de.pfann.budgetmanager.server.core;

import de.pfann.budgetmanager.server.common.facade.AppUserFacade;
import de.pfann.budgetmanager.server.common.facade.EntryFacade;
import de.pfann.budgetmanager.server.common.facade.StandingOrderFacade;
import de.pfann.budgetmanager.server.common.model.AppUser;

import java.util.List;

public abstract class AbstractUseCase implements UseCase {

    public static final String DELETE_IF_EXISTS = "delete.if.exists";
    public static final String NOT_DELETE_IF_EXISTS = "not.delete.if.exists";

    protected AppUserFacade userFacade;
    protected EntryFacade entryFacade;
    protected StandingOrderFacade standingOrderFacade;

    protected AbstractUseCase(AppUserFacade aUserFacade, EntryFacade aEntryFacade, StandingOrderFacade aStandingOrderFacade){
        userFacade = aUserFacade;
        entryFacade = aEntryFacade;
        standingOrderFacade = aStandingOrderFacade;
    }

    @Override
    public void setState(String aState) {

    }


    public abstract AppUser createUser();

    public abstract void createContent(AppUser aUser);

    @Override
    public void init(){
        AppUser user = createUser();

        if(!isUserExists(user)){
            createContent(user);
        }

        if(isUserExists(user)){
            userFacade.deleteUser(user);
            userFacade.createNewUser(user);
            createContent(user);
        }
    }


    private boolean isUserExists(AppUser aUser) {
        List<AppUser> users = userFacade.getAllUser();

        for(AppUser user : users){
            if(user.getName() == aUser.getName()){
                return true;
            }
        }
        return false;
    }






}
