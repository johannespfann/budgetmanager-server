package de.pfann.budgetmanager.server.model;

import java.util.Date;
import java.util.List;

public class V2StandingOrder {

    /**
     * standingorder header
     */

    private String hash;

    private String username;

    /**
     * standingorder dates
     */

    private String rotationStrategy;

    private Date startAt;

    private Date endAt;

    private Date lastExecuted;

    private Date lastModified;

    /**
     * entry data
     */

    private double amount;

    private String currency;

    private String memo;

    private List<String> tags;

    /**
     * constructor
     */

    public V2StandingOrder() {
        // default
    }

    public V2StandingOrder(String aHash,
                           String aUsername,
                           String aRotationStrategy,
                           Date aStartAt,
                           Date aEndAt,
                           Date aLastExecuted,
                           Date aLastModified,
                           double aAmount,
                           String aCurrency,
                           String aMemo,
                           List<String> aTags) {
        hash = aHash;
        username = aUsername;

        rotationStrategy = aRotationStrategy;
        startAt = aStartAt;
        endAt = aEndAt;
        lastExecuted = aLastExecuted;
        lastModified = aLastModified;

        amount = aAmount;
        currency = aCurrency;
        memo = aMemo;
        tags = aTags;
    }

    /**
     * setter
     */

    public void setUsername(String username) {
        this.username = username;
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

    public String getRotationStrategy() {
        return rotationStrategy;
    }

    public Date getStartAt() {
        return startAt;
    }

    public Date getEndAt() {
        return endAt;
    }

    public Date getLastExecuted() {
        return lastExecuted;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public double getAmount() {
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
}
