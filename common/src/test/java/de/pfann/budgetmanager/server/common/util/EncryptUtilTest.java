package de.pfann.budgetmanager.server.common.util;

import org.junit.Assert;
import org.junit.Test;

public class EncryptUtilTest {

    private String key = "keymaster";

    @Test
    public void testEncryptKey() {
        // prepare
        String text = "{username='johannes-1234', role='user', createdAt=Tue Jul 10 19:24:35 CEST 2018, expiredate=Thu Jul 12 19:24:35 CEST 2018}";

        // execute
        String value = EncryptUtil.encrypt( text.trim(),key);
        String result = EncryptUtil.decrypt(value,key);

        // validate
        Assert.assertEquals(text,result);
    }
}
