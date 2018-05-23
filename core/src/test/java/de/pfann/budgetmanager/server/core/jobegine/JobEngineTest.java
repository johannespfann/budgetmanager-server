package de.pfann.budgetmanager.server.core.jobegine;

import de.pfann.budgetmanager.server.core.jobengine.*;
import de.pfann.budgetmanager.server.persistens.daos.RunFacade;
import de.pfann.budgetmanager.server.persistens.model.Run;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;


public class JobEngineTest {

    /**
     * mocks
     */

    private RunFacade runFacade;
    private Job dummyJob;
    private RunProvider runProvider;

    /**
     * class under test
     */

    private JobEngine jobEngine;


    @Before
    public void setUp(){

        runFacade = mock(RunFacade.class);

        runProvider = mock(RunProvider.class);

        dummyJob = mock(DummyJob.class);
        List<Job> jobs = new ArrayList<>();
        jobs.add(dummyJob);

        jobEngine = new JobEngine(runFacade, runProvider, jobs);
    }

    @Test
    public void test_initRun_first_time(){
        when(runFacade.getLastRun()).thenReturn(null);
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

        when(runProvider.prepareRuns(any(LocalDateTime.class),any(LocalDateTime.class))).thenReturn(runs);
        when(runFacade.getAllRuns()).thenReturn(runs);
        when(runFacade.getLastRun()).thenReturn(run1);

        jobEngine.start();

        verify(dummyJob,times(3)).execute(any(Run.class));

    }

}
