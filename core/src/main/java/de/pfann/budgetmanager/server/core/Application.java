package de.pfann.budgetmanager.server.core;

import de.pfann.budgetmanager.server.common.configuration.ConfigurationProvider;
import de.pfann.budgetmanager.server.common.email.EmailService;
import de.pfann.budgetmanager.server.common.facade.*;
import de.pfann.budgetmanager.server.common.util.DateUtil;
import de.pfann.budgetmanager.server.persistens.core.DbConnectorFactory;
import de.pfann.budgetmanager.server.persistens.dao.DBEntryDaoFactory;
import de.pfann.budgetmanager.server.persistens.dao.DBStandingOrderDaoFactory;
import de.pfann.budgetmanager.server.persistens.facade.DBEntryFacade;
import de.pfann.budgetmanager.server.persistens.facade.DBStandingOrderFacade;
import de.pfann.budgetmanager.server.persistenscouchdb.core.CouchDbConnectorFactory;
import de.pfann.budgetmanager.server.persistenscouchdb.dao.CDBEntryDaoFactory;
import de.pfann.budgetmanager.server.persistenscouchdb.dao.CDBStandingOrderDaoFactory;
import de.pfann.budgetmanager.server.persistenscouchdb.dao.CDBUserDao;
import de.pfann.budgetmanager.server.persistenscouchdb.dao.CDBUserDaoFactory;
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
        ConfigurationProvider configurationProvider = new ConfigurationProvider("applications.properties");
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

        /**
         * couchdbfacade
         */

        CouchDBUtil couchDBUtil = new CouchDBUtil(httpClient);


        CouchDbConnectorFactory couchDbConnectorFactory = new CouchDbConnectorFactory(dbInstance, couchdbprefix, objectMapperFactory);
        CDBUserDaoFactory CDBUserDaoFactory = new CDBUserDaoFactory(couchDbConnectorFactory);

        /**
         * resources
         */

        AuthenticationManager authenticationManager = new AuthenticationManager();
        ContactResourceFacade contactResourceFacade = new ContactResourceFacade(emailService);
        ContactResource contactResource = new ContactResource(contactResourceFacade);

        /**
         * resources
         */

        CDBUserDao userDao = CDBUserDaoFactory.createDao();

        ActivationPool activationPool = new ActivationPool();
        UserFacade userFacadeV2 = new CDBUserFacade(CDBUserDaoFactory);
        UserResourceFacade userResourceFacade = new UserResourceFacade(userFacadeV2,emailService,authenticationManager,activationPool);
        UserResource userResource = new UserResource(userResourceFacade);

        AccountFacade accountFacade = new CDBAccountFacade(userDao);
        AccountResourceFacade accountResouceFacade = new AccountResourceFacade(accountFacade,userFacadeV2);
        AccountResource accountResource = new AccountResource(accountResouceFacade);

        CDBEntryDaoFactory v2EntryDaoFactory = new CDBEntryDaoFactory(couchDbConnectorFactory);
        EntryFacade entryFacade = new CDBEntryFacade(v2EntryDaoFactory);
        EntryResourceFacade entryResourceFacade = new EntryResourceFacade(accountFacade, entryFacade);
        EntryResource entryResource = new EntryResource(entryResourceFacade);

        CDBStandingOrderDaoFactory v2StandingDaoFactory = new CDBStandingOrderDaoFactory(couchDbConnectorFactory);
        StandingOrderFacade standingOrderFacade = new CDBStandingOrderFacade(v2StandingDaoFactory);
        StandingOrderResourceFacade standingOrderResourceFacade = new StandingOrderResourceFacade(accountFacade, standingOrderFacade);
        StandingOrderResource standingOrderResource = new StandingOrderResource(standingOrderResourceFacade);

        TagRuleFacade tagRuleFacade = new CDBTagRuleFacade(userDao);
        TagRuleResourceFacade tagRuleResourceFacade = new TagRuleResourceFacade(tagRuleFacade);
        TagRuleResource tagRuleResource = new TagRuleResource(tagRuleResourceFacade);


        /**
         * coppy resource
         */

        DbConnectorFactory copyCouchDbConnectorFactory = new DbConnectorFactory(dbInstance, "copy_" + couchdbprefix, objectMapperFactory);
        DBStandingOrderDaoFactory copyStandingOrderDaoFactory = new DBStandingOrderDaoFactory(copyCouchDbConnectorFactory);
        DBStandingOrderFacade copyStandingOrderFacade = new DBStandingOrderFacade(copyStandingOrderDaoFactory);
        DBEntryDaoFactory copyEntryDaoFactor = new DBEntryDaoFactory(copyCouchDbConnectorFactory);
        DBEntryFacade copyEntryFacade = new DBEntryFacade(copyEntryDaoFactor);

        CopyRepositoryResourceFacade copyResouceFacade = new CopyRepositoryResourceFacade(accountFacade, copyEntryFacade, copyStandingOrderFacade);
        CopyRepositoryResource copyRepositoryResource = new CopyRepositoryResource(copyResouceFacade);

        /**
         * authantication
         */

        RequestBasicAuthenticationFilter requestBasicAuthenticationFilter = new RequestBasicAuthenticationFilter(authenticationManager);

        final ResourceConfig rc = new ResourceConfig()
                .register(RequestLoggingFilter.class)
                .register(ResponseLoggingFilter.class)
                .register(CrossOriginFilterImpl.class)
                .register(ContactValidatRequestFilter.class)
                .register(EmailDublicatedExceptionMapper.class)
                .register(requestBasicAuthenticationFilter)
                .register(contactResource)
                .register(accountResource)
                .register(tagRuleResource)
                .register(standingOrderResource)
                .register(entryResource)
                .register(userResource)
                .register(new HealthResource())
                .register(copyRepositoryResource);


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
