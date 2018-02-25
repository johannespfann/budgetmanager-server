package de.pfann.budgetmanager.server.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

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

    @ManyToMany
    @JoinTable(
            name="entry_tag",
            joinColumns=@JoinColumn(name="ENTRY_ID", referencedColumnName="id"),
            inverseJoinColumns=@JoinColumn(name="TAG_ID", referencedColumnName="id"))
    private List<Tag> tags = new ArrayList<>();

    @ManyToOne
    @JoinColumn(nullable = false)
    private Category category;

    @Column(nullable = false)
    private double amount;

    private Date created_at;

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
        entry.created_at = aEntry.created_at;
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

    public Date getCreated_at() {
        return created_at;
    }

    // setter

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

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

    public void addTag(Tag aTag){
        tags.add(aTag);
    }

    public void remove(Tag aTag){
        tags.remove(aTag);
    }
}
