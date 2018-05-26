package de.pfann.budgetmanager.server.core;

import de.pfann.budgetmanager.server.core.jobengine.*;
import de.pfann.budgetmanager.server.core.rotationjobs.MonthlyRotationPattern;
import de.pfann.budgetmanager.server.core.rotationjobs.QuarterRotationEntryPattern;
import de.pfann.budgetmanager.server.core.rotationjobs.RotationEntryJob;
import de.pfann.budgetmanager.server.core.rotationjobs.RotationEntryPattern;
import de.pfann.budgetmanager.server.persistens.core.SessionDistributor;
import de.pfann.budgetmanager.server.persistens.daos.AppUserFacade;
import de.pfann.budgetmanager.server.persistens.daos.EntryFacade;
import de.pfann.budgetmanager.server.persistens.daos.RotationEntryFacade;
import de.pfann.budgetmanager.server.persistens.daos.RunFacade;
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

        final ResourceConfig rc = new ResourceConfig().packages("de.pfann.budgetmanager.server.restservices.resources");

        final HttpServer server = GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);

        SessionDistributor.createForProd();


        RotationEntryPattern monthlyRotationEntry = new MonthlyRotationPattern();
        QuarterRotationEntryPattern quarterRotationEntryPattern = new QuarterRotationEntryPattern();

        List<RotationEntryPattern> patternList = new LinkedList<>();
        patternList.add(monthlyRotationEntry);
        patternList.add(quarterRotationEntryPattern);

        Job rotationEntryJob = new RotationEntryJob(
                patternList,
                new AppUserFacade(),
                new EntryFacade(),
                new RotationEntryFacade());


        TimeInterval timeInterval = new MinuteInterval(1);
        RunProvider provider = new RunProviderImpl(timeInterval);

        List<JobRunner> jobRunners = new LinkedList<>();
        JobRunner jobRunner = new JobRunner(rotationEntryJob);
        jobRunners.add(jobRunner);

        RunFacade runFacade = new RunFacade();
        JobEngine jobEngine = new JobEngine(runFacade,provider, jobRunners);

        //jobEngine.start();

        ExecutionTime startTime = new SecStartTime(1);
        TimeInterval timeInterval1 = new MinuteInterval(5);

        JobScheduler scheduler = new JobScheduler(startTime,timeInterval1,jobEngine);
        scheduler.start();



        Logger.getLogger("org.hibernate").setLevel(Level.OFF);

        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl\nHit enter to stop it...", BASE_URI));


        AppUserFacade facade = new AppUserFacade();
        System.out.println("All Users: " + facade.getAllUser().size());
        System.in.read();
        server.stop();

    }
}
