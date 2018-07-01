package de.pfann.budgetmanager.server.common.util;

import org.junit.Assert;
import org.junit.Test;

public class HashUtilTest {

    @Test
    public void testGernateHash(){
        String output = HashUtil.getUniueHash();
        Assert.assertTrue(output.length() > 0);
    }

    @Test
    public void testEightDigitGenerateRandomId(){
        String output = HashUtil.getEightDigitRandomId();
        Assert.assertTrue(output.length() == 8);
    }
}
