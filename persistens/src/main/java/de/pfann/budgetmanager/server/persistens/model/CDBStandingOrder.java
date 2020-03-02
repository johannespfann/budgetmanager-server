package de.pfann.budgetmanager.server.persistens.model;

import java.util.Date;
import java.util.List;

public class CDBStandingOrder extends AbstractDocument {

    /**
     * standingorder header
     */

    private String hash;

    private String username;

    /**
     * standingorder data
     */

    private Date start_at;

    private Date end_at;

    private Date last_executed;

    private Date last_modified;

    private String rotation_strategy;

    /**
     * generate content
     */

    private double amount;

    private String currency;

    private String memo;

    private List<String> tags;

    /**
     * constructor
     */

    public CDBStandingOrder() {
        // default
    }

    /**
     * getter
     */
    public String getHash() {
        return hash;
    }

    public String getUsername() {
        return username;
    }

    public Date getStart_at() {
        return start_at;
    }

    public Date getEnd_at() {
        return end_at;
    }

    public Date getLast_executed() {
        return last_executed;
    }

    public Date getLast_modified() {
        return last_modified;
    }

    public Double getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public String getMemo() {
        return memo;
    }

    public List<String> getTags() {
        return tags;
    }

    public String getRotation_strategy() {
        return rotation_strategy;
    }

    /**
     * setter
     */

    public void setHash(String hash) {
        this.hash = hash;
    }

    public void setRotation_strategy(String rotation_strategy) {
        this.rotation_strategy = rotation_strategy;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setStart_at(Date start_at) {
        this.start_at = start_at;
    }

    public void setEnd_at(Date end_at) {
        this.end_at = end_at;
    }

    public void setLast_executed(Date last_executed) {
        this.last_executed = last_executed;
    }

    public void setLast_modified(Date last_modified) {
        this.last_modified = last_modified;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }


}
