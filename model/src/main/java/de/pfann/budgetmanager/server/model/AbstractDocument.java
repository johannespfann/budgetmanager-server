package de.pfann.budgetmanager.server.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AbstractDocument {

    @JsonProperty("_id")
    private String id;

    @JsonProperty("_rev")
    private String rev;

    public AbstractDocument() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRev() {
        return rev;
    }

    public void setRev(String rev) {
        this.rev = rev;
    }
}
