package de.pfann.budgetmanager.server.jobengine.rotationjobs;

import de.pfann.budgetmanager.server.common.model.RotationEntry;
import de.pfann.budgetmanager.server.common.util.DateUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.Month;

public class YearlyRotationPatternTest {

    // attributes
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDateTime currentDateOfRun;
    private LocalDateTime lastExecuted;

    private RotationEntry rotationEntry;

    // class under test
    private RotationEntryPattern rotationEntryPattern;

    @Before
    public void setUp(){
        rotationEntryPattern = new YearlyRotationPattern();
        rotationEntry = new RotationEntry();
    }

    @Test
    public void testTodayIsBeforStartTime(){
        // prepare
        startDate = LocalDateTime.of(2018,5,20,1,1);
        endDate = LocalDateTime.of(3000,5,20,1,1);
        currentDateOfRun = LocalDateTime.of(2018,4,20,1,1);
        lastExecuted = LocalDateTime.of(1000,4,20,1,1);

        rotationEntry.setStart_at(DateUtil.asDate(startDate));
        rotationEntry.setEnd_at(DateUtil.asDate(endDate));
        rotationEntry.setLast_executed(DateUtil.asDate(lastExecuted));

        // execute
        boolean executable = rotationEntryPattern.isExecutable(currentDateOfRun,rotationEntry);

        // validate
        Assert.assertFalse(executable);
    }

    @Test
    public void testCurrentDateIsAfterEndDate(){
        // prepare
        startDate = LocalDateTime.of(2018,5,20,1,1);
        endDate = LocalDateTime.of(2019,5,20,1,1);
        currentDateOfRun = LocalDateTime.of(2020,4,20,1,1);
        lastExecuted = LocalDateTime.of(1000,4,20,1,1);

        rotationEntry.setStart_at(DateUtil.asDate(startDate));
        rotationEntry.setEnd_at(DateUtil.asDate(endDate));
        rotationEntry.setLast_executed(DateUtil.asDate(lastExecuted));

        // execute
        boolean executable = rotationEntryPattern.isExecutable(currentDateOfRun,rotationEntry);

        // validate
        Assert.assertFalse(executable);
    }

    @Test
    public void testAlreadyExecutedInCurrentYear(){
        // prepare
        startDate = LocalDateTime.of(2018,5,20,1,1);
        endDate = LocalDateTime.of(2030,5,20,1,1);
        currentDateOfRun = LocalDateTime.of(2019,7,20,1,1);
        lastExecuted = LocalDateTime.of(2019,3,20,1,1);

        rotationEntry.setStart_at(DateUtil.asDate(startDate));
        rotationEntry.setEnd_at(DateUtil.asDate(endDate));
        rotationEntry.setLast_executed(DateUtil.asDate(lastExecuted));

        // execute
        boolean executable = rotationEntryPattern.isExecutable(currentDateOfRun,rotationEntry);

        // validate
        Assert.assertFalse(executable);
    }

    @Test
    public void testCurrentRunIsBeforeNextExecutionTime(){
        // prepare
        startDate = LocalDateTime.of(2018,5,20,1,1);
        endDate = LocalDateTime.of(2030,5,20,1,1);
        currentDateOfRun = LocalDateTime.of(2019,4,20,1,1);
        lastExecuted = LocalDateTime.of(2012,3,20,1,1);

        rotationEntry.setStart_at(DateUtil.asDate(startDate));
        rotationEntry.setEnd_at(DateUtil.asDate(endDate));
        rotationEntry.setLast_executed(DateUtil.asDate(lastExecuted));

        // execute
        boolean executable = rotationEntryPattern.isExecutable(currentDateOfRun,rotationEntry);

        // validate
        Assert.assertFalse(executable);
    }

}
