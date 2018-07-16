package de.pfann.budgetmanager.server.common.email;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailService {

    /**
     *  PropertyKEYS
     */

    private static final String MAIL_SMTP_HOST = "mail.smtp.host";
    private static final String MAIL_SMTP_AUTH = "mail.smtp.auth";
    private static final String MAIL_SMTP_STARTTLS_ENABLE = "mail.smtp.starttls.enable";
    private static final String MAIL_SMTP_PORT = "mail.smtp.port";
    private static final String SMTP_PROTOCOL = "smtp";

    /**
     *  members
     */

    private String smtpHost;
    private String smtpAuth;
    private String smtpStartTls;
    private String smtpPort;
    private String email;
    private String password;


    public EmailService(String aSmtpHost, String aSmtpAuth, String aSmtpStartTls, String aSmtpPort, String aEmail, String aPassword){
        smtpHost = aSmtpHost;
        smtpAuth = aSmtpAuth;
        smtpStartTls = aSmtpStartTls;
        smtpPort = aSmtpPort;
        email = aEmail;
        password = aPassword;
    }

    public void sendEmail(String aReceiverEmail, String aSubject, String aContent) {

        Properties props = new Properties();
        props.setProperty(MAIL_SMTP_HOST, smtpHost );
        props.setProperty(MAIL_SMTP_AUTH, smtpAuth );
        props.put(MAIL_SMTP_STARTTLS_ENABLE, smtpStartTls);
        props.setProperty(MAIL_SMTP_PORT, smtpPort);
        MailAuthenticator auth = new MailAuthenticator(email, password);
        Session session = Session.getDefaultInstance(props, auth);

        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(email));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(
                    aReceiverEmail, false));
            msg.setSubject(aSubject);
            msg.setText(aContent);
            Transport transport = session.getTransport(SMTP_PROTOCOL);
            transport.connect(smtpHost, email, password);
            transport.sendMessage(msg, msg.getAllRecipients());
            transport.close();
        }
        catch (Exception e) {
            e.printStackTrace( );
        }
    }
}
