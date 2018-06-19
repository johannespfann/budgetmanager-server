package de.pfann.budgetmanager.server.persistenscouchdb.core;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import org.ektorp.CouchDbConnector;
import org.ektorp.impl.ObjectMapperFactory;
import org.ektorp.impl.jackson.EktorpJacksonModule;

public class BMObjectMapperFactory implements ObjectMapperFactory {

    private ObjectMapper instance;

    @Override
    public ObjectMapper createObjectMapper() {
        ObjectMapper result = instance;
        if (result == null) {
            result = new ObjectMapper();
            applyDefaultConfiguration(result);
            result.registerModule(new JSR310Module());
            instance = result;
        }
        return result;
    }

    @Override
    public ObjectMapper createObjectMapper(CouchDbConnector couchDbConnector) {
        ObjectMapper objectMapper = new ObjectMapper();
        applyDefaultConfiguration(objectMapper);
        objectMapper.registerModule(new JSR310Module());
        objectMapper.registerModule(new EktorpJacksonModule(couchDbConnector, objectMapper));
        return objectMapper;
    }

    private void applyDefaultConfiguration(ObjectMapper om) {
        om.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        om.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    private boolean writeDatesAsTimestamps = false;

    public void setWriteDatesAsTimestamps(boolean b) {
        this.writeDatesAsTimestamps = b;
    }

}
