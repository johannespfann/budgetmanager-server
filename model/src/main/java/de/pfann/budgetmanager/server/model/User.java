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

    private Statistic statistic;

    private List<Account> kontos;

    private List<Account> foreignKontos;


    public User() {
        kontos = new LinkedList<>();
        foreignKontos = new LinkedList<>();
        emails = new LinkedList<>();
    }

    public User(String aUsername, String aPassword, boolean aIsActivated, List<String> aEmails, Date aCreatedAt, Statistic aStatistics, List<Account> aKontos, List<Account> aForeignKonto) {
        name = aUsername;
        password = aPassword;
        activated = aIsActivated;
        emails = aEmails;
        createdAt = aCreatedAt;
        statistic = aStatistics;
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

    public Statistic getStatistic() {
        return statistic;
    }

    public List<Account> getKontos() {
        return kontos;
    }

    public List<Account> getForeignKontos() {
        return foreignKontos;
    }

    /**
     * setter
     */

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void activate() {
        this.activated = true;
    }

    public void deactivate() {
        this.activated = false;
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setStatistic(Statistic statistic) {
        this.statistic = statistic;
    }

    public void setKontos(List<Account> kontos) {
        this.kontos = kontos;
    }

    public void setForeignKontos(List<Account> foreignKontos) {
        this.foreignKontos = foreignKontos;
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
                ", statistic=" + statistic +
                ", kontos=" + kontos +
                ", foreignKontos=" + foreignKontos +
                '}';
    }
}
