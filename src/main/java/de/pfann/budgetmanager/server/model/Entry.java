package de.pfann.budgetmanager.server.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
public class Entry  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String hash;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(nullable = false)
    private AppUser appUser;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Category category;

    @Column(nullable = false)
    private double amount;

    private String memo;

    public Entry(){

        // hibernate
    }

    public Entry(Entry aEntry){
        Entry entry = new Entry();
        entry.id = aEntry.id;
        entry.amount = aEntry.amount;
        entry.appUser = aEntry.appUser;
        entry.category = aEntry.category;
        entry.hash = aEntry.hash;
        entry.memo = aEntry.memo;
    }

    // getter

    public String getHash() {
        return hash;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public Category getCategory() {
        return category;
    }

    public double getAmount() {
        return amount;
    }

    public String getMemo() {
        return memo;
    }

    // setter

    public void setId(long id) {
        this.id = id;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
