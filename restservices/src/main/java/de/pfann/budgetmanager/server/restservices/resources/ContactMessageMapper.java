package de.pfann.budgetmanager.server.restservices.resources;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class ContactMessageMapper {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static ContactMessage convertToContectMessage(String aValue) {
        try {
            return objectMapper.readValue(aValue, ContactMessage.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
