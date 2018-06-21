package de.pfann.budgetmanager.server.persistenscouchdb.model;

import de.pfann.budgetmanager.server.persistenscouchdb.core.AbstractDocument;

import java.time.LocalDateTime;
import java.util.List;

public class CDBStandigOrderEntry extends AbstractDocument {

    /**
     * standingorderinfos
     */


    private String hash;

    private String username;

    private String konto;

    private LocalDateTime start_at;

    private LocalDateTime end_at;

    private LocalDateTime last_executed;

    private String rotation_strategy;


    /**
     * entryinfos
     */

    private String memo;

    private String amount;

    private List<CDBTag> tags;

    public CDBStandigOrderEntry(){
        // default
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getKonto() {
        return konto;
    }

    public void setKonto(String konto) {
        this.konto = konto;
    }

    public LocalDateTime getStart_at() {
        return start_at;
    }

    public void setStart_at(LocalDateTime start_at) {
        this.start_at = start_at;
    }

    public LocalDateTime getEnd_at() {
        return end_at;
    }

    public void setEnd_at(LocalDateTime end_at) {
        this.end_at = end_at;
    }

    public LocalDateTime getLast_executed() {
        return last_executed;
    }

    public void setLast_executed(LocalDateTime last_executed) {
        this.last_executed = last_executed;
    }

    public String getRotation_strategy() {
        return rotation_strategy;
    }

    public void setRotation_strategy(String rotation_strategy) {
        this.rotation_strategy = rotation_strategy;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public List<CDBTag> getTags() {
        return tags;
    }

    public void setTags(List<CDBTag> tags) {
        this.tags = tags;
    }


}
