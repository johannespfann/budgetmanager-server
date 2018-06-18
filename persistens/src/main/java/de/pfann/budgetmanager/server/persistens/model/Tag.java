package de.pfann.budgetmanager.server.persistens.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
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
    @JsonIgnore
    private List<Entry> entries;

    @Column(nullable = false)
    private String name;

    private int count;

    public void increaseCount(){
        count++;
    }

    public void descreaseCount(){
        if(count > 0) {
            count--;
        }
    }

    public Tag(){
        entries = new ArrayList<Entry>();
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Entry> getEntries() {
        return entries;
    }

    public void setEntries(List<Entry> entries) {
        this.entries = entries;
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
    }

    public void removeEntry(Entry aEntry){
        entries.remove(aEntry);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;

        if (this == obj)
            return true;

        if (getClass() != obj.getClass())
            return false;

        Tag other = (Tag) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }
}

