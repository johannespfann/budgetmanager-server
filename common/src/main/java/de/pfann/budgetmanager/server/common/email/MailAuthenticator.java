package de.pfann.budgetmanager.server.common.email;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class MailAuthenticator extends Authenticator {

    private final String password;
    private final String user;

    protected MailAuthenticator(String aUser, String aPassword) {
        user = aUser;
        password = aPassword;
    }

    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(user, password);
    }

}
