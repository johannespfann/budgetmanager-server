package de.pfann.budgetmanager.server;

import de.pfann.budgetmanager.server.login.LoginUtil;
import de.pfann.budgetmanager.server.model.AppUser;
import de.pfann.budgetmanager.server.model.Category;
import de.pfann.budgetmanager.server.persistens.AppUserFacade;
import de.pfann.budgetmanager.server.persistens.daos.AppUserDao;
import de.pfann.budgetmanager.server.persistens.daos.CategoryDao;
import de.pfann.budgetmanager.server.util.LogUtil;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;


import java.io.IOException;
import java.net.URI;

/**
 * Hello world!
 *
 */
public class App 
{
    public static final String BASE_URI = "http://192.168.2.103:8081/budget/";
    //public static final String BASE_URI = "http://192.168.2.106:8081/budget/";
    //public static final String BASE_URI = "http://192.168.2.101:8081/budget/";

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     * @return Grizzly HTTP server.
     */
    public static HttpServer startServer() {
        // create a resource config that scans for JAX-RS resources and providers
        // in com.example package
        final ResourceConfig rc = new ResourceConfig().packages("de.pfann.budgetmanager.server.resources");

        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    /**
     * Main method.
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        final HttpServer server = startServer();

        AppUserFacade userFacade = new AppUserFacade(AppUserDao.create(), CategoryDao.create());
        userFacade.createNewUser(
                LoginUtil.getUserNameWithUnique("johannes"),
                "johannes@pfann.de",
                "key");

        AppUser user = userFacade.getUserByNameOrEmail("johannes@pfann.de");

        userFacade.activateUser(user);


        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl\nHit enter to stop it...", BASE_URI));
        System.in.read();
        server.stop();
    }
}
