package de.pfann.budgetmanager.server.model;

import javax.jws.soap.SOAPBinding;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class User {

    private String username;

    private String password;

    private boolean activated;

    private List<String> emails;

    private Date createdAt;

    private Statistic statistic;

    private List<Konto> kontos;

    private List<Konto> foreignKontos;


    public User() {
        kontos = new LinkedList<>();
        foreignKontos = new LinkedList<>();
        emails = new LinkedList<>();
    }

    public User(String aUsername, String aPassword, boolean aIsActivated, List<String> aEmails, Date aCreatedAt, Statistic aStatistics, List<Konto> aKontos, List<Konto> aForeignKonto) {
        username = aUsername;
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

    public String getUsername() {
        return username;
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

    public List<Konto> getKontos() {
        return kontos;
    }

    public List<Konto> getForeignKontos() {
        return foreignKontos;
    }
}
