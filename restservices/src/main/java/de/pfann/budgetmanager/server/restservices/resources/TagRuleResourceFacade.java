package de.pfann.budgetmanager.server.restservices.resources;

import de.pfann.budgetmanager.server.common.facade.TagRuleFacade;
import de.pfann.budgetmanager.server.model.TagRule;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class  TagRuleResourceFacade {

    private TagRuleFacade tagRuleFacade;

    public TagRuleResourceFacade(TagRuleFacade aTagRuleFacade) {
        tagRuleFacade = aTagRuleFacade;
    }

    public List<TagRule> getAllTagRules(String aOwner, String aAccountHash) {
        return tagRuleFacade.getTagRules(aOwner, aAccountHash);
    }

    public void saveTagRule(String aOwner, String aAccountHash, TagRule aTagRule) {

        // format tags
        String formattedWhenTag = formattTag(aTagRule.getWhenTag());
        List<String> formattedThenTags = aTagRule.getThenTags()
                .stream()
                .map( thenTag -> formattTag(thenTag) )
                .collect(Collectors.toList());

        // check for valid letters
        checkNumbersAndLetters(formattedWhenTag);
        formattedThenTags.stream().forEach( thenTag -> checkNumbersAndLetters(thenTag));

        // check for valid size
        checkTagLenghtLimit(formattedWhenTag);
        formattedThenTags.stream().forEach(( thenTag -> checkTagLenghtLimit(thenTag)));

        // check logic
        checkThenTagInWhenTags(formattedWhenTag, formattedThenTags);
        List<String> filteredWhenTags = filterDublicates(formattedThenTags);

        TagRule preparedTagRule = new TagRule(formattedWhenTag, filteredWhenTags);
        tagRuleFacade.addTagRule(aOwner, aAccountHash, preparedTagRule);
    }

    private List<String> filterDublicates(List<String> formattedThenTags) {
        return new LinkedList<String>(new HashSet<>(formattedThenTags));
    }

    private void checkThenTagInWhenTags(String formattedWhenTag, List<String> formattedThenTags) {
        if(formattedThenTags.stream().anyMatch( thenTag -> thenTag.equals(formattedWhenTag))){
            throw new IllegalArgumentException("in thentags (" + formattedThenTags + ") was a whentag ("+ formattedWhenTag +") found! ");
        }
    }

    public void deleteTagRule(String aOwner, String aAccountHash, String aThenTagName) {
        tagRuleFacade.deleteTagRule(aOwner, aAccountHash, aThenTagName);
    }

    private void checkNumbersAndLetters(String aValue) {
        if(!aValue.matches("[0123456789a-zA-ZäÄüÜöÖ]+")){
            throw new IllegalArgumentException("only letters and numbers allowed : " + aValue);
        };
    }

    private void checkTagLenghtLimit(String aValue) {
        if(aValue.length() > 50){
            throw new IllegalArgumentException("tag must be under 50 letters: " + aValue);
        };
    }

    private String formattTag(String aValue) {
        return aValue.trim().toLowerCase();
    }

}
