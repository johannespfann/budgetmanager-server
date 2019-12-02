package de.pfann.budgetmanager.server.restservices.resources.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.pfann.budgetmanager.server.model.Entry;
import de.pfann.budgetmanager.server.model.TagRule;

import java.io.IOException;
import java.util.List;

public class TagRuleJsonMapper {

    private static ObjectMapper objectMapper = new ObjectMapper();


    public static String convertToJson(List<TagRule> aTagRules) {
        try {
            return objectMapper.writeValueAsString(aTagRules);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "[]";
    }

    public static TagRule convertToTagRule(String aTagRule) {
        try {
            return objectMapper.readValue(aTagRule, TagRule.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
