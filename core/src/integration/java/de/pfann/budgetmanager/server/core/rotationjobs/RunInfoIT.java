package de.pfann.budgetmanager.server.core.rotationjobs;

import de.pfann.budgetmanager.server.persistens.core.SessionDistributor;
import de.pfann.budgetmanager.server.persistens.daos.RunDao;
import de.pfann.budgetmanager.server.persistens.daos.RunInfoDao;
import de.pfann.budgetmanager.server.persistens.model.Run;
import de.pfann.budgetmanager.server.persistens.model.RunInfo;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class RunInfoIT {



    /**
     * testobjects
     */

    private RunInfo runInfo;

    private Run run;

    private String test_identifier;

    /**
     * helper classes
     */

    private RunDao runDao;

    /**
     * class under test
     */
    private RunInfoDao runInfoDao;


    @Before
    public void setUp() {
        // Setup db befor each test
        SessionDistributor.createForIT();

        runInfoDao = RunInfoDao.create();

        runDao = RunDao.create();


    }

    @Test
    public void testSaveRunDao(){
        // prepare
        Run run = new Run(LocalDateTime.now());
        runDao.save(run);
        test_identifier = "test_identifier";
        runInfo = new RunInfo(run, test_identifier);
        runInfo.start();
        runInfo.stop("asdf");
        runInfoDao.save(runInfo);

        // execute
        List<RunInfo> runInfos = runInfoDao.getAll();

        // validate
        Assert.assertTrue(!runInfos.isEmpty());
    }


    @After
    public void cleanUp(){
        // close db after each test
        SessionDistributor.closeSessions();
    }

}
