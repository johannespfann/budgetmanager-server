package de.pfann.budgetmanager.server.core;

import de.pfann.budgetmanager.server.jobengine.core.*;
import de.pfann.budgetmanager.server.jobengine.rotationjobs.*;
import de.pfann.budgetmanager.server.persistens.daos.AppUserSQLFacade;
import de.pfann.budgetmanager.server.persistens.daos.EntrySQLFacade;
import de.pfann.budgetmanager.server.persistens.daos.RotationEntrySQLFacade;
import de.pfann.budgetmanager.server.persistens.daos.RunSQLFacade;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Hello world!
 *
 */
public class StartDev
{
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

        /*
        LocalDate firstDayOfYear2018 =  LocalDate.now().with(firstDayOfYear());
        Run lastRun = new Run(firstDayOfYear2018);
        RunDao.create().save(lastRun);
        */
        Logger.getLogger("org.hibernate").setLevel(Level.OFF);


        RotationEntryPattern monthlyRotationEntry = new MonthlyRotationPattern();
        RotationEntryPattern quarterRotationEntryPattern = new QuarterRotationEntryPattern();
        RotationEntryPattern yearlyRotationEntryPattern = new YearlyRotationPattern();

        List<RotationEntryPattern> patternList = new LinkedList<>();
        patternList.add(monthlyRotationEntry);
        patternList.add(quarterRotationEntryPattern);
        patternList.add(yearlyRotationEntryPattern);

        Job rotationEntryJob = new RotationEntryJob(
                patternList,
                new AppUserSQLFacade(),
                new EntrySQLFacade(),
                new RotationEntrySQLFacade());


        TimeInterval timeInterval = new MinuteInterval(1);
        RunProvider provider = new RunProviderImpl(timeInterval);

        List<JobRunner> jobRunners = new LinkedList<>();
        JobRunner jobRunner = new JobRunner(rotationEntryJob);
        jobRunners.add(jobRunner);

        RunSQLFacade runFacade = new RunSQLFacade();
        JobEngine jobEngine = new JobEngine(runFacade,provider, jobRunners);

        ExecutionTime startTime = new SecStartTime(1);
        TimeInterval timeInterval1 = new MinuteInterval(180);

        JobScheduler scheduler = new JobScheduler(startTime,timeInterval1,jobEngine);
        scheduler.start();

        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl\nHit enter to stop it...", BASE_URI));
        System.in.read();
        server.stop();
    }


}
