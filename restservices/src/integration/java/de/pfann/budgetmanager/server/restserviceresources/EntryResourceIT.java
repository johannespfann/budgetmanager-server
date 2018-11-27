package de.pfann.budgetmanager.server.restserviceresources;

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
}
