package de.pfann.budgetmanager.server;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("home")
public class Resource {

    private String ACCESS_CONTROL_ALLOW_ORIGIN_PROPERTY = "Access-Control-Allow-Origin";
    private String ACCESS_CONTROL_ALLOW_ORIGIN_VALUE = "*";

    private String ACCESS_CONTROL_ALLOW_METHODS_PROPERTY = "Access-Control-Allow-Methods";
    private String ACCESS_CONTROL_ALLOW_METHODS_VALUE = "GET, POST, DELETE, PUT";

    @GET
    @Path("hello")
    @Produces(MediaType.APPLICATION_JSON)
    public Response helloWorld() {
        return Response.ok()
                .entity("{\"name\" : \"hello\"}")
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", ACCESS_CONTROL_ALLOW_METHODS_VALUE)
                .build();
    }
}
