package de.pfann.budgetmanager.server.persistenscouchdb.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

public class CDBEntryIdTest {


    /**
     * values for id
     */

    private String prefix;
    private String username;
    private LocalDateTime localDateTime;
    private String hash;

    String validateString;

    /**
     * class under test
     */

    public CDBEntryId cdbEntryId;

    @Before
    public void setUp(){
        prefix = CDBEntryId.TYP_PREFIX;
        username = "johannes-4213";
        hash = "23j2oi3k2ll";

        validateString = "entry:johannes-4213:23j2oi3k2ll";
    }

    @Test
    public void testGenerateId(){
        String generatedId = CDBEntryId.createBuilder()
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
        CDBEntryId entryId = CDBEntryId.createBuilder().build(validateString);

        // validate
        Assert.assertTrue(username.equals(entryId.getUsername()));
        Assert.assertTrue(prefix.equals("entry"));
        Assert.assertTrue(hash.equals(entryId.getHash()));
    }
}
