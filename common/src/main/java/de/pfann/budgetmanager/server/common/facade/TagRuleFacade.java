package de.pfann.budgetmanager.server.common.facade;

import de.pfann.budgetmanager.server.model.TagRule;

import java.util.List;

public interface TagRuleFacade {

    List<TagRule> getTagRules(String aOwner, String aAccountHash);

    void addTagRule(String aOwner, String aAccountHash, TagRule aTagRule);

    void deleteTagRule(String aOwner, String aAccountHash, String aThenTagName);


}
