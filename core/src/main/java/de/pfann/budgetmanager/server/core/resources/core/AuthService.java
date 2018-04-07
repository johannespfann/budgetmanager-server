package de.pfann.budgetmanager.server.core.resources.core;

import de.pfann.budgetmanager.server.core.login.AccessPool;
import de.pfann.budgetmanager.server.core.model.AppUser;

public class AuthService {

    public AuthService(){
        // default
    }

    public boolean isAuthenticated(AppUser aUser, String aAccessToken){
        if(AccessPool.getInstance().isValid(aUser,aAccessToken)){
            return true;
        }
        return false;
    }
}
