package de.pfann.budgetmanager.server.restservices.resources.login;

import java.util.Date;

public class AuthToken {

    private String username;

    private Date expiredate;

    public AuthToken(){
        //
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getExpiredate() {
        return expiredate;
    }

    public void setExpiredate(Date expiredate) {
        this.expiredate = expiredate;
    }

    @Override
    public String toString() {
        return "{" +
                "username='" + username + '\'' +
                ", expiredate=" + expiredate +
                '}';
    }
}
