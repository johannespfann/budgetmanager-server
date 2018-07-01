package de.pfann.budgetmanager.server.persistenscouchdb.model;

import java.util.ArrayList;
import java.util.List;

public class CDBKonto {

    private String hash;

    private String name;

    private String owner;

    private List<String> foreignUser;


    public CDBKonto(){
        foreignUser = new ArrayList<>();
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public List<String> getForeignUser() {
        return foreignUser;
    }

    public void addForeignUser(String aUser){
        foreignUser.add(aUser);
    }
}
