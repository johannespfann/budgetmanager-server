package de.pfann.budgetmanager.server.persistenscouchdb.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

public class CDBKontoDatabaseIdTest {


    /**
     * values for id
     */

    private String prefix;
    private String username;
    private String konto;

    String validateString;

    /**
     * class under test
     */

    public CDBEntryId cdbEntryId;

    @Before
    public void setUp(){
        prefix = CDBKontoDatabaseId.APPLICATION_PREFIX;
        username = "johannes-4213";
        konto = "d5n32";

        validateString = "bm:johannes-4213:d5n32";
    }

    @Test
    public void testGenerateId(){
        // execute
        String generatedId = CDBKontoDatabaseId.builder()
                .withUsername(username)
                .withDatabaseName(konto)
                .build()
                .toString();

        // validate
        Assert.assertTrue(generatedId.equals(validateString));
    }

    @Test
    public void testParsId(){
        // exucute
        CDBKontoDatabaseId entryId = CDBKontoDatabaseId.builder().build(validateString);

        // validate
        Assert.assertTrue(username.equals(entryId.getUsername()));
        Assert.assertTrue(prefix.equals(prefix));
        Assert.assertTrue(konto.equals(entryId.getDatabasename()));
    }
}
