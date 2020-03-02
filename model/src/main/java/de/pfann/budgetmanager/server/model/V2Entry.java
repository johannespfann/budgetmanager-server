package de.pfann.budgetmanager.server.model;

import java.util.Date;
import java.util.List;

public class V2Entry {

    /**
     * header attribures
     */

    private String hash;

    private String username;

    private Date createdAt;

    /**
     * data attributes
     */

    private double amount;

    private String currency;

    private String memo;

    private List<String> tags;


    /**
     * constructor
     */

    public V2Entry() {
        // default
    }

    public V2Entry(String aHash, String aUsername, Date aCreatedAd, double aAmount, String aCurrency, String aMemo, List<String> aTags) {
        hash = aHash;
        username = aUsername;
        createdAt = aCreatedAd;
        amount = aAmount;
        currency = aCurrency;
        memo = aMemo;
        tags = aTags;
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

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
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

    /**
     * getter
     */

    public String getHash() {
        return hash;
    }

    public String getUsername() {
        return username;
    }

    public Date getCreatedAt() {
        return createdAt;
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


    @Override
    public String toString() {
        return "V2Entry{" +
                "hash='" + hash + '\'' +
                ", username='" + username + '\'' +
                ", createdAt=" + createdAt +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                ", memo='" + memo + '\'' +
                ", tags=" + tags +
                '}';
    }
}
