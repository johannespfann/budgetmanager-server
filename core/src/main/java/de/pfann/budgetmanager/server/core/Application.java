package de.pfann.budgetmanager.server.core;

import de.pfann.budgetmanager.server.common.configuration.ConfigurationProvider;
import de.pfann.budgetmanager.server.common.email.EmailService;
import de.pfann.budgetmanager.server.common.facade.*;
import de.pfann.budgetmanager.server.common.util.DateUtil;
import de.pfann.budgetmanager.server.jobengine.core.*;
import de.pfann.budgetmanager.server.jobengine.rotationjobs.*;
import de.pfann.budgetmanager.server.persistenscouchdb.core.CouchDbConnectorFactory;
import de.pfann.budgetmanager.server.persistenscouchdb.dao.*;
import de.pfann.budgetmanager.server.persistenscouchdb.facade.*;
import de.pfann.budgetmanager.server.persistenscouchdb.util.CouchDBUtil;
import de.pfann.budgetmanager.server.restservices.resources.*;
import de.pfann.budgetmanager.server.restservices.resources.core.*;
import de.pfann.budgetmanager.server.restservices.resources.filter.ContactValidatRequestFilter;
import de.pfann.budgetmanager.server.restservices.resources.login.ActivationPool;
import de.pfann.budgetmanager.server.restservices.resources.login.AuthenticationManager;
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
import java.net.URI;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

public class Application {

    public final static String KEY_SERVER_ADRESS = "server.adress";
    public final static String KEY_SERVER_PORT = "server.port";
    public final static String KEY_SERVER_BASE_PATH = "server.basepath";
    public final static String KEY_COUCHDB_HOST = "couchdb.host";
    public final static String KEY_COUCHDB_PORT = "couchdb.port";
    public final static String KEY_COUCHDB_PW = "couchdb.pw";
    public final static String KEY_COUCHDB_USER = "couchdb.user";
    public final static String KEY_COUCHDB_PREFIX = "couchdb.prefix";

    public static final String KEY_MAIL_SMTP_AUTH = "mail.smtp.auth";
    public static final String KEY_MAIL_SMTP_STARTTL_ENABLE = "mail.smtp.starttls.enable";
    public static final String KEY_MAIL_SMTP_HOST = "mail.smtp.host";
    public static final String KEY_MAIL_SMTP_PORT = "mail.smtp.port";
    public static final String KEY_MAIL_EMAIL = "mail.email";
    public static final String KEY_MAIL_PASSWORD = "mail.pw";


    public Application(){
        //
    }

    public static void main(String[] args) throws IOException {
        Application application = new Application();
        ConfigurationProvider configurationProvider = new ConfigurationProvider("budgetmanager.properties");
        Properties properties = configurationProvider.getProperties();
        Set<String> propertyList = properties.stringPropertyNames();

        for(String key : propertyList){
            System.out.println(key +" : " + properties.getProperty(key));
        }

        application.start(properties);
    }

