package de.pfann.budgetmanager.server.persistens.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AbstractDocument {

    @JsonProperty("_id")
    protected String id;

    @JsonProperty("_rev")
    protected String rev;

    public AbstractDocument() {

    }

    public String getId() {
        return id;
    }

    public String getRev() {
        return rev;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setRev(String rev) {
        this.rev = rev;
    }

}
