package de.pfann.budgetmanager.server.restservices.resources.email;

import de.pfann.budgetmanager.server.common.email.EmailGenerator;
import org.junit.Before;
import org.junit.Test;

public class EmailTest {

    /**
     * values
     */

    private String username;
    private String email;
    private String activationCode;

    /**
     * class under test
     */

    private EmailGenerator emailGenerator;

    @Before
    public void setUp(){
        username = "johannes-1234";
        email = "jopfann@gmail.com";
        activationCode = "MDKDJ568DR";
        emailGenerator = new ActivationEmailGenerator(username,email,activationCode);
    }

    @Test
    public void testEmail(){
        System.out.println(emailGenerator.getContent());
    }
}
