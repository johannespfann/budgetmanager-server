package de.pfann.budgetmanager.server.persistens.model;

import java.util.Date;

public class CDBEntry extends AbstractDocument {


    /**
     * header attribures
     */


    private String hash;

    private String username;

    private Date createdAt;


    /**
     * body attributes
     */

    private Double amount;

    private String currency;

    private String memo;

    private String[] tags;


    public CDBEntry() {

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

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
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
