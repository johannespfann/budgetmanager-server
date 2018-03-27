package de.pfann.budgetmanager.server.rotationjobs;

import de.pfann.budgetmanager.server.persistens.core.SessionDistributor;
import org.junit.After;
import org.junit.Before;

public class JobExecuterEngineIT {

    private RunDao runDao;

    private RunInfoDao runInfoDao;

    /**
     * class under test
     */

    private JobExecuterEngine jobExecuterEngine;

    @Before
    public void setUp() {
        // Setup db befor each test
        SessionDistributor.createForIT();
    }


    @After
    public void cleanUp(){
        // close db after each test
        SessionDistributor.closeSessions();
    }


}
