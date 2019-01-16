package de.pfann.budgetmanager.server.persistenscouchdb.model;

import de.pfann.budgetmanager.server.model.AbstractDocument;

import java.util.Date;
import java.util.List;

public class CDBEntry extends AbstractDocument{

    /**
     * header attribures
     */

    private String hash;

    private String username;

    private Date created_at;


    /**
     * data attributes
     */

    private String currency;

    private String memo;

    private String amount;

    private List<CDBTag> tags;

    private String data;


    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public List<CDBTag> getTags() {
        return tags;
    }

    public void setTags(List<CDBTag> tags) {
        this.tags = tags;
    }
}
