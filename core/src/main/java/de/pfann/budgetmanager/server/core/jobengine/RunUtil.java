package de.pfann.budgetmanager.server.core.jobengine;

import de.pfann.budgetmanager.server.persistens.daos.RunFacade;

public class RunUtil {

    public static boolean isFirstStart(RunFacade runFacade) {
        if(runFacade.getLastRun() == null){
            return true;
        }
        return false;
    }

}
