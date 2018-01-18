package de.pfann.budgetmanager.server.resources;

import javax.ws.rs.HeaderParam;
import javax.ws.rs.core.Response;

public class RestUtil {

    public static Response.ResponseBuilder prepareDefaultHeader(Response.ResponseBuilder aBuilder){
        return aBuilder
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
    }
}
