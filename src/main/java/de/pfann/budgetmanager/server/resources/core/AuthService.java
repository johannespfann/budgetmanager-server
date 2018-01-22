package de.pfann.budgetmanager.server.resources.core;

import de.pfann.budgetmanager.server.login.AccessPool;
import de.pfann.budgetmanager.server.model.AppUser;

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
