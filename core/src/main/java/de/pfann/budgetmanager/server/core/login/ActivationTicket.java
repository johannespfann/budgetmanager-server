package de.pfann.budgetmanager.server.core.login;

import java.util.Date;

// Value-Object
// TODO EQUALS-METHODE
public class ActivationTicket {

    private String username;

    private String activationCode;

    private String email;

    private Date date_of_creation;

    public ActivationTicket(String aUsername, String aActivationCode, String aEmail){
        username = aUsername;
        activationCode = aActivationCode;
        email = aEmail;
        date_of_creation = new Date();
    }

    public String getUsername() {
        return username;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public String getEmail() {
        return email;
    }

    public Date getDate_of_creation() {
        return date_of_creation;
    }

    @Override
    public String toString() {
        return "Ticket for: " + username + " (" + email + ") and Code " + activationCode + " (" + date_of_creation.toString() +")";
    }


}
