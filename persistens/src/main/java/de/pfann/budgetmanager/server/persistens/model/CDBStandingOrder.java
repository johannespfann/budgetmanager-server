package de.pfann.budgetmanager.server.persistens.model;

import java.util.Date;

public class CDBStandingOrder extends AbstractDocument {

    /**
     * standingorder header
     */

    private String hash;

    private String username;

    /**
     * standingorder data
     */

    private Date startAt;

    private Date endAt;

    private Date last_executed;

    private Date last_modified;

    /**
     * generate content
     */

    private Double amount;

    private String currency;

    private String memo;

    private String[] tags;

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

    public Date getStartAt() {
        return startAt;
    }

    public Date getEndAt() {
        return endAt;
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

    public String[] getTags() {
        return tags;
    }

    /**
     * setter
     */

    public void setHash(String hash) {
        this.hash = hash;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setStartAt(Date startAt) {
        this.startAt = startAt;
    }

    public void setEndAt(Date endAt) {
        this.endAt = endAt;
    }

    public void setLast_executed(Date last_executed) {
        this.last_executed = last_executed;
    }

    public void setLast_modified(Date last_modified) {
        this.last_modified = last_modified;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

}
