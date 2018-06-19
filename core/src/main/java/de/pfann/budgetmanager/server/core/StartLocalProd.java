package de.pfann.budgetmanager.server.core;

import de.pfann.budgetmanager.server.common.model.AppUser;
import de.pfann.budgetmanager.server.common.model.Run;
import de.pfann.budgetmanager.server.common.util.LogUtil;
import de.pfann.budgetmanager.server.persistens.core.SessionDistributor;
import de.pfann.budgetmanager.server.persistens.daos.AppUserSQLFacade;
import de.pfann.budgetmanager.server.persistens.daos.RunSQLFacade;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StartLocalProd {

    //public static final String BASE_URI = "http://localhost:8081/budget/";
    public static final String BASE_URI = "http://0.0.0.0:8081/budget/";
    //public static final String BASE_URI = "http://192.168.2.103:8081/budget/";

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     * @return Grizzly HTTP server.
     */
    public static HttpServer startServer() {
        // create a resource config that scans for JAX-RS resources and providers
        // in com.example package
        final ResourceConfig rc = new ResourceConfig().packages("de.pfann.budgetmanager.server.restservices.resources");
        //final ResourceConfig rc = new ResourceConfig();
        //rc.register(new EntryResource());

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

        SessionDistributor.createForProd();

        AppUserSQLFacade facade = new AppUserSQLFacade();
        Logger.getLogger("org.hibernate").setLevel(Level.OFF);
        LogUtil.info(StartLocalProd.class,"StartApplication");
        LogUtil.info(StartLocalProd.class,"- Alle user");

        List<AppUser> userList = facade.getAllUser();

        for(AppUser user : userList){
            LogUtil.info(StartLocalProd.class,"- " + user.getName());
        }

        LogUtil.info(StartLocalProd.class," ");
        LogUtil.info(StartLocalProd.class," ");



        RunSQLFacade runFacade = new RunSQLFacade();

        List<Run> runs = runFacade.getAllRuns();

        LogUtil.info(StartLocalProd.class,"Show all Runs: " + runs.size());
        for(Run run : runs){
            LogUtil.info(StartLocalProd.class,"- " + run.getExecuted_at().toString());
        }





        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl\nHit enter to stop it...", BASE_URI));
        System.in.read();
        server.stop();
    }
}