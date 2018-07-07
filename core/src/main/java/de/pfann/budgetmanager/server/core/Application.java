package de.pfann.budgetmanager.server.core;

import de.pfann.budgetmanager.server.common.facade.*;
import de.pfann.budgetmanager.server.common.model.AppUser;
import de.pfann.budgetmanager.server.common.model.Entry;
import de.pfann.budgetmanager.server.common.model.Tag;
import de.pfann.budgetmanager.server.persistenscouchdb.core.CouchDbConnectorFactory;
import de.pfann.budgetmanager.server.persistenscouchdb.dao.CDBEntryDaoFactory;
import de.pfann.budgetmanager.server.persistenscouchdb.dao.CDBRunDoaFactory;
import de.pfann.budgetmanager.server.persistenscouchdb.dao.CDBStandingOrderDaoFactory;
import de.pfann.budgetmanager.server.persistenscouchdb.dao.CDBUserDaoFactory;
import de.pfann.budgetmanager.server.persistenscouchdb.facade.*;
import de.pfann.budgetmanager.server.restservices.resources.*;
import de.pfann.budgetmanager.server.restservices.resources.core.CrossOriginFilterImpl;
import de.pfann.budgetmanager.server.restservices.resources.core.RequestBasicAuthenticationFilter;
import de.pfann.budgetmanager.server.restservices.resources.core.RequestLoggingFilter;
import de.pfann.budgetmanager.server.restservices.resources.core.ResponseLoggingFilter;
import de.pfann.budgetmanager.server.restservices.resources.email.EmailService;
import org.ektorp.CouchDbInstance;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.ObjectMapperFactory;
import org.ektorp.impl.StdCouchDbInstance;
import org.ektorp.impl.StdObjectMapperFactory;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Application {

    public static final String BASE_URI = "http://0.0.0.0:8090/budget/";


    public Application(){
        // default
    }

    public static void main(String[] args) throws IOException {
        Application application = new Application();
        application.start();
    }

    public void start() throws IOException {
        cleanDb();
        /**
         * couchdb
         */
        HttpClient httpClient = new StdHttpClient.Builder()
                .url("http://localhost:5984")
                .build();
        CouchDbInstance dbInstance = new StdCouchDbInstance(httpClient);
        ObjectMapperFactory objectMapperFactory = new StdObjectMapperFactory();
        CouchDbConnectorFactory couchDbConnectorFactory = new CouchDbConnectorFactory(dbInstance,objectMapperFactory);

        CDBUserDaoFactory userDaoFactory = new CDBUserDaoFactory(couchDbConnectorFactory);
        CDBKontoDatabaseFacade kontoDatabaseFacade = new CDBKontoDatabaseFacade(couchDbConnectorFactory,dbInstance);
        CDBEntryDaoFactory entryDaoFactory = new CDBEntryDaoFactory(couchDbConnectorFactory);
        CDBStandingOrderDaoFactory standingOrderDaoFactory = new CDBStandingOrderDaoFactory(couchDbConnectorFactory);
        CDBRunDoaFactory runDaoFactory = new CDBRunDoaFactory(couchDbConnectorFactory);

        AppUserFacade userFacade = new CDBUserFacade(userDaoFactory, kontoDatabaseFacade);
        EntryFacade entryFacade = new CDBEntryFacade(userDaoFactory,entryDaoFactory);
        RotationEntryFacade standingOrderFacade = new CDBStandingOrderFacade(standingOrderDaoFactory,userDaoFactory);
        RunFacade runFacade = new CDBRunFacade(runDaoFactory);
        TagStatisticFacade statisticFacade = new CDBStatisticFacade(userDaoFactory);


        /**
         * resources
         */
        UserResourceFacade userResourceFacade = new UserResourceFacade(userFacade,new EmailService());
        UserResource userResource = new UserResource(userResourceFacade);

        EntryResourceFacade entryResourceFacade = new EntryResourceFacade(userFacade,entryFacade);
        EntryResource entryResource = new EntryResource(entryResourceFacade);

        RotationEntryResourceFacade rotationEntryResourceFacade = new RotationEntryResourceFacade(userFacade,standingOrderFacade);
        RotationEntryResource rotationEntryResource = new RotationEntryResource(rotationEntryResourceFacade);

        TagStatisticResourceFacade tagStatisticResourceFacade = new TagStatisticResourceFacade(statisticFacade,userFacade);
        TagStatisticResource tagStatisticResource = new TagStatisticResource(tagStatisticResourceFacade);

        EncryptionResourceFacade encryptionResourceFacade = new EncryptionResourceFacade(userFacade);
        EncryptionResource encryptionResource = new EncryptionResource(encryptionResourceFacade);


        AppUser user = createUserIfNotExist(userFacade,entryFacade);



        final ResourceConfig rc = new ResourceConfig()
                .register(RequestLoggingFilter.class)
                .register(ResponseLoggingFilter.class)
                .register(CrossOriginFilterImpl.class)
                .register(RequestBasicAuthenticationFilter.class)
                .register(userResource)
                .register(entryResource)
                .register(rotationEntryResource)
                .register(tagStatisticResource)
                .register(encryptionResource)
                .register(HelloResource.class);


        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl\nHit enter to stop it...", BASE_URI));


        final HttpServer server = GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
        System.in.read();



        server.stop();

        // end - Add defaultUser

        /*
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

        JobEngine jobEngine = new JobEngine(runFacade,provider, jobRunners);

        ExecutionTime startTime = new OneOClockAM();
        TimeInterval timeInterval1 = new Daily();

        JobScheduler scheduler = new JobScheduler(startTime,timeInterval1,jobEngine);
        scheduler.start();
        */



    }

    private void cleanDb() {
        HttpClient httpClient = null;
        try {
            httpClient = new StdHttpClient.Builder()
                    .url("http://localhost:5984")
                    .build();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        CouchDbInstance dbInstance = new StdCouchDbInstance(httpClient);

        List<String> dbs = dbInstance.getAllDatabases();

        for(String db : dbs){
            System.out.println(db);
            dbInstance.deleteDatabase(db);
        }
    }

    private AppUser createUserIfNotExist(AppUserFacade userFacade, EntryFacade entryFacade) {
        AppUser user = new AppUser();
        user.setName("johannes-1234");
        user.setPassword("key");
        user.setEncrypted(true);
        user.setEncryptionText("Das ist der Text");
        user.setEmail("jopfann@gmail.com");

        userFacade.createNewUser(user);


        Entry entry = new Entry();
        entry.setAppUser(user);

        List<Tag> tags = new LinkedList<>();
        Tag tag1 = new Tag("luxus");
        Tag tag2 = new Tag("fixkosten");
        tags.add(tag1);
        tags.add(tag2);
        entry.setTags(tags);
        entry.setCreated_at(new Date());
        entry.setHash("asdfasdf");
        entry.setMemo("memo");
        entry.setAmount("1234");
        entryFacade.persistEntry(entry);

        return user;
    }
}
