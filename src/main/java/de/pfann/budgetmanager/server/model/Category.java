package de.pfann.budgetmanager.server.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Category {

    @Id
    private String hash;

    private User user;

    private String name;


}
