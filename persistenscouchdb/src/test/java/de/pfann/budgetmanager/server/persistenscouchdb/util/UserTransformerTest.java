package de.pfann.budgetmanager.server.persistenscouchdb.util;

import de.pfann.budgetmanager.server.common.model.AppUser;
import de.pfann.budgetmanager.server.persistenscouchdb.model.CDBUser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UserTransformerTest {

    /**
     * attributes
     */

    private AppUser appUser;
    private CDBUser cdbUser;


    private String name;
    private String email;
    private String password;
    private boolean activated;
    private String encryptionText;

    @Before
    public void setUp(){
        name = "johannes";
        email = "johannes@pfann.de";
        activated = true;
        password = "keymaster";
        encryptionText = "text";

        appUser = new AppUser();
        appUser.setName(name);
        appUser.setEmail(email);
        appUser.setPassword(password);
        appUser.activate();
        appUser.setEncryptionText(encryptionText);

        cdbUser = new CDBUser();
        cdbUser.setUsername(name);
        cdbUser.setPassword(password);
        cdbUser.addEmail(email);
        cdbUser.activate();
        cdbUser.setEncryptionText(encryptionText);
    }

    @Test
    public void testUpdateCDBUser() {
        // prepare
        CDBUser cdbUser = new CDBUser();

        // execute
        UserTransformer.updateCDBUser(appUser,cdbUser);

        // validate
        Assert.assertEquals(cdbUser.getUsername(),name);
        Assert.assertEquals(cdbUser.getPassword(), password);
        Assert.assertEquals(cdbUser.getEmails().get(0), email);
        Assert.assertEquals(cdbUser.getEncryptiontext(), encryptionText);
        Assert.assertEquals(cdbUser.isActivated(), true);

    }


    @Test
    public void testCreateAppUser() {
        // execute
        AppUser appUser = UserTransformer.createAppUser(cdbUser);

        // validate
        Assert.assertEquals(appUser.getName(),name);
        Assert.assertEquals(appUser.getPassword(),password);
        Assert.assertEquals(appUser.getEmail(),email);
        Assert.assertEquals(appUser.isActivated(), true);
        Assert.assertEquals(appUser.getEncryptionText(),encryptionText);
    }
}
