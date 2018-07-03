package de.pfann.budgetmanager.server.persistenscouchdb.model;

import java.util.List;

public class CDBTagStatistic {

    private String name;

    private String weight;

    private List<CDBTagStatistic> neighbors;

    public CDBTagStatistic(){
        // default
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public List<CDBTagStatistic> getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(List<CDBTagStatistic> neighbors) {
        this.neighbors = neighbors;
    }
}
