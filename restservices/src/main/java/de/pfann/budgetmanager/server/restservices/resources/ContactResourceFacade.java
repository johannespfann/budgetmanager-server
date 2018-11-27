package de.pfann.budgetmanager.server.restservices.resources;

import de.pfann.budgetmanager.server.common.email.EmailService;
import de.pfann.budgetmanager.server.restservices.resources.email.ContactEmailGenerator;
import de.pfann.server.logging.core.LogUtil;

public class ContactResourceFacade {

    public EmailService emailService;

    public ContactResourceFacade(EmailService aEmailService){
        emailService = aEmailService;
    }

    public void sendEmail(String aSenderEmail, String aSenderName, String aTextmessage) {
        ContactEmailGenerator emailGenerator = new ContactEmailGenerator(aSenderEmail,aSenderName,aTextmessage);
        emailService.sendEmail("pfann.development@gmail.com",emailGenerator.getSubject(),emailGenerator.getContent());
    }

}
