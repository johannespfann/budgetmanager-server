package de.pfann.budgetmanager.server.restservices.resources.email;

import de.pfann.budgetmanager.server.common.email.EmailGenerator;

public class ActivationEmailGenerator implements EmailGenerator {

    private String username;
    private String email;
    private String activationcode;

    public ActivationEmailGenerator(String aUsername, String aEmail, String aActivationCode){
        username = aUsername;
        email = aEmail;
        activationcode = aActivationCode;
    }

    @Override
    public String getSubject() {
        return "Activationcode für " + username;
    }

    @Override
    public String getContent() {
        StringBuilder contentBuilder = new StringBuilder();
        contentBuilder
                .append("Hallo " + username + ",")
                .append("\n")
                .append("der aktivierungscode lautet ")
                .append(activationcode)
                .append("\n")
                .append("Die registrierte Email ")
                .append(email)
                .append(" wurde dem Benutzer gespeichert");
        return contentBuilder.toString();
    }
}
