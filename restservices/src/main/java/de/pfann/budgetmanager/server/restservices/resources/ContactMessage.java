package de.pfann.budgetmanager.server.restservices.resources;

public class ContactMessage {

    private String name;

    private String email;

    private String message;

    public ContactMessage() {
        // default
    }

    public ContactMessage(String aName, String aEmail, String aMessage){
        name = aName;
        email = aEmail;
        message = aMessage;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getMessage() {
        return message;
    }


}
