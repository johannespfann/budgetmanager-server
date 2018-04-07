package de.pfann.budgetmanager.server.core.rotationjobs;

import de.pfann.budgetmanager.server.core.persistens.core.SessionDistributor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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

    @Test
    public void testTest(){
        System.out.println("works!");
    }


    @After
    public void cleanUp(){
        // close db after each test
        SessionDistributor.closeSessions();
    }


}