    public void start(Properties aProperties) throws IOException {

        /**
         * couchdb
         */
        String serveradress = getServerAdress(aProperties);
        String couchdbadress = getCouchDBAdress(aProperties);
        String couchdbPw = aProperties.getProperty(KEY_COUCHDB_PW);
        String couchdbUser = aProperties.getProperty(KEY_COUCHDB_USER);

        String mailSmtpHost = aProperties.getProperty(KEY_MAIL_SMTP_HOST);
        String mailSmtpAuth = aProperties.getProperty(KEY_MAIL_SMTP_AUTH);
        String mailSmtpStartTlsEnable = aProperties.getProperty(KEY_MAIL_SMTP_STARTTL_ENABLE);
        String mailSmtpPort = aProperties.getProperty(KEY_MAIL_SMTP_PORT);
        String senderEmail = aProperties.getProperty(KEY_MAIL_EMAIL);
        String senderPassword = aProperties.getProperty(KEY_MAIL_PASSWORD);
        String couchdbprefix = aProperties.getProperty(KEY_COUCHDB_PREFIX);

        EmailService emailService = new EmailService(
                mailSmtpHost,
                mailSmtpAuth,
                mailSmtpStartTlsEnable,
                mailSmtpPort,
                senderEmail,
                senderPassword);

        StdHttpClient.Builder httpClientBuilder = new StdHttpClient.Builder();
        httpClientBuilder.url(couchdbadress);

        if(foundCouchDbCredentials(aProperties)) {
            httpClientBuilder
                    .username(couchdbUser)
                    .password(couchdbPw);
        }

        HttpClient httpClient = httpClientBuilder.build();
        CouchDbInstance dbInstance = new StdCouchDbInstance(httpClient);

        ObjectMapperFactory objectMapperFactory = new StdObjectMapperFactory();
        CouchDbConnectorFactory couchDbConnectorFactory = new CouchDbConnectorFactory(dbInstance,couchdbprefix,objectMapperFactory);

        CDBUserDaoFactory userDaoFactory = new CDBUserDaoFactory(couchDbConnectorFactory);
        CDBKontoDatabaseCreator kontoDatabaseFacade = new CDBKontoDatabaseCreator(couchDbConnectorFactory,dbInstance);
        CDBEntryDaoFactory entryDaoFactory = new CDBEntryDaoFactory(couchDbConnectorFactory);
        CDBStandingOrderDaoFactory standingOrderDaoFactory = new CDBStandingOrderDaoFactory(couchDbConnectorFactory);
        CDBRunDoaFactory runDaoFactory = new CDBRunDoaFactory(couchDbConnectorFactory);

        AppUserFacade userFacade = new CDBUserFacade(userDaoFactory, kontoDatabaseFacade);
        EntryFacade entryFacade = new CDBEntryFacade(userDaoFactory,entryDaoFactory);

        RunFacade runFacade = new CDBRunFacade(runDaoFactory);
        TagStatisticFacade statisticFacade = new CDBStatisticFacade(userDaoFactory);


        /**
         * couchdbfacade v2
         */

        String couchdbPrefixV2 = "cashtrack";
        CouchDBUtil couchDBUtil = new CouchDBUtil(httpClient);
        //couchDBUtil.deleteDatabases(couchdbPrefixV2);


        CouchDbConnectorFactory couchDbConnectorFactoryV2 = new CouchDbConnectorFactory(dbInstance,couchdbPrefixV2,objectMapperFactory);
        UserDaoFactory userDaoFactoryV2 = new UserDaoFactory(couchDbConnectorFactoryV2);




        /**
         * rotationpattern
         */

        RotationEntryPattern monthlyRotationEntry = new MonthlyRotationPattern();
        RotationEntryPattern quarterRotationEntryPattern = new QuarterRotationEntryPattern();
        RotationEntryPattern yearlyRotationEntryPattern = new YearlyRotationPattern();

        List<RotationEntryPattern> patternList = new LinkedList<>();
        patternList.add(monthlyRotationEntry);
        patternList.add(quarterRotationEntryPattern);
        patternList.add(yearlyRotationEntryPattern);

        RotationEntryExecuter rotationEntryExecuter = new RotationEntryExecuter(patternList,null,entryFacade);

        ActivationPool activationPool = new ActivationPool();

        /**
         * resources
         */

        AuthenticationManager authenticationManager = new AuthenticationManager(userFacade);
        UserResourceFacade userResourceFacade = new UserResourceFacade(userFacade,emailService,authenticationManager, activationPool);
        UserResource userResource = new UserResource(userResourceFacade);



        TagStatisticResourceFacade tagStatisticResourceFacade = new TagStatisticResourceFacade(statisticFacade,userFacade);
        TagStatisticResource tagStatisticResource = new TagStatisticResource(tagStatisticResourceFacade);

        EncryptionResourceFacade encryptionResourceFacade = new EncryptionResourceFacade(userFacade);
        EncryptionResource encryptionResource = new EncryptionResource(encryptionResourceFacade);

        ContactResourceFacade contactResourceFacade = new ContactResourceFacade(emailService);
        ContactResource contactResource = new ContactResource(contactResourceFacade);

        /**
         * resources v2
         */
        UserDao userDao = userDaoFactoryV2.createDao();

        UserFacade userFacadeV2 = new V2CDBUserFacade(userDaoFactoryV2);
        V2UserResourceFacade v2UserResourceFacade = new V2UserResourceFacade(userFacadeV2,emailService,authenticationManager,activationPool);
        V2UserResource v2userResource = new V2UserResource(v2UserResourceFacade);

        AccountFacade accountFacade = new CDBAccountFacade(userDao);
        AccountResourceFacade accountResouceFacade = new AccountResourceFacade(accountFacade,userFacadeV2);
        AccountResource accountResource = new AccountResource(accountResouceFacade);

        V2CDBEntryDaoFactory v2EntryDaoFactory = new V2CDBEntryDaoFactory(couchDbConnectorFactoryV2);
        Entry2Facade entry2Facade = new V2CDBEntryFacade(v2EntryDaoFactory);
        V2EntryResourceFacade v2EntryResourceFacade = new V2EntryResourceFacade(accountFacade,entry2Facade);
        V2EntryResource v2EntryResource = new V2EntryResource(v2EntryResourceFacade);

        V2CDBStandingOrderDaoFactory v2StandingDaoFactory = new V2CDBStandingOrderDaoFactory(couchDbConnectorFactoryV2);
        StandingOrder2Facade standingOrder2Facade = new V2CDBStandingOrderFacade(v2StandingDaoFactory);
        V2StandingOrderResourceFacade v2StandingOrderResourceFacade = new V2StandingOrderResourceFacade(accountFacade,standingOrder2Facade);
        V2StandingOrderResource v2StandingOrderResource = new V2StandingOrderResource(v2StandingOrderResourceFacade);

        /**
         * authantication
         */

        RequestBasicAuthenticationFilter requestBasicAuthenticationFilter = new RequestBasicAuthenticationFilter(authenticationManager);

        final ResourceConfig rc = new ResourceConfig()
                .register(RequestLoggingFilter.class)
                .register(ResponseLoggingFilter.class)
                .register(CrossOriginFilterImpl.class)
                .register(ContactValidatRequestFilter.class)
                .register(requestBasicAuthenticationFilter)
                .register(userResource)
                .register(tagStatisticResource)
                .register(encryptionResource)
                .register(contactResource)
                .register(EmailDublicatedExceptionMapper.class)

                /**
                 * new resources -> v2
                 */
                .register(accountResource)
                .register(v2StandingOrderResource)
                .register(v2EntryResource)
                .register(v2userResource);

        Job rotationEntryJob = new RotationEntryJob(
                patternList,
                userFacade,
                entryFacade,
                null);

        TimeInterval timeIntervalOfRuns = new HourInterval(12);
        RunProvider provider = new RunProviderImpl(timeIntervalOfRuns);

        List<JobRunner> jobRunners = new LinkedList<>();
        JobRunner jobRunner = new JobRunner(rotationEntryJob);
        jobRunners.add(jobRunner);

        JobEngine jobEngine = new JobEngine(runFacade,provider, jobRunners);

        ExecutionTime startTime = new SecStartTime(10);
        TimeInterval timeIntervalOfScheduler = new HourInterval(2);

        JobScheduler scheduler = new JobScheduler(startTime,timeIntervalOfScheduler,jobEngine);
        //scheduler.start();

        System.out.println("Current time of server     : " + LocalDateTime.now());
        System.out.println("Current time of application: " + DateUtil.getCurrentTimeOfBERLIN());

        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl\nHit enter to stop it...", serveradress));

        final HttpServer server = GrizzlyHttpServerFactory.createHttpServer(URI.create(serveradress), rc);
        System.in.read();
        server.stop();
    }

    private boolean foundCouchDbCredentials(Properties aProperties) {
        String couchdbPw = aProperties.getProperty(KEY_COUCHDB_PW);
        String couchdbUser = aProperties.getProperty(KEY_COUCHDB_USER);

        if(couchdbPw == null || couchdbPw.isEmpty()){
            return false;
        }

        if(couchdbUser == null || couchdbUser.isEmpty()){
            return  false;
        }

        return true;
    }

    private String getCouchDBAdress(Properties aProperties) {
        return new StringBuilder()
                    .append(aProperties.getProperty(KEY_COUCHDB_HOST))
                    .append(":")
                    .append(aProperties.getProperty(KEY_COUCHDB_PORT))
                    .toString();
    }

    private String getServerAdress(Properties aProperties) {
        String serveradress;
        serveradress= new StringBuilder()
                .append(aProperties.getProperty(KEY_SERVER_ADRESS))
                .append(":")
                .append(aProperties.getProperty(KEY_SERVER_PORT))
                .append("/")
                .append(aProperties.getProperty(KEY_SERVER_BASE_PATH))
                .append("/")
                .toString();
        return serveradress;
    }

}
