package de.pfann.budgetmanager.server.persistenscouchdb.model;

import de.pfann.budgetmanager.server.persistenscouchdb.core.AbstractDocument;

import java.time.LocalDateTime;
import java.util.List;

public class CDBStandigOrderEntry extends AbstractDocument {

    private String hash;

    private String username;

    private String konto;

    private LocalDateTime created_at;

    private LocalDateTime last_executed;


    private String memo;

    private String amount;

    private List<CDBTag> tags;
}
