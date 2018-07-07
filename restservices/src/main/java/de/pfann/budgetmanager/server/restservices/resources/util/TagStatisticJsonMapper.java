package de.pfann.budgetmanager.server.restservices.resources.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.pfann.budgetmanager.server.common.model.Entry;
import de.pfann.budgetmanager.server.common.model.Tag;
import de.pfann.budgetmanager.server.common.model.TagStatistic;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class TagStatisticJsonMapper {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static String convertToJson(TagStatistic aTagStatistic){
        try {
            return objectMapper.writeValueAsString(aTagStatistic);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "{}";
    }

    public static String convertToJson(List<TagStatistic> aTagStatistic){
        try {
            return objectMapper.writeValueAsString(aTagStatistic);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "[]";
    }

    public static TagStatistic convertToEntry(String aValue){
        try {
            return objectMapper.readValue(aValue,TagStatistic.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<TagStatistic> convertToEntries(String aValue){
        try {
            return objectMapper.readValue(aValue, List.class );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new LinkedList<TagStatistic>();
    }

}
