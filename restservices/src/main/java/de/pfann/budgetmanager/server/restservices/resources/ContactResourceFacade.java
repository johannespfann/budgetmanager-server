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
        LogUtil.info(this.getClass(), "Send Email: ");
        LogUtil.info(this.getClass(), "- from: " + aSenderName);
        LogUtil.info(this.getClass(), "      : " + aSenderEmail);
        LogUtil.info(this.getClass(), "- text: " + aTextmessage);

        ContactEmailGenerator emailGenerator = new ContactEmailGenerator(aSenderEmail,aSenderName,aTextmessage);
        emailService.sendEmail("pfann.development@gmail.com",emailGenerator.getSubject(),emailGenerator.getContent());
    }

}