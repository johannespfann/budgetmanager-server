package de.pfann.budgetmanager.server.jobengine.core;

import de.pfann.budgetmanager.server.persistens.daos.RunSQLFacade;

public class RunUtil {

    public static boolean isFirstStart(RunSQLFacade runFacade) {
        if(runFacade.getLastRun() == null){
            return true;
        }
        return false;
    }

}
