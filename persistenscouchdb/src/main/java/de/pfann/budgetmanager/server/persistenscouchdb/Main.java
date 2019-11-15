package de.pfann.budgetmanager.server.persistenscouchdb;

import de.pfann.budgetmanager.server.common.configuration.ConfigurationProvider;
import de.pfann.budgetmanager.server.persistenscouchdb.core.CouchDbConnectorFactory;
import de.pfann.budgetmanager.server.persistenscouchdb.facade.EntrySupport;
import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.ObjectMapperFactory;
import org.ektorp.impl.StdCouchDbInstance;
import org.ektorp.impl.StdObjectMapperFactory;

import java.util.Properties;

public class Main {

    public final static String KEY_SERVER_ADRESS = "server.adress";
    public final static String KEY_SERVER_PORT = "server.port";
    public final static String KEY_SERVER_BASE_PATH = "server.basepath";
    public final static String KEY_COUCHDB_HOST = "couchdb.host";
    public final static String KEY_COUCHDB_PORT = "couchdb.port";
    public final static String KEY_COUCHDB_PW = "couchdb.pw";
    public final static String KEY_COUCHDB_USER = "couchdb.user";
    public final static String KEY_COUCHDB_PREFIX = "couchdb.prefix";


    public static void main(String[] args) {

        ConfigurationProvider configurationProvider = new ConfigurationProvider("budgetmanager.properties");
        Properties properties = configurationProvider.getProperties();

        String couchdbPw = properties.getProperty(KEY_COUCHDB_PW);
        String couchdbUser = properties.getProperty(KEY_COUCHDB_USER);

        String couchdbprefix = properties.getProperty(KEY_COUCHDB_PREFIX);


        StdHttpClient.Builder httpClientBuilder = new StdHttpClient.Builder();
        if(foundCouchDbCredentials(properties)) {
            httpClientBuilder
                    .username(couchdbUser)
                    .password(couchdbPw);
        }

        HttpClient httpClient = httpClientBuilder.build();
        ObjectMapperFactory objectMapperFactory = new StdObjectMapperFactory();

        CouchDbInstance dbInstance = new StdCouchDbInstance(httpClient);
        CouchDbConnectorFactory couchDbConnectorFactory = new CouchDbConnectorFactory(dbInstance, couchdbprefix, objectMapperFactory);


        CouchDbConnector connector = couchDbConnectorFactory.createCouchDbConnector("johannes-750_97ff6f_entries");
        EntrySupport support = new EntrySupport(connector);

        System.out.println(support.findByHash().size());



    }

    private static boolean foundCouchDbCredentials(Properties aProperties) {
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

    private static String getCouchDBAdress(Properties aProperties) {
        return new StringBuilder()
                .append(aProperties.getProperty(KEY_COUCHDB_HOST))
                .append(":")
                .append(aProperties.getProperty(KEY_COUCHDB_PORT))
                .toString();
    }

    private static String getServerAdress(Properties aProperties) {
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
