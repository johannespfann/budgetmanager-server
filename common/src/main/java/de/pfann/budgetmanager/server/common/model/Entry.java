package de.pfann.budgetmanager.server.common.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Entry  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String hash;

    @ManyToOne
    @JoinColumn(nullable = false)
    @JsonIgnore
    private AppUser appUser;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name="entry_tag",
            joinColumns=@JoinColumn(name="ENTRY_ID", referencedColumnName="id"),
            inverseJoinColumns=@JoinColumn(name="TAG_ID", referencedColumnName="id"))
    private List<Tag> tags;

    @Column(nullable = false)
    @Type(type="text")
    private String amount;

    private Date created_at;

    @Type(type="text")
    private String memo;

    public Entry(){
        tags = new ArrayList<>();
    }

    public Entry(Entry aEntry){
        Entry entry = new Entry();
        entry.id = aEntry.id;
        entry.amount = aEntry.amount;
        entry.appUser = aEntry.appUser;
        entry.hash = aEntry.hash;
        entry.memo = aEntry.memo;
        entry.created_at = aEntry.created_at;
        entry.tags = aEntry.tags;
    }

    public long getId() {
        return id;
    }

// getter

    public List<Tag> getTags() {
        return tags;
    }

    public String getHash() {
        return hash;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public String getAmount() {
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

    public void setAmount(String amount) {
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

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "Entry{" +
                "hash='" + hash + '\'' +
                ", tags=" + tags +
                ", amount='" + amount + '\'' +
                ", created_at=" + created_at +
                ", memo='" + memo + '\'' +
                '}';
    }
}
