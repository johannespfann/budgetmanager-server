package de.pfann.budgetmanager.server.model;

import java.util.Date;

public class Entry extends AbstractDocument  {

    /**
     * header attribures
     */

    private String hash;

    private String username;

    private Date createdAt;

    /**
     * data attributes
     */

    private String data;


    /**
     * constructor
     */

    public Entry() {
        // default
    }

    public Entry(String aHash, String aUsername, Date aCreatedAd, String aData) {
        hash = aHash;
        username = aUsername;
        createdAt = aCreatedAd;
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public String getData() {
        return data;
    }


    @Override
    public String toString() {
        return "Entry{" +
                "hash='" + hash + '\'' +
                ", username='" + username + '\'' +
                ", createdAt=" + createdAt +
                ", data='" + data + '\'' +
                '}';
    }
}
