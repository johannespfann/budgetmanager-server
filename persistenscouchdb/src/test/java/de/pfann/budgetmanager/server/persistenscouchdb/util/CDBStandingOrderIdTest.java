package de.pfann.budgetmanager.server.persistenscouchdb.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class CDBStandingOrderIdTest {

    /**
     * values for id
     */

    private String prefix;
    private String username;
    private String hash;


    private String validateString;

    /**
     * class under test
     */

    public CDBStandingOrderId cdbStandingOrderId;

    @Before
    public void setUp(){
        prefix = CDBEntryId.TYP_PREFIX;
        username = "johannes-4213";
        hash = "23j2oi3k2ll";

        validateString = "standingorder:johannes-4213:23j2oi3k2ll";
    }

    @Test
    public void testGenerateId(){
        String generatedId = CDBStandingOrderId.createBuilder()
                .withUsername(username)
                .withHash(hash)
                .build()
                .toString();

        // validate
        Assert.assertTrue(generatedId.equals(validateString));
    }

    @Test
    public void testParsId(){
        // exucute
        cdbStandingOrderId = CDBStandingOrderId.createBuilder().build(validateString);
        // validate
        Assert.assertTrue(username.equals(cdbStandingOrderId.getUsername()));
        Assert.assertTrue(prefix.equals("entry"));
        Assert.assertTrue(hash.equals(cdbStandingOrderId.getHash()));
    }

}
