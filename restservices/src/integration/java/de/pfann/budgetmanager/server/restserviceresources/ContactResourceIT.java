package de.pfann.budgetmanager.server.restserviceresources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.pfann.budgetmanager.server.common.email.EmailService;
import de.pfann.budgetmanager.server.restservices.resources.ContactMessage;
import de.pfann.budgetmanager.server.restservices.resources.ContactResource;
import de.pfann.budgetmanager.server.restservices.resources.ContactResourceFacade;
import de.pfann.budgetmanager.server.restservices.resources.core.CrossOriginFilterImpl;
import de.pfann.budgetmanager.server.restservices.resources.core.RequestLoggingFilter;
import de.pfann.budgetmanager.server.restservices.resources.core.ResponseLoggingFilter;
import de.pfann.budgetmanager.server.restservices.resources.filter.ContactValidatRequestFilter;
import io.restassured.RestAssured;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.*;

import java.net.URI;

import static io.restassured.RestAssured.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;


public class ContactResourceIT {

    private EmailService emailService;
    private String serveradress = "http://localhost:8890/budget";
    private HttpServer server;

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
        System.out.println("Setup before class");
        // prepare emailservice
        emailService = mock(EmailService.class);
        doNothing().when(emailService).sendEmail(any(),any(),any());
        ContactResourceFacade contactResourceFacade = new ContactResourceFacade(emailService);
        ContactResource contactResource = new ContactResource(contactResourceFacade);

        //RequestBasicAuthenticationFilter requestBasicAuthenticationFilter = new RequestBasicAuthenticationFilter(authenticationManager);

        ResourceConfig rc = new ResourceConfig()
                .register(RequestLoggingFilter.class)
                .register(ResponseLoggingFilter.class)
                .register(CrossOriginFilterImpl.class)
                .register(ContactValidatRequestFilter.class)
                .register(contactResource);

        server = GrizzlyHttpServerFactory.createHttpServer(URI.create(serveradress), rc);
    }

    @Test
    public void testValidRequest() throws JsonProcessingException {
        // prepare
        ContactMessage message = new ContactMessage("johannes", "jopf88@googlemail.com","Ganz viel Text");
        ObjectMapper jsonMapper = new ObjectMapper();
        String bodyJson = jsonMapper.writeValueAsString(message);

        // validate
        given()
                .contentType("application/json")
        .when().body(bodyJson)
                .post("/send")
        .then()
                .statusCode(204);
    }

    @Test
    public void testEmptyEmail() throws JsonProcessingException {
        // prepare
        ContactMessage message = new ContactMessage("johannes", "","Ganz viel Text");
        ObjectMapper jsonMapper = new ObjectMapper();
        String bodyJson = jsonMapper.writeValueAsString(message);

        // validate
        given().contentType("application/json").when().body(bodyJson).post("/send").then().statusCode(500);
    }

    @Test
    public void testEmptyName() throws JsonProcessingException {
        // prepare
        ContactMessage message = new ContactMessage("", "jopf88@googlemail.com","Ganz viel Text");
        ObjectMapper jsonMapper = new ObjectMapper();
        String bodyJson = jsonMapper.writeValueAsString(message);

        // validate
        given().contentType("application/json").when().body(bodyJson).post("/send").then().statusCode(500);
    }

    @Test
    public void testEmptyMessage() throws JsonProcessingException {
        // prepare
        ContactMessage message = new ContactMessage("johannes", "jopf88@googlemail.com","");
        ObjectMapper jsonMapper = new ObjectMapper();
        String bodyJson = jsonMapper.writeValueAsString(message);

        // validate
        given().contentType("application/json").when().body(bodyJson).post("/send").then().statusCode(500);
    }

    @After
    public void clean() {
        server.stop();
    }

}
