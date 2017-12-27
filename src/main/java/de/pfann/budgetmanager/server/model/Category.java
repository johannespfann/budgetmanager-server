package de.pfann.budgetmanager.server.model;

import javax.persistence.*;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String hash;

    private User user;

    private String name;


}
