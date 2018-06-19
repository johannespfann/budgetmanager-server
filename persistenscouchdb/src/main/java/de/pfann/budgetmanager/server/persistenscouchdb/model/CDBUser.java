package de.pfann.budgetmanager.server.persistenscouchdb.model;

import de.pfann.budgetmanager.server.persistenscouchdb.core.AbstractDocument;

import java.time.LocalDateTime;
import java.util.List;

public class CDBUser extends AbstractDocument {

    private String hash;

    private String username;

    private boolean activated;

    private List<String> emails;


    private String isEncrypted;

    private String encryptiontext;



    private LocalDateTime createdAt;

    private LocalDateTime lastLogin;


}
