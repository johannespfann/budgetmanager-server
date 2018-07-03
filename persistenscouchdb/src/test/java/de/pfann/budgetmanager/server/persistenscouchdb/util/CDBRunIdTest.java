package de.pfann.budgetmanager.server.persistenscouchdb.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

public class CDBRunIdTest {

    /**
     * values
     */

    private int year;
    private int month;
    private int dayOfMonth;
    private int hour;
    private int minute;
    private int second;

    @Before
    public void setUp(){
        year = 2018;
        month = 3;
        dayOfMonth = 4;
        hour = 4;
        minute = 5;
        second = 3;
    }

    @Test
    public void testGenerateId(){
        // prepare
        LocalDateTime time = LocalDateTime.of(year, month, dayOfMonth, hour, minute, second);

        // execute
        String id = CDBRunId.createId(time);

        // validate
        System.out.println(id);
    }
}
