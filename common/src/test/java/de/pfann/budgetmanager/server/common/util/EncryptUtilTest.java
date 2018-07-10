package de.pfann.budgetmanager.server.common.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;
import org.junit.Assert;
import org.junit.Test;

import java.security.Key;
import java.util.Date;

public class EncryptUtilTest {

    private String key = "keymaster";

    @Test
    public void testEncryptKey(){
        // prepare
        String text = "{username='johannes-1234', role='user', createdAt=Tue Jul 10 19:24:35 CEST 2018, expiredate=Thu Jul 12 19:24:35 CEST 2018}";
        text.trim();

        // execute
        String value = EncryptUtil.encrypt( text.trim(),key);
        String result = EncryptUtil.decrypt(value,key);
        System.out.println("value " + value);
        System.out.println("result: " + result);

        // validate
        Assert.assertEquals(text,result);
    }





}
