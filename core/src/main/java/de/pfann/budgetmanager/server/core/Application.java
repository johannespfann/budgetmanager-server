package de.pfann.budgetmanager.server.core;

import de.pfann.budgetmanager.server.core.rotationjobs.DailyExecutor;
import de.pfann.budgetmanager.server.persistens.core.SessionDistributor;
import de.pfann.budgetmanager.server.persistens.daos.AppUserFacade;
import de.pfann.budgetmanager.server.persistens.model.AppUser;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Application {

    public static final String BASE_URI = "http://0.0.0.0:8081/budget/";


    public Application(){
        // default
    }

    public static void main(String[] args) throws IOException {
        Application application = new Application();
        application.start();
    }

    public void start() throws IOException {

        final ResourceConfig rc = new ResourceConfig().packages("de.pfann.budgetmanager.server.restservices.resources");

        final HttpServer server = GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);

        SessionDistributor.createForProd();

        AppUserFacade facade = new AppUserFacade();


        DailyExecutor executor = new DailyExecutor();
        executor.start();

        Logger.getLogger("org.hibernate").setLevel(Level.OFF);

        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl\nHit enter to stop it...", BASE_URI));


        System.out.println("All Users: " + facade.getAllUser().size());
        System.in.read();
        server.stop();

    }
}
