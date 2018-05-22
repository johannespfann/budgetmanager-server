package de.pfann.budgetmanager.server.core;

import de.pfann.budgetmanager.server.core.jobengine.Hourly;
import org.junit.Test;

public class JobEngineTest {

    @Test
    public void test(){
        System.out.println(new Hourly().getTimePerMilliSecond());
    }

}
