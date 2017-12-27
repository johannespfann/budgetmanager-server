package de.pfann.budgetmanager.server.model;

import javax.persistence.*;

@Entity
public class Entry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String hash;

    private User user;

    private Category category;

    private double amount;

    private String memo;

    public Entry(){

    }

    public Entry(Entry aEntry){
        Entry entry = new Entry();
        entry.id = aEntry.id;
        entry.amount = aEntry.amount;
        entry.user = aEntry.user;
        entry.category = aEntry.category;
        entry.hash = aEntry.hash;
        entry.memo = aEntry.memo;
    }

    // getter


    public String getHash() {
        return hash;
    }

    public User getUser() {
        return user;
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

    public void setUser(User user) {
        this.user = user;
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
