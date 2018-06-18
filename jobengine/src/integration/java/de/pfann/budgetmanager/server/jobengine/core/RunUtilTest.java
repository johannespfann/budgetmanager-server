package java.de.pfann.budgetmanager.server.jobengine.core;

import de.pfann.budgetmanager.server.common.model.Run;
import de.pfann.budgetmanager.server.jobengine.core.RunUtil;
import de.pfann.budgetmanager.server.persistens.core.SessionDistributor;
import de.pfann.budgetmanager.server.persistens.daos.RunSQLFacade;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RunUtilTest {

    // helper
    private RunSQLFacade runFacade;

    @Before
    public void setUp() {
        // Setup db befor each test
        SessionDistributor.createForIT();

        runFacade = new RunSQLFacade();
    }

    @Test
    public void test_isFirstStart(){
        Assert.assertTrue(RunUtil.isFirstStart(runFacade));
    }

    @Test
    public void test_isNotFirstStart(){
        // prepare
        Run run = new Run();
        runFacade.persist(run);

        Assert.assertFalse(RunUtil.isFirstStart(runFacade));
    }

    @After
    public void cleanUp(){
        // close db after each test
        SessionDistributor.closeSessions();
    }
}
