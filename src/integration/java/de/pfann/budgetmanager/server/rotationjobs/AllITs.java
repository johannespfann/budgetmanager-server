package de.pfann.budgetmanager.server.rotationjobs;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        JobExecuterEngineIT.class,
        RunDaoIT.class ,
        RunInfoIT.class })
public class AllITs {
}
