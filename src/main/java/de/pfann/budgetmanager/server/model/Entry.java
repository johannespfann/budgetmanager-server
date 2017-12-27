package de.pfann.budgetmanager.server.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Entry {

    @Id
    private String hash;

    private User user;

    private Category category;

    private double amount;

    private String memo;
}
