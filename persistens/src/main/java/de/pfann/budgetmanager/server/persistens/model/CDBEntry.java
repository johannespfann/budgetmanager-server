package de.pfann.budgetmanager.server.persistens.model;

import java.util.Date;
import java.util.List;

public class CDBEntry extends AbstractDocument {


    /**
     * header attribures
     */


    private String hash;

    private String username;

    private Date created_At;


    /**
     * body attributes
     */

    private double amount;

    private String currency;

    private String memo;

    private List<String> tags;


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

    public Date getCreated_At() {
        return created_At;
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

    /**
     * setter
     */

    public void setHash(String hash) {
        this.hash = hash;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setCreated_At(Date created_At) {
        this.created_At = created_At;
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

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
