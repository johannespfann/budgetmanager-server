package de.pfann.budgetmanager.server.persistenscouchdb.model;

import java.time.LocalDateTime;

public class CDBRunAction {

    private String state;

    private String actionname;

    private LocalDateTime starttime;

    private LocalDateTime endtime;

    public CDBRunAction(String aActionName, String aState, LocalDateTime aStartTime, LocalDateTime aEndTime){
        state = aState;
        actionname = aActionName;
        starttime = aStartTime;
        endtime = aEndTime;
    }

    public String getState() {
        return state;
    }

    public String getActionname() {
        return actionname;
    }

    public LocalDateTime getStarttime() {
        return starttime;
    }

    public LocalDateTime getEndtime() {
        return endtime;
    }
}
