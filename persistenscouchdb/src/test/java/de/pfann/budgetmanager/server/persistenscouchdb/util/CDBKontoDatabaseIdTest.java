package de.pfann.budgetmanager.server.persistenscouchdb.util;

import de.pfann.budgetmanager.server.persistenscouchdb.model.CDBKonto;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CDBKontoDatabaseIdTest {


    /**
     * values for id
     */


    private String username;
    private String konto;
    private String typ;

    String validateString;

    /**
     * class under test
     */

    public CDBEntryId cdbEntryId;

    @Before
    public void setUp(){
        username = "johannes-4213";
        konto = "d5n32";
        typ = CDBKonto.ENTRY_KONTO;

        validateString = "johannes-4213_d5n32_" + CDBKonto.ENTRY_KONTO;
    }

    @Test
    public void testGenerateId(){
        // execute
        String generatedId = CDBKontoDatabaseId.builder()
                .withUsername(username)
                .withKontoHash(konto)
                .withObjectTyp(CDBKonto.ENTRY_KONTO)
                .build()
                .toString();

        // validate
        Assert.assertTrue(generatedId.equals(validateString));
    }

    @Test
    public void testParsId(){
        // exucute
        System.out.println();
        CDBKontoDatabaseId entryId = CDBKontoDatabaseId.builder().build(validateString);
        // validate
        Assert.assertTrue(username.equals(entryId.getUsername()));
        Assert.assertTrue(konto.equals(entryId.getKontoHash()));
    }
}
