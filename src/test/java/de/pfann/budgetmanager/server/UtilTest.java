package de.pfann.budgetmanager.server;

import de.pfann.budgetmanager.server.util.Util;
import junit.framework.Assert;
import org.junit.Test;


public class UtilTest {

    @Test
    public void testRandomUserName(){
        Assert.assertTrue(Util.getUserNameWithUnique("johannes").contains("#"));
        Assert.assertTrue(Util.getUserNameWithUnique("johannes").contains("johannes"));
    }
}
