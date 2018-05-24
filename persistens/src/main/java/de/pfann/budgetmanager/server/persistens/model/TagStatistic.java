package de.pfann.budgetmanager.server.persistens.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

public class TagStatistic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private long id;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = false, length = 255)
    private String weight;

    @ManyToOne
    @Column(nullable = false)
    @JsonIgnore
    public AppUser user;

    public TagStatistic() {
        // default
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }
}
