package de.pfann.budgetmanager.server.persistenscouchdb.model;

import java.util.List;

public class CDBTagStatistic {

    private String name;

    private String amount;

    private List<CDBTagStatistic> neighbors;
}
