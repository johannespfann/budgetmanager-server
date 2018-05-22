package de.pfann.budgetmanager.server.core.rotationjob;

import de.pfann.budgetmanager.server.common.util.DateUtil;
import de.pfann.budgetmanager.server.core.rotationjobs.MonthlyRotationEntry;
import de.pfann.budgetmanager.server.persistens.model.RotationEntry;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.util.Date;

public class MonthlyRotationEntryTest {

    /**
     * dateobjects
     */

    private LocalDateTime today;

    private Date startDate;

    private Date lastExecutedThisMonth;

    private Date lastExecuted;


    @Before
    public void setUp(){
        today = LocalDateTime.of(2018, Month.MARCH,27,1,1);
        startDate = DateUtil.asDate(LocalDateTime.of(2018,Month.JANUARY,1,1,1));
        lastExecuted = DateUtil.asDate(LocalDateTime.of(2018,Month.FEBRUARY,16,1,1));
    }

    @Test
    public void testTodayIsBeforStartTime(){
        // prepare
        RotationEntry entry = new RotationEntry();
        entry.setStart_at(startDate);
        LocalDateTime now = LocalDateTime.of(2017,Month.APRIL,3,1,1);

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

        LocalDateTime now = LocalDateTime.of(2018,Month.FEBRUARY,18,1,1);
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

        LocalDateTime now = LocalDateTime.of(2018,Month.FEBRUARY,16,1,1);
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

        LocalDateTime now = LocalDateTime.of(2018,Month.MARCH,20,1,1);

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

        LocalDateTime now = LocalDateTime.of(2018,Month.MARCH,16,1,1);

        // execute
        boolean executable = new MonthlyRotationEntry().isExecutable(now ,entry);

        // validate
        Assert.assertTrue(executable);
    }

    @Test
    public void testLastDaysConflictOfMonth(){
        // prepare
        LocalDateTime today = LocalDateTime.of(2018,2,28,1,1);
        LocalDateTime startDate = LocalDateTime.of(2017,1,1,1,1);
        LocalDateTime lastExecuted = LocalDateTime.of(2018,1,30,1,1);

        RotationEntry entry = new RotationEntry();
        entry.setStart_at(DateUtil.asDate(startDate));
        entry.setLast_executed(DateUtil.asDate(lastExecuted));
        // execute
        boolean executable = new MonthlyRotationEntry().isExecutable(today,entry);

        // validate
        Assert.assertTrue(executable);
    }


    public Date convert(LocalDate aLocalDate){
        return Date.from(aLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }


}
