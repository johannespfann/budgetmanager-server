package de.pfann.budgetmanager.server.rotationjobs;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.Date;

public class MonthlyRotationEntryTest {

    /**
     * dateobjects
     */

    private LocalDate today;

    private Date startDate;

    private Date lastExecutedThisMonth;

    private Date lastExecuted;


    @Before
    public void setUp(){
        today = LocalDate.of(2018, Month.MARCH,27);
        startDate = convert(LocalDate.of(2018,Month.JANUARY,1));
        lastExecuted = convert(LocalDate.of(2018,Month.FEBRUARY,16));
    }

    @Test
    public void testTodayIsBeforStartTime(){
        // prepare
        RotationEntry entry = new RotationEntry();
        entry.setStart_at(startDate);
        LocalDate now = LocalDate.of(2017,Month.APRIL,3);

        // execute
        boolean executable = new MonthlyRotationEntry().isExecutable(now,entry);

        // validate
        Assert.assertFalse(executable);
    }


    @Test
    public void testLastExecutedThisMonth(){
        // prepare
        RotationEntry entry = new RotationEntry();
        entry.setStart_at(startDate);
        entry.setLast_executed(lastExecuted);

        LocalDate now = LocalDate.of(2018,Month.FEBRUARY,18);
        // execute
        boolean executable = new MonthlyRotationEntry().isExecutable(now ,entry);

        // validate
        Assert.assertFalse(executable);
    }

    @Test
    public void testLastExecutedLastMonthSameDay(){
        // prepare
        RotationEntry entry = new RotationEntry();
        entry.setStart_at(startDate);
        entry.setLast_executed(lastExecuted);

        LocalDate now = LocalDate.of(2018,Month.FEBRUARY,16);
        // execute
        boolean executable = new MonthlyRotationEntry().isExecutable(now ,entry);

        // validate
        Assert.assertFalse(executable);

    }

    @Test
    public void testLastExecutedLastMonthAfterToday(){
        RotationEntry entry = new RotationEntry();
        entry.setStart_at(startDate);
        entry.setLast_executed(lastExecuted);

        LocalDate now = LocalDate.of(2018,Month.MARCH,20);

        // execute
        boolean executable = new MonthlyRotationEntry().isExecutable(now ,entry);

        // validate
        Assert.assertFalse(executable);
    }

    @Test
    public void testLastExecutedLastMonthSameToday(){
        RotationEntry entry = new RotationEntry();
        entry.setStart_at(startDate);
        entry.setLast_executed(lastExecuted);

        LocalDate now = LocalDate.of(2018,Month.MARCH,16);

        // execute
        boolean executable = new MonthlyRotationEntry().isExecutable(now ,entry);

        // validate
        Assert.assertTrue(executable);
    }



    public Date convert(LocalDate aLocalDate){
        return Date.from(aLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }


}
