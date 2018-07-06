package de.pfann.budgetmanager.server.persistenscouchdb.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CDBUserIdTest {

    /**
     * attributes
     */
    private String username;

    private String userIdToValidate;
    /**
     * class under test
     */

    private CDBUserId cdbUserId;



    @Before
    public void setUp() {
        username = "johannes-5437";
        userIdToValidate = "user:johannes-5437";
    }

    @Test
    public void testParseId(){
        // execute
        cdbUserId = CDBUserId.create(username);

        // validate
        Assert.assertTrue(cdbUserId.toString().equals(userIdToValidate));
    }

    @Test
    public void testGenerateId(){
        // execute
        cdbUserId = CDBUserId.createWithString(userIdToValidate);

        // validate
        Assert.assertEquals(cdbUserId.getUsername(),username);
    }

}
