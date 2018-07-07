package de.pfann.budgetmanager.server.jobengine.core;

import de.pfann.budgetmanager.server.common.facade.RunFacade;
import de.pfann.budgetmanager.server.persistens.daos.RunSQLFacade;

public class RunUtil {

    public static boolean isFirstStart(RunFacade runFacade) {
        if(runFacade.getLastRun() == null){
            return true;
        }
        return false;
    }

}
