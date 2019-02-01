package de.pfann.budgetmanager.server.model;

import java.util.LinkedList;
import java.util.List;

public class Account {

    public static final String ENTRY_KONTO = "entries";
    public static final String ORDER_KONTO = "standingorders";

    private String hash;

    private String owner;

    private String name;

    private String entryAccount;

    private String standingOrderAccount;

    private boolean activated;

    private String encryptionText;

    private List<String> members;

    private String statistics;

    private String rules;

    /**
     * constructors
     */

    public Account() {
        activated = true;
        members = new LinkedList<>();
        entryAccount = ENTRY_KONTO;
        standingOrderAccount = ORDER_KONTO;
    }

    public Account(String aHash, String aName, String aEntryKnto, String aOrderKonto, boolean aIsEncrypted, String aEncryptionText, String aOwner, List<String> aForeignUser) {
        hash = aHash;
        name = aName;
        entryAccount = aEntryKnto;
        standingOrderAccount = aOrderKonto;
        encryptionText = aEncryptionText;
        owner = aOwner;
        members = aForeignUser;
    }

    /**
     * getter
     */

    public boolean isActivated() {
        return activated;
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
        activated = true;
    }

    public void deactivate() {
        activated = false;
    }

    public String getStatistics() {
        return statistics;
    }

    public String getRules() {
        return rules;
    }
}
