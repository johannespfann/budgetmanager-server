package de.pfann.budgetmanager.server.model;

public class StandingOrder extends AbstractDocument {

    /**
     * standingorder header
     */

    private String hash;

    private String username;

    /**
     * standingorder data
     */

    private String data;

    /**
     * constructor
     */

    private StandingOrder(String aHash, String aUsername, String aData) {
        hash = aHash;
        username = aUsername;
        data = aData;
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

    public String getData() {
        return data;
    }
}
