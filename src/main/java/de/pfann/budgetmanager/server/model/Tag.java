package de.pfann.budgetmanager.server.model;

import javax.persistence.*;

@Entity
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private AppUser appUser;

    private String name;
}
