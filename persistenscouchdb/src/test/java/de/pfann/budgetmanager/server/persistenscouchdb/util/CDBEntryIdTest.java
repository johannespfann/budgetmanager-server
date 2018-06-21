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
    private String konto;
    private String hash;
    private int year;
    private int month;

    String validateString;

    /**
     * class under test
     */

    public CDBEntryId cdbEntryId;

    @Before
    public void setUp(){
        prefix = CDBEntryId.TYP_PREFIX;
        username = "johannes-4213";
        localDateTime = LocalDateTime.of(2018,10,4,1,1,1);
        year = 2018;
        month = 10;
        hash = "23j2oi3k2ll";
        konto = "k2mji122";

        validateString = "entry:johannes-4213:k2mji122:2018:10:23j2oi3k2ll";
    }

    @Test
    public void testGenerateId(){
        String generatedId = CDBEntryId.createBuilder()
                .withUsername(username)
                .withKonto(konto)
                .withCreatedAt(localDateTime)
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
        Assert.assertTrue(konto.equals(entryId.getKonto()));
        Assert.assertTrue(year == entryId.getYear());
        Assert.assertTrue(month == entryId.getMonth());
    }
}
