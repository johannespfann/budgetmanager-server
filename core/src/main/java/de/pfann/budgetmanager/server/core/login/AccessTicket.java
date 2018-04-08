package de.pfann.budgetmanager.server.core.login;

import de.pfann.budgetmanager.server.persistens.model.AppUser;

import java.util.Date;

public class AccessTicket {

    private AppUser user;

    private String accessToken;

    private Date creationTime;

    public AccessTicket(AppUser aUser, String aAccessToken){
        user = aUser;
        accessToken = aAccessToken;
        creationTime = new Date();
    }

    public AppUser getUser() {
        return user;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public Date getCreationTime() {
        return creationTime;
    }
}
