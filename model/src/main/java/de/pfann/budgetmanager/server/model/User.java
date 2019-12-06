package de.pfann.budgetmanager.server.model;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class User extends AbstractDocument {

    private String name;

    private String password;

    private boolean activated;

    private List<String> emails;

    private Date createdAt;

    private List<Account> kontos;

    private List<Account> foreignKontos;

    private User(String aId, String aRev) {
        id = aId;
        rev = aRev;
        kontos = new LinkedList<>();
        foreignKontos = new LinkedList<>();
        emails = new LinkedList<>();
    }

    private User(String aId, String aRev, String aUsername, String aPassword, boolean aIsActivated, List<String> aEmails, Date aCreatedAt, List<Account> aKontos, List<Account> aForeignKonto) {
        id = aId;
        rev = aRev;
        name = aUsername;
        password = aPassword;
        activated = aIsActivated;
        emails = aEmails;
        createdAt = aCreatedAt;
        kontos = aKontos;
        foreignKontos = aForeignKonto;
    }

    /**
     * getter
     */

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public boolean isActivated() {
        return activated;
    }

    public List<String> getEmails() {
        return emails;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public List<Account> getKontos() {
        return kontos;
    }

    public List<Account> getForeignKontos() {
        return foreignKontos;
    }

    public static UserBuilder create(String aId) {
        return new UserBuilder(aId);
    }

    public static UserBuilder createWithCopy(User aUser) {
        return new UserBuilder(aUser);
    }

    /**
     * helper
     */

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", activated=" + activated +
                ", emails=" + emails +
                ", createdAt=" + createdAt +
                ", kontos=" + kontos +
                ", foreignKontos=" + foreignKontos +
                '}';
    }

    private static class UserBuilder {

        private String id;

        private String rev;

        private String name;

        private String password;

        private boolean activated;

        private List<String> emails;

        private Date createdAt;

        private List<Account> kontos;

        private List<Account> foreignKontos;

        public UserBuilder(String aId) {
            id = aId;
            emails = new LinkedList<>();
            kontos = new LinkedList<>();
            foreignKontos = new LinkedList<>();
        }

        public UserBuilder(User aUser) {
            id = aUser.id;
            rev = aUser.rev;
            name = aUser.name;
            password = aUser.password;
            activated = aUser.activated;
            emails = aUser.emails;
            createdAt = aUser.createdAt;
            kontos = new LinkedList<>(aUser.kontos);
            foreignKontos = new LinkedList<>(aUser.foreignKontos);
        }

        public UserBuilder withName(String value) {
            name = value;
            return this;
        }

        public UserBuilder withPassword(String value) {
            password = value;
            return this;
        }

        public UserBuilder withActivated(boolean value) {
            activated = value;
            return this;
        }

        public UserBuilder withEmails(List<String> value) {
            emails = new LinkedList<>(value);
            return this;
        }

        public UserBuilder withCreatedAt(Date value) {
            createdAt = value;
            return this;
        }

        public UserBuilder withKontos(List<Account> value) {
            kontos = new LinkedList<>(value);
            return this;
        }

        public UserBuilder withForeignKontos(List<Account> value) {
            foreignKontos = new LinkedList<>(value);
            return this;
        }

        public User build() {
            return new User(id, rev, name, password, activated, emails, createdAt, kontos, foreignKontos);
        }
    }
}
