package de.pfann.budgetmanager.server.restservices.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("hello")
public class HelloResource {

    @GET
    public void sayHello(){
        System.out.println("HelloWorld");
    }

}
