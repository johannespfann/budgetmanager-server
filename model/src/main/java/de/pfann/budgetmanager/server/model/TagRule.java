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
        this.thenTags = thenTags;
    }

    public String getWhenTag() {
        return whenTag;
    }

    public List<String> getThenTags() {
        return thenTags;
    }

    public static TagRuleBuilder createAndCopy(TagRule aTagRule) {
        return new TagRuleBuilder(aTagRule);
    }

    public static TagRuleBuilder create() {
        return new TagRuleBuilder();
    }

    @Override
    public String toString() {
        return "TagRule{" +
                "whenTag='" + whenTag + '\'' +
                ", thenTags=" + thenTags +
                '}';
    }

    public static class TagRuleBuilder {

        private String whenTag;

        private List<String> thenTags;

        public TagRuleBuilder() {
            thenTags = new LinkedList<>();
        }

        public TagRuleBuilder(TagRule aTagRule) {
            whenTag = aTagRule.whenTag;
            thenTags = aTagRule.thenTags;
        }

        public TagRuleBuilder withWhenTag(String aWhenTag) {
            whenTag = aWhenTag;
            return this;
        }

        public TagRuleBuilder withThenTag(String aThenTag) {
            thenTags.add(aThenTag);
            return this;
        }

        public TagRule build() {
            return new TagRule(whenTag,thenTags);
        }
    }
}
