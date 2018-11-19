package de.pfann.budgetmanager.server.restservices.resources.email;

import de.pfann.budgetmanager.server.common.email.EmailGenerator;

public class ContactEmailGenerator implements EmailGenerator {

    private String email;
    private String username;
    private String usermessage;

    public ContactEmailGenerator(final String aUsername, final String aEmail, final String aUserMessage) {
        email = aEmail;
        username = aUsername;
        usermessage = aUserMessage;
    }

    @Override
    public String getSubject() {
        return "Kontaktnachricht von " + email;
    }

    @Override
    public String getContent() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Eine neue Nachricht von " + username).append("\n");
        stringBuilder.append("Email: " + email).append("\n");
        stringBuilder.append("\n");
        stringBuilder.append("Inhalt der Nachricht: ").append("\n");
        stringBuilder.append(usermessage).append("\n");
        stringBuilder.append("\n");
        return stringBuilder.toString();
    }
}
