package de.pfann.budgetmanager.server.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class Tag implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    @JsonIgnore
    private AppUser appUser;

    @ManyToMany(mappedBy="tags")
    private List<Entry> entries;

    @Column(nullable = false)
    private String name;

    public Tag(){
        // default
    }

    public Tag(String aName){
        name = aName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addEntry(Entry aEntry){
        entries.add(aEntry);
    }

    public void removeEntry(Entry aEntry){
        entries.remove(aEntry);
    }
}

