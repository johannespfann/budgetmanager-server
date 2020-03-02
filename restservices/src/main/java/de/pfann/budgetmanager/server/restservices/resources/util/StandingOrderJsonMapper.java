package de.pfann.budgetmanager.server.restservices.resources.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.pfann.budgetmanager.server.model.StandingOrder;
import de.pfann.budgetmanager.server.model.V2StandingOrder;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class StandingOrderJsonMapper {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static String convertToJson(StandingOrder aRotatinEntry){
        try {
            return objectMapper.writeValueAsString(aRotatinEntry);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "{}";
    }

    public static String convertToJson(List<StandingOrder> aRotationEntries){
        try {
            return objectMapper.writeValueAsString(aRotationEntries);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "[]";
    }

    public static StandingOrder convertToEntry(String aValue){
        try {
            return objectMapper.readValue(aValue,StandingOrder.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<StandingOrder> convertToEntries(String aValue){
        try {
            return objectMapper.readValue(aValue, new TypeReference<List<StandingOrder>>(){} );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new LinkedList<StandingOrder>();
    }

    public static List<V2StandingOrder> convertToV2Entries(String aRotationEntries) {
        try {
            return objectMapper.readValue(aRotationEntries, new TypeReference<List<V2StandingOrder>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new LinkedList<V2StandingOrder>();
    }
}
