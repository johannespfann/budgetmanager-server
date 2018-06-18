package de.pfann.budgetmanager.server.jobengine.core;

import de.pfann.budgetmanager.server.common.model.Run;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;

public class RunProviderTest {

    // attributes for tests

    private Run lastRun;

    private Run currentRun;

    private TimeInterval hourly;

    private TimeInterval daily;


    // class under Test

    private RunProvider runProvider;


    @Before
    public void setUp(){
        LocalDateTime lastLocalDateTime = LocalDateTime.of(2018,5,20,1,20,12);
        LocalDateTime currentLocalDateTim = LocalDateTime.of(2018,5,25,1,19,25);

        lastRun = new Run(lastLocalDateTime);
        currentRun = new Run(currentLocalDateTim);

        hourly = new Hourly();
        daily = new Daily();
    }

    @Test
    public void test_prepareRuns_With_Daily(){
        // prepare
        runProvider = new RunProviderImpl(daily);

        // exceute
        List<Run> runs = runProvider.prepareRuns(lastRun.getExecuted_at(),currentRun.getExecuted_at());

        // validate
        Assert.assertEquals(runs.size(),4);
    }

    @Test
    public void test_prepareRuns_With_Hourly(){
        // prepare
        runProvider = new RunProviderImpl(hourly);

        // exceute
        List<Run> runs = runProvider.prepareRuns(lastRun.getExecuted_at(),currentRun.getExecuted_at());

        // validate
        Assert.assertEquals(runs.size(),119);
    }
}
