package de.pfann.budgetmanager.server.model;

import java.util.LinkedList;
import java.util.List;

public class TagRule {

    private String whenTag;

    private List<String> thenTags;

    public TagRule() {
        thenTags = new LinkedList<>();
    }

    public TagRule(String whenTag, List<String> thenTags) {
        this.whenTag = whenTag;
        this.thenTags = new LinkedList<>();
    }

    public String getWhenTag() {
        return whenTag;
    }

    public List<String> getThenTags() {
        return thenTags;
    }
}
