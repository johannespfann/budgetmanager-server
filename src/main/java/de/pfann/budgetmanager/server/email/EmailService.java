package de.pfann.budgetmanager.server.email;

public class EmailService {

    private static final String NEW_LINE = "\n";

    public void sendActivationEmail(String aUsername, String aEmail, String activationCode) {
        System.out.println("Send new Email:");

        StringBuilder stringBuilder = new StringBuilder("#### New Email ####")
                .append(NEW_LINE)
                .append("Username: " + aUsername)
                .append(NEW_LINE)
                .append("Email: " + aEmail)
                .append(NEW_LINE)
                .append("ActivationCode: " + activationCode);

        System.out.println(stringBuilder.toString());
        // TODO send EMAIL
    }
}
