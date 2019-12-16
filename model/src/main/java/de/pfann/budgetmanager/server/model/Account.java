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

    private List<TagRule> tagrules;

    /**
     * constructors
     */

    public Account() {
        activated = true;
        members = new LinkedList<>();
        tagrules = new LinkedList<>();
        entryAccount = ENTRY_KONTO;
        standingOrderAccount = ORDER_KONTO;
    }

    public Account(String aHash, String aName, String aEntryKnto, String aOrderKonto, boolean aIsActivated, String aEncryptionText, String aOwner, List<String> aForeignUser, List<TagRule> aTagRules) {
        hash = aHash;
        name = aName;
        entryAccount = aEntryKnto;
        standingOrderAccount = aOrderKonto;
        encryptionText = aEncryptionText;
        owner = aOwner;
        members = aForeignUser;
        tagrules = aTagRules;
        activated = aIsActivated;
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

    public List<TagRule> getTagrules() {
        return tagrules;
    }


    public static AccountBuilder create() {
        return new AccountBuilder();
    }

    public static AccountBuilder copyAccount(Account aAccount) {
        return new AccountBuilder(aAccount);
    }


    @Override
    public String toString() {
        return "Account{" +
                "hash='" + hash + '\'' +
                ", owner='" + owner + '\'' +
                ", name='" + name + '\'' +
                ", entryAccount='" + entryAccount + '\'' +
                ", standingOrderAccount='" + standingOrderAccount + '\'' +
                ", activated=" + activated +
                ", encryptionText='" + encryptionText + '\'' +
                ", members=" + members +
                ", tagrules=" + tagrules +
                '}';
    }

    public static class AccountBuilder {


        private String hash;

        private String owner;

        private String name;

        private String entryAccount;

        private String standingOrderAccount;

        private boolean activated;

        private String encryptionText;

        private List<String> members;

        private List<TagRule> tagrules;


        public AccountBuilder() {
            members = new LinkedList<>();
            tagrules = new LinkedList<>();
        }


        public AccountBuilder(Account aAccount) {
            hash = aAccount.hash;
            owner = aAccount.owner;
            name = aAccount.name;
            entryAccount = aAccount.entryAccount;
            standingOrderAccount = aAccount.standingOrderAccount;
            activated = aAccount.isActivated();
            encryptionText = aAccount.encryptionText;
            members = new LinkedList<>(aAccount.members);
            tagrules = new LinkedList<>(aAccount.tagrules);
        }


        public AccountBuilder withHash(String aValue) {
            hash = aValue;
            return this;
        }

        public AccountBuilder withOwner(String aValue) {
            owner = aValue;
            return this;
        }

        public AccountBuilder withName(String aValue) {
            name = aValue;
            return this;
        }

        public AccountBuilder withEntryAccount(String aValue) {
            entryAccount = aValue;
            return this;
        }

        public AccountBuilder withStandingOrderAccount(String aValue) {
            standingOrderAccount = aValue;
            return this;
        }

        public AccountBuilder withActivated(boolean aValue) {
            activated = aValue;
            return this;
        }

        public AccountBuilder withMemebers(List<String> aValues) {
            members = new LinkedList<>(aValues);
            return this;
        }

        public AccountBuilder withTagRules(List<TagRule> aValues) {
            tagrules = new LinkedList<>(aValues);
            return this;
        }

        public Account build() {
            return new Account(hash,
                    name,
                    entryAccount,
                    standingOrderAccount,
                    activated,
                    encryptionText,
                    owner,
                    members,
                    tagrules);
        }

    }

}
