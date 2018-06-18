package de.pfann.budgetmanager.server.restservices.resources.core;


import de.pfann.budgetmanager.server.common.model.AppUser;
import de.pfann.budgetmanager.server.restservices.resources.login.AccessPool;

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
