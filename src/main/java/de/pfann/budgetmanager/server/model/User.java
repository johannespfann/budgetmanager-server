package de.pfann.budgetmanager.server.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany
    private List<Tag> tags;

    @OneToMany
    private List<Category> categories;

    @OneToMany
    private List<Entry> entries;

    private String name;

    private String email;

    private String password;

    public long getId() {
        return id;
    }


}
