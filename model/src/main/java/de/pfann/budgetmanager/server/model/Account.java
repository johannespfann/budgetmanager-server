package de.pfann.budgetmanager.server.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.LinkedList;
import java.util.List;

public class Account {

    public static final String ENTRY_KONTO = "entries";
    public static final String ORDER_KONTO = "standingorders";

    private String hash;

    private String name;

    @JsonProperty("entry_account")
    private String entryAccount;

    @JsonProperty("standingorder_account")
    private String standingOrderAccount;

    @JsonProperty("is_encrypted")
    private boolean isEncrypted;

    @JsonProperty("is_activated")
    private boolean isActivated;

    @JsonProperty("encryption_text")
    private String encryptionText;

    private String owner;

    private List<String> members;

    /**
     * constructors
     */

    public Account() {
        isActivated = true;
        members = new LinkedList<>();
    }

    public Account(String aHash, String aName, String aEntryKnto, String aOrderKonto, boolean aIsEncrypted, String aEncryptionText, String aOwner, List<String> aForeignUser) {
        hash = aHash;
        name = aName;
        entryAccount = aEntryKnto;
        standingOrderAccount = aOrderKonto;
        isEncrypted = aIsEncrypted;
        encryptionText = aEncryptionText;
        owner = aOwner;
        members = aForeignUser;
    }



    /**
     * getter
     */

    public boolean isActivated() {
        return isActivated;
    }

    public String getHash() {
        return hash;
    }

    public String getName() {
        return name;
    }

    public String getEntryAccount() {
        return entryAccount;
    }

    public String getStandingOrderAccount() {
        return standingOrderAccount;
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

    public List<String> getMembers() {
        return members;
    }


    public void activate() {
        isActivated = true;
    }

    public void deactivate() {
        isActivated = false;
    }
}
