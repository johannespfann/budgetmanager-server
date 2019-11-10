package de.pfann.budgetmanager.server.restserviceresources;

import com.google.gson.Gson;
import de.pfann.budgetmanager.server.restservices.resources.core.CrossOriginFilterImpl;
import de.pfann.budgetmanager.server.restservices.resources.core.RequestLoggingFilter;
import de.pfann.budgetmanager.server.restservices.resources.core.ResponseLoggingFilter;
import io.restassured.RestAssured;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.net.URI;
import java.time.LocalDate;
import java.util.Date;


import de.pfann.budgetmanager.server.model.Entry;

public class EntryResourceIT {

    /**
     *  given().auth().basic("username", "password"). ..
     *  RestAssured.authentication = basic("username", "password");
     */

    /**
     * RequestSpecBuilder builder = new RequestSpecBuilder();
     * builder.addParam("parameter1", "parameterValue");
     * builder.addHeader("header1", "headerValue");
     * RequestSpecification requestSpec = builder.build();
     *
     * given().
     *         spec(requestSpec).
     *         param("parameter2", "paramValue").
     * when().
     *         get("/something").
     * then().
     *         body("x.y.z", equalTo("something"));
     */

    /**
     * ResponseSpecBuilder builder = new ResponseSpecBuilder();
     * builder.expectStatusCode(200);
     * builder.expectBody("x.y.size()", is(2));
     * ResponseSpecification responseSpec = builder.build();
     *
     * // Now you can re-use the "responseSpec" in many different tests:
     * when().
     *        get("/something").
     * then().
     *        spec(responseSpec).
     *        body("x.y.z", equalTo("something"));
     */


    /**
     * helper objects
     */

    private String serveradress = "http://localhost:8890/budget";
    private HttpServer server;
    private Gson gson;

    /**
     *
     */



    /**
     * authentication attributes
     */

    private String userJohannes = "johannes-710";
    private String otherUser = "other-user";

    private String validJohannesToken = "";
    private String invalidJohannesToken = "";


    /**
     * entry objects
     */

    private Entry validEntry;
    private String validEntryHash;
    private Date validEntryDate;
    private String validEntryData;
    private String validEntryJSON;


    /**
     * class under test
     */


    @BeforeClass
    public static void setUpTestEnv() {

        RestAssured.port = Integer.valueOf(8890);
        RestAssured.baseURI = "http://localhost";
        RestAssured.basePath = "/budget/contact";
        RestAssured.filters(new io.restassured.filter.log.RequestLoggingFilter(),
                new io.restassured.filter.log.ResponseLoggingFilter());
    }

    @Before
    public void setUp(){

        gson = new Gson();

        //RequestBasicAuthenticationFilter requestBasicAuthenticationFilter = new RequestBasicAuthenticationFilter(authenticationManager);
        // String aHash, String aUsername, Date aCreatedAd, String aData

        validEntryDate = new Date();
        validEntryHash = "1789h193hr19h8fh132";
        validEntryData = "{+123u8fiojijn2k3n4ro2j3oi4j23krj23kr4j234j234j23l4j24j}";
        validEntry = new Entry(validEntryHash, userJohannes, validEntryDate, validEntryData);
        validEntryJSON = gson.toJson(validEntry);


        ResourceConfig rc = new ResourceConfig()
                .register(RequestLoggingFilter.class)
                .register(ResponseLoggingFilter.class)
                .register(CrossOriginFilterImpl.class);

        server = GrizzlyHttpServerFactory.createHttpServer(URI.create(serveradress), rc);
    }

    @After
    public void clean() {
        server.stop();
    }


    /**
     * addEntry
     */

    @Test
    public void testAddValidEntry() {

        RestAssured.given().
                contentType("application/json")
                .when().body(validEntryJSON);
    }

    @Test
    public void testAddInvalidEntry() {
        // TODO
    }

    @Test
    public void testAddValidEntryWithWrongAuth() {
        // TODO
    }

    /**
     * getEntries
     */

    @Test
    public void testGetEntries() {
        // TODO
    }

    @Test
    public void testGetEntriesWithWrongAuth() {
        // TODO
    }


    /**
     * UpdateEntry
     */


    @Test
    public void testUpdateValidEntry() {
        // TODO
    }

    @Test
    public void testUpdateInvalidEntry() {
        // TODO
    }

    @Test
    public void testUpdateValidEntryWithWrongAuth() {
        // TODO
    }

    /**
     * deleteEntry
     */


    @Test
    public void testDeleteEntries() {
        // TODO
    }

    @Test
    public void testDeleteEntriesWithWrongAuth() {
        // TODO
    }


}
