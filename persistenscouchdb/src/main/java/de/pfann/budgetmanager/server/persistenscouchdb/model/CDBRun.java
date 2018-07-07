package de.pfann.budgetmanager.server.persistenscouchdb.model;

import de.pfann.budgetmanager.server.persistenscouchdb.core.AbstractDocument;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class CDBRun extends AbstractDocument {

    private Date executedAt;

    private List<CDBRunAction> runactions;

    public CDBRun(Date aExecutedAd){
        executedAt = aExecutedAd;
        runactions = new LinkedList<>();
    }

    public Date getExecutedAt() {
        return executedAt;
    }

    public void addRunAction(CDBRunAction aRunAction){
        runactions.add(aRunAction);
    }

    public List<CDBRunAction> getRunactions() {
        return runactions;
    }

    public void setRunactions(List<CDBRunAction> runactions) {
        this.runactions = runactions;
    }
}
