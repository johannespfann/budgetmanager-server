package de.pfann.budgetmanager.server.restservices.resources.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.pfann.budgetmanager.server.common.model.RotationEntry;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class RotationEntryJsonMapper {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static String convertToJson(RotationEntry aRotatinEntry){
        try {
            return objectMapper.writeValueAsString(aRotatinEntry);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "{}";
    }

    public static String convertToJson(List<RotationEntry> aRotationEntries){
        try {
            return objectMapper.writeValueAsString(aRotationEntries);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "[]";
    }

    public static RotationEntry convertToEntry(String aValue){
        try {
            return objectMapper.readValue(aValue,RotationEntry.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<RotationEntry> convertToEntries(String aValue){
        try {
            return objectMapper.readValue(aValue, new TypeReference<List<RotationEntry>>(){} );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new LinkedList<RotationEntry>();
    }

}
