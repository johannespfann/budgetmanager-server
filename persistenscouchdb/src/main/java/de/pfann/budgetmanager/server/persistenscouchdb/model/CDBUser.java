package de.pfann.budgetmanager.server.persistenscouchdb.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.pfann.budgetmanager.server.model.AbstractDocument;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CDBUser extends AbstractDocument {

    private String username;

    private String password;

    private boolean activated;

    private List<String> emails;

    private String encryptiontext;

    private Date createdAt;



    private List<CDBKonto> kontos;

    private List<CDBTagStatistic> tagStatistics;


    public CDBUser(){
        kontos = new ArrayList<>();
        emails = new ArrayList<>();
        tagStatistics = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isActivated() {
        return activated;
    }

    public void activate() {
        this.activated = true;
    }

    public void deactivate(){
        this.activated = false;
    }

    public List<String> getEmails() {
        return emails;
    }

    public void addEmail(String email) {
        emails.add(email);
    }

    @JsonIgnore
    public boolean isUSerEncrypted() {
        if(encryptiontext != null && !encryptiontext.isEmpty()){
            return true;
        }
        return false;
    }

    public String getEncryptiontext() {
        return encryptiontext;
    }

    public void setEncryptionText(String encryptiontext) {
        this.encryptiontext = encryptiontext;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public List<CDBKonto> getKontos() {
        return kontos;
    }

    public void addKonto(CDBKonto aKonto) {
        kontos.add(aKonto);
    }

    public void setTagStatistics(List<CDBTagStatistic> tagStatistics) {
        this.tagStatistics = tagStatistics;
    }

    public List<CDBTagStatistic> getTagStatistics() {
        return tagStatistics;
    }

    public void addTagStatistics(CDBTagStatistic tagStatistic) {
        tagStatistics.add(tagStatistic);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
