package de.pfann.budgetmanager.server;

import de.pfann.budgetmanager.server.login.LoginUtil;
import de.pfann.budgetmanager.server.model.AppUser;
import de.pfann.budgetmanager.server.model.Category;
import de.pfann.budgetmanager.server.persistens.core.DbWriter;
import de.pfann.budgetmanager.server.persistens.daos.AppUserFacade;
import de.pfann.budgetmanager.server.persistens.daos.CategoryDao;
import de.pfann.budgetmanager.server.persistens.daos.CategoryFacade;
import de.pfann.budgetmanager.server.rotationjobs.JobExecuterEngine;
import de.pfann.budgetmanager.server.rotationjobs.MonthlyRotationEntry;
import de.pfann.budgetmanager.server.rotationjobs.RotationEntry;
import de.pfann.budgetmanager.server.rotationjobs.RotationEntryJob;
import de.pfann.budgetmanager.server.rotationjobs.RunDao;
import de.pfann.budgetmanager.server.rotationjobs.RunFacade;
import de.pfann.budgetmanager.server.rotationjobs.RunInfoDao;
import de.pfann.budgetmanager.server.util.Util;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;


import java.io.IOException;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Hello world!
 *
 */
public class App 
{
    public static final String BASE_URI = "http://localhost:8081/budget/";
    //public static final String BASE_URI = "http://192.168.2.103:8081/budget/";
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

        TestClass environmentObjects = new TestClass();
        environmentObjects.persistEnviroment();

        RotationEntryJob rotationEntryJob = new RotationEntryJob();


        RunInfoDao runInfoDao = RunInfoDao.create();
        RunDao runDao = RunDao.create();
        RunFacade runFacade = new RunFacade(runInfoDao,runDao);

        JobExecuterEngine engine = JobExecuterEngine.builder()
                .addJob(rotationEntryJob)
                .withRunFacade(runFacade)
                .build();

        engine.start();


        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl\nHit enter to stop it...", BASE_URI));
        System.in.read();
        server.stop();
    }


}
