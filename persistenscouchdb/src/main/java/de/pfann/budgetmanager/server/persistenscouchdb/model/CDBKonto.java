package de.pfann.budgetmanager.server.persistenscouchdb.model;

import java.util.ArrayList;
import java.util.List;

public class CDBKonto {

    public static final String ENTRY_KONTO = "entries";
    public static final String ORDER_KONTO = "standingorders";

    private String hash;

    private String name;

    private String entryKonto;

    private String orderKonto;

    private String owner;

    private List<String> foreignUser;

    public CDBKonto(){
        foreignUser = new ArrayList<>();
        entryKonto = ENTRY_KONTO;
        orderKonto = ORDER_KONTO;
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

    public String getEntryKonto() {
        return entryKonto;
    }

    public String getOrderKonto() {
        return orderKonto;
    }

}
