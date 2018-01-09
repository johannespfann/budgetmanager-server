package de.pfann.budgetmanager.server;

import de.pfann.budgetmanager.server.login.LoginUtil;
import junit.framework.Assert;
import org.junit.Test;


public class UtilTest {

    @Test
    public void testRandomUserName(){
        Assert.assertTrue(LoginUtil.getUserNameWithUnique("johannes").contains("#"));
        Assert.assertTrue(LoginUtil.getUserNameWithUnique("johannes").contains("johannes"));
    }
}
