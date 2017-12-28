package de.pfann.budgetmanager.server.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(mappedBy = "appUser")
    private List<Tag> tags;

    @OneToMany(mappedBy = "appUser")
    private List<Category> categories;

    @OneToMany(mappedBy = "appUser")
    private List<Entry> entries;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    public long getId() {
        return id;
    }


}
