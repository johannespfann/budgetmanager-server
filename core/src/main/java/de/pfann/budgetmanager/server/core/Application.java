package de.pfann.budgetmanager.server.core;

import de.pfann.budgetmanager.server.jobengine.core.*;
import de.pfann.budgetmanager.server.jobengine.rotationjobs.*;
import de.pfann.budgetmanager.server.persistens.core.SessionDistributor;
import de.pfann.budgetmanager.server.persistens.daos.AppUserSQLFacade;
import de.pfann.budgetmanager.server.persistens.daos.EntrySQLFacade;
import de.pfann.budgetmanager.server.persistens.daos.RotationEntrySQLFacade;
import de.pfann.budgetmanager.server.persistens.daos.RunSQLFacade;
import org.ektorp.CouchDbInstance;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.ObjectMapperFactory;
import org.ektorp.impl.StdCouchDbInstance;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;
import java.util.LinkedList;
import java.util.List;
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

        HttpClient httpClient = new StdHttpClient.Builder()
                .url("http://localhost:5984")
                .build();
        CouchDbInstance dbInstance = new StdCouchDbInstance(httpClient);
        ObjectMapperFactory factory = new BMObjectMapperFactory();



        final ResourceConfig rc = new ResourceConfig().packages("de.pfann.budgetmanager.server.restservices.resources");

        final HttpServer server = GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);

        SessionDistributor.createForProd();
        Logger.getLogger("org.hibernate").setLevel(Level.OFF);

        // Add defaultUser

        AppUserSQLFacade facade = new AppUserSQLFacade();

        // end - Add defaultUser


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

        TimeInterval timeInterval = new HourInterval(12);
        RunProvider provider = new RunProviderImpl(timeInterval);

        List<JobRunner> jobRunners = new LinkedList<>();
        JobRunner jobRunner = new JobRunner(rotationEntryJob);
        jobRunners.add(jobRunner);

        RunSQLFacade runFacade = new RunSQLFacade();
        JobEngine jobEngine = new JobEngine(runFacade,provider, jobRunners);

        ExecutionTime startTime = new OneOClockAM();
        TimeInterval timeInterval1 = new Daily();

        JobScheduler scheduler = new JobScheduler(startTime,timeInterval1,jobEngine);
        scheduler.start();


        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl\nHit enter to stop it...", BASE_URI));


        System.out.println("All Users: " + facade.getAllUser().size());
        System.in.read();
        server.stop();

    }
}
