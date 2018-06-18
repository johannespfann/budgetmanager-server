package de.pfann.budgetmanager.server.jobengine.core;

import de.pfann.budgetmanager.server.common.model.Run;
import de.pfann.budgetmanager.server.persistens.daos.RunSQLFacade;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;


public class JobEngineTest {

    /**
     * mocks
     */

    private RunSQLFacade runFacade;
    private Job dummyJob;
    private RunProvider runProvider;

    /**
     * class under test
     */

    private JobEngine jobEngine;


    @Before
    public void setUp(){

        runFacade = Mockito.mock(RunSQLFacade.class);

        runProvider = Mockito.mock(RunProvider.class);

        dummyJob = Mockito.mock(DummyJob.class);
        List<JobRunner> jobs = new ArrayList<>();
        jobs.add(new JobRunner(dummyJob));

        jobEngine = new JobEngine(runFacade, runProvider, jobs);
    }

    @Test
    public void test_initRun_first_time(){
        Mockito.when(runFacade.getLastRun()).thenReturn(null);
        jobEngine.start();
    }

    @Test
    public void test_jobengine_with_more_runs() throws JobException {
        List<Run> runs = new ArrayList<Run>();
        Run run1 = new Run();
        Run run2 = new Run();
        Run run3 = new Run();

        runs.add(run1);
        runs.add(run2);
        runs.add(run3);

        Mockito.when(runProvider.prepareRuns(Matchers.any(LocalDateTime.class), Matchers.any(LocalDateTime.class))).thenReturn(runs);
        Mockito.when(runFacade.getAllRuns()).thenReturn(runs);
        Mockito.when(runFacade.getLastRun()).thenReturn(run1);

        jobEngine.start();

        Mockito.verify(dummyJob, Mockito.times(3)).execute(Matchers.any(Run.class));

    }

}
