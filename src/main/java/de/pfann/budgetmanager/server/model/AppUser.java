package de.pfann.budgetmanager.server.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private long id;

    @OneToMany(mappedBy = "appUser")
    @JsonIgnore
    private List<Category> categories;

    @OneToMany(mappedBy = "appUser")
    @JsonIgnore
    private List<Entry> entries;

    @OneToMany(mappedBy = "appUser")
    @JsonIgnore
    private List<Tag> tags;

    @OneToOne
    @JsonIgnore
    private Category defaultCategory;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @JsonIgnore
    private String password;

    @JsonIgnore
    private boolean activated;

    public AppUser() {
        tags = new LinkedList<>();
        entries = new LinkedList<>();
        categories = new LinkedList<>();
    }

    public Category getDefaultCategory() {
        return defaultCategory;
    }

    public void setDefaultCategory(Category defaultCategory) {
        this.defaultCategory = defaultCategory;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<Entry> getEntries() {
        return entries;
    }

    public void setEntries(List<Entry> entries) {
        this.entries = entries;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActivated() {
        return activated;
    }

    public void activate() {
        System.out.println("activate user");
        this.activated = true;
    }

    public void deactivate() {
        this.activated = false;
    }
}
