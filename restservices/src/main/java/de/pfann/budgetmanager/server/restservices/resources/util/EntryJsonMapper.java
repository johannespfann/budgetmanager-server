package de.pfann.budgetmanager.server.restservices.resources.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.pfann.budgetmanager.server.common.model.Entry;
import de.pfann.budgetmanager.server.common.model.TagStatistic;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class EntryJsonMapper {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static String convertToJson(Entry aEntry){
        try {
            return objectMapper.writeValueAsString(aEntry);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "{}";
    }

    public static String convertToJson(List<Entry> aEntries){
        try {
            return objectMapper.writeValueAsString(aEntries);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "[]";
    }

    public static Entry convertToEntry(String aValue){
        try {
            return objectMapper.readValue(aValue,Entry.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Entry> convertToEntries(String aValue){
        try {
            return objectMapper.readValue(aValue, new TypeReference<List<Entry>>(){} );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new LinkedList<Entry>();
    }

}
