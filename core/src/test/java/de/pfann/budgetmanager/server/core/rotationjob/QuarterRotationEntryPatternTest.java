package de.pfann.budgetmanager.server.core.rotationjob;

import de.pfann.budgetmanager.server.common.util.DateUtil;
import de.pfann.budgetmanager.server.core.rotationjobs.QuarterRotationEntryPattern;
import de.pfann.budgetmanager.server.core.rotationjobs.RotationEntryPattern;
import de.pfann.budgetmanager.server.persistens.model.RotationEntry;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

public class QuarterRotationEntryPatternTest {

    /**
     * attributes
     */

    /**
     * class under test
     */

    private RotationEntryPattern quarterRotationEntryPattern;


    @Before
    public void Setup(){
        quarterRotationEntryPattern = new QuarterRotationEntryPattern();
    }

    @Test
    public void test_startTime_not_rearched(){

        LocalDateTime startTime = LocalDateTime.of(2018,6,1,4,4);

        RotationEntry entry = new RotationEntry();
        entry.setStart_at(DateUtil.asDate(startTime));
        entry.setEnd_at(DateUtil.getMaximumDate());
        entry.setLast_executed(DateUtil.getMinimumDate());

        LocalDateTime now = LocalDateTime.of(2018,5,23,16,10,40);

        boolean result = quarterRotationEntryPattern.isExecutable(now,entry);

        Assert.assertFalse(result);
    }

    @Test
    public void test_after_endtime(){

        LocalDateTime startTime = LocalDateTime.of(2018,3,1,4,4);
        LocalDateTime endTime = LocalDateTime.of(2018,4,1,4,4);

        RotationEntry entry = new RotationEntry();
        entry.setStart_at(DateUtil.asDate(startTime));
        entry.setEnd_at(DateUtil.asDate(endTime));
        entry.setLast_executed(DateUtil.getMinimumDate());

        LocalDateTime now = LocalDateTime.of(2018,5,23,16,10,40);

        boolean result = quarterRotationEntryPattern.isExecutable(now,entry);

        Assert.assertFalse(result);
    }


    @Test
    public void test_was_nerver_executed(){

        LocalDateTime startTime = LocalDateTime.of(2018,3,1,4,4);
        LocalDateTime endTime = LocalDateTime.of(2018,7,1,4,4);

        RotationEntry entry = new RotationEntry();
        entry.setStart_at(DateUtil.asDate(startTime));
        entry.setEnd_at(DateUtil.asDate(endTime));
        entry.setLast_executed(DateUtil.getMinimumDate());

        LocalDateTime now = LocalDateTime.of(2018,5,23,16,10,40);

        boolean result = quarterRotationEntryPattern.isExecutable(now,entry);

        Assert.assertTrue(result);
    }

    @Test
    public void test_was_not_executed_in_quartal(){

        LocalDateTime endTime = LocalDateTime.of(2022,12,1,4,4);
        LocalDateTime startTime = LocalDateTime.of(2017,7,1,4,4);
        LocalDateTime lastExecuted = LocalDateTime.of(2018,6,2,1,1);

        RotationEntry entry = new RotationEntry();
        entry.setStart_at(DateUtil.asDate(startTime));
        entry.setEnd_at(DateUtil.asDate(endTime));
        entry.setLast_executed(DateUtil.asDate(lastExecuted));

        LocalDateTime now = LocalDateTime.of(2018,7,3,16,10,40);

        boolean result = quarterRotationEntryPattern.isExecutable(now,entry);

        Assert.assertTrue(result);
    }

    @Test
    public void test_was_executed_in_quartal(){

        LocalDateTime endTime = LocalDateTime.of(2022,12,1,4,4);
        LocalDateTime startTime = LocalDateTime.of(2017,4,1,4,4);
        LocalDateTime lastExecuted = LocalDateTime.of(2018,4,2,1,1);

        RotationEntry entry = new RotationEntry();
        entry.setStart_at(DateUtil.asDate(startTime));
        entry.setEnd_at(DateUtil.asDate(endTime));
        entry.setLast_executed(DateUtil.asDate(lastExecuted));

        LocalDateTime now = LocalDateTime.of(2018,5,3,16,10,40);

        boolean result = quarterRotationEntryPattern.isExecutable(now,entry);

        Assert.assertFalse(result);
    }

    /*
[Timer-0] INFO de.pfann.budgetmanager.server.core.rotationjobs.QuarterRotationEntryPattern - Now       : 2018-05-23T23:14:14.183
[Timer-0] INFO de.pfann.budgetmanager.server.core.rotationjobs.QuarterRotationEntryPattern - StartTime : 2018-05-23T23:11:42
[Timer-0] INFO de.pfann.budgetmanager.server.core.rotationjobs.QuarterRotationEntryPattern - EndTime   : 3500-07-04T12:30
[Timer-0] INFO de.pfann.budgetmanager.server.core.rotationjobs.QuarterRotationEntryPattern - LastExe   : 2018-05-23T23:12:14
[Timer-0] INFO de.pfann.budgetmanager.server.core.rotationjobs.QuarterRotationEntryPattern - NextTime  : 2018-08-23T23:12:14
[Timer-0] INFO de.pfann.budgetmanager.server.core.rotationjobs.QuarterRotationEntryPattern - default -> false

     */

}
