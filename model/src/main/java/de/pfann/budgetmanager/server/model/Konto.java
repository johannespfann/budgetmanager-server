package de.pfann.budgetmanager.server.model;

import java.util.LinkedList;
import java.util.List;

public class Konto {

    public static final String ENTRY_KONTO = "entries";
    public static final String ORDER_KONTO = "standingorders";

    private String hash;

    private String name;

    private String entryKonto;

    private String orderKonto;

    private boolean isEncrypted;

    private String encryptionText;

    private String owner;

    private List<String> foreignUser;

    /**
     * constructors
     */

    public Konto() {
        foreignUser = new LinkedList<>();
    }

    public Konto(String aHash, String aName, String aEntryKnto, String aOrderKonto, boolean aIsEncrypted, String aEncryptionText, String aOwner, List<String> aForeignUser) {
        hash = aHash;
        name = aName;
        entryKonto = aEntryKnto;
        orderKonto = aOrderKonto;
        isEncrypted = aIsEncrypted;
        encryptionText = aEncryptionText;
        owner = aOwner;
        foreignUser = aForeignUser;
    }

    /**
     * getter
     */

    public String getHash() {
        return hash;
    }

    public String getName() {
        return name;
    }

    public String getEntryKonto() {
        return entryKonto;
    }

    public String getOrderKonto() {
        return orderKonto;
    }

    public boolean isEncrypted() {
        return isEncrypted;
    }

    public String getEncryptionText() {
        return encryptionText;
    }

    public String getOwner() {
        return owner;
    }

    public List<String> getForeignUser() {
        return foreignUser;
    }
}
