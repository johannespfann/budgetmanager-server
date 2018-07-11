package de.pfann.budgetmanager.server.persistenscouchdb.model;

import java.time.LocalDateTime;
import java.util.Date;

public class CDBRunAction {

    private String state;

    private String actionname;

    private Date starttime;

    private Date endtime;

    public CDBRunAction(){
        // default
    }

    public CDBRunAction(String aActionName, String aState, Date aStartTime, Date aEndTime){
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

    public Date getStarttime() {
        return starttime;
    }

    public Date getEndtime() {
        return endtime;
    }
}
