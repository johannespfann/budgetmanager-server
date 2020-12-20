package de.pfann.budgetmanager.server.persistenscouchdb.file;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import de.pfann.budgetmanager.server.common.configuration.ConfigurationProvider;
import de.pfann.budgetmanager.server.common.facade.AccountFacade;
import de.pfann.budgetmanager.server.common.facade.EntryFacade;
import de.pfann.budgetmanager.server.common.facade.StandingOrderFacade;
import de.pfann.budgetmanager.server.model.Account;
import de.pfann.budgetmanager.server.model.Entry;
import de.pfann.budgetmanager.server.model.StandingOrder;
import de.pfann.budgetmanager.server.model.User;
import de.pfann.budgetmanager.server.persistenscouchdb.core.CouchDbConnectorFactory;
import de.pfann.budgetmanager.server.persistenscouchdb.dao.CDBEntryDaoFactory;
import de.pfann.budgetmanager.server.persistenscouchdb.dao.CDBStandingOrderDaoFactory;
import de.pfann.budgetmanager.server.persistenscouchdb.dao.CDBUserDaoFactory;
import de.pfann.budgetmanager.server.persistenscouchdb.facade.CDBAccountFacade;
import de.pfann.budgetmanager.server.persistenscouchdb.facade.CDBEntryFacade;
import de.pfann.budgetmanager.server.persistenscouchdb.facade.CDBStandingOrderFacade;
import de.pfann.budgetmanager.server.persistenscouchdb.facade.CDBUserFacade;
import de.pfann.budgetmanager.server.persistenscouchdb.util.CouchDBUtil;
import org.ektorp.CouchDbInstance;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.ObjectMapperFactory;
import org.ektorp.impl.StdCouchDbInstance;
import org.ektorp.impl.StdObjectMapperFactory;

import java.io.File;
import java.net.MalformedURLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

public class StartCouchDbWriter {


    public static void main(String[] args) throws MalformedURLException {
        Gson json = new Gson();

        ConfigurationProvider configurationProvider = new ConfigurationProvider("cdbwriter_local.properties");
        Properties properties = configurationProvider.getProperties();

        String couchPrefix = properties.getProperty("couchdb.prefix");
        String port = properties.getProperty("couchdb.port");
        String host = properties.getProperty("couchdb.host");
        String url = host + ":" +  port;
        String username = properties.getProperty("couchdb.user");
        String password = properties.getProperty("couchdb.pw");
        String fileInputRootDirectory = properties.getProperty("file.input.directory");

        System.out.println(couchPrefix);
        System.out.println(port);
        System.out.println(host);
        System.out.println(username);
        System.out.println(password);
        System.out.println(fileInputRootDirectory);

        StdHttpClient.Builder httpClientBuilder = new StdHttpClient.Builder();
        httpClientBuilder
                .url(url)
                .username(username)
                .password(password);
        HttpClient httpClient = httpClientBuilder.build();

        CouchDbInstance dbInstance = new StdCouchDbInstance(httpClient);
        ObjectMapperFactory objectMapperFactory = new StdObjectMapperFactory();
        CouchDbConnectorFactory couchDbConnectorFactory = new CouchDbConnectorFactory(dbInstance,couchPrefix,objectMapperFactory);
        CouchDBUtil couchDBUtil = new CouchDBUtil(httpClient);

        couchDBUtil.deleteDatabases(couchPrefix);

        CDBUserDaoFactory userDaoFactory = new CDBUserDaoFactory(couchDbConnectorFactory);
        CDBEntryDaoFactory entryDaoFactory = new CDBEntryDaoFactory(couchDbConnectorFactory);
        CDBStandingOrderDaoFactory standingOrderDaoFactory = new CDBStandingOrderDaoFactory(couchDbConnectorFactory);

        CDBUserFacade userFacade = new CDBUserFacade(userDaoFactory);
        AccountFacade accountFacade = new CDBAccountFacade(userDaoFactory.createDao());
        EntryFacade entryFacade = new CDBEntryFacade(entryDaoFactory);
        StandingOrderFacade standingOrderFacade = new CDBStandingOrderFacade(standingOrderDaoFactory);


        List<File> userFolders = FileUtil.listDirectories(fileInputRootDirectory);


        for(File userFolder : userFolders) {

            System.out.println("#### Process " + userFolder.getName());

            String jsonUserFile = FileUtil.readContent(new File(userFolder.getAbsolutePath() + File.separator + "user.json"));
            User user = json.fromJson(jsonUserFile, User.class);
            userFacade.createNewUser(user);
            userFacade.activateUser(user);

            List<Account> accounts = user.getKontos();
            List<Account> foreignAccounts = user.getForeignKontos();


            for(Account account : accounts) {
                accountFacade.addAccount(user.getName(), account);


                List<File> files = FileUtil.listFiles(userFolder.getAbsolutePath() + File.separator + account.getName());
                List<File> entryFiles = new LinkedList<>();
                List<File> standingOrderFiles = new LinkedList<>();


                for(File file : files) {

                    if(file.getName().contains("entry")) {
                        entryFiles.add(file);
                    }

                    if(file.getName().contains("standingorder")) {
                        standingOrderFiles.add(file);
                    }

                }


                /**
                 * entries
                 */

                List<Entry> collectedEntries = new LinkedList<>();

                for(File entryFile : entryFiles) {
                    String content = FileUtil.readContent(entryFile);
                    List<Entry> entries = json.fromJson(content, new TypeToken<List<Entry>>(){}.getType());
                    collectedEntries.addAll(entries);
                }

                for(Entry entry : collectedEntries) {
                    Entry newEntry = new Entry(entry.getHash(), entry.getUsername(), entry.getCreatedAt(), entry.getData());
                    entryFacade.save(account,newEntry);
                }

                /**
                 * standingorders
                 */

                List<StandingOrder> collectedStandingOrders = new LinkedList<>();

                for(File standingOrdersfile : standingOrderFiles) {

                    String content = FileUtil.readContent(standingOrdersfile);
                    List<StandingOrder> standingOrders = json.fromJson(content, new TypeToken<List<StandingOrder>>(){}.getType());
                    collectedStandingOrders.addAll(standingOrders);
                }

                for(StandingOrder standingOrder : collectedStandingOrders) {
                    StandingOrder newStandingOrder = new StandingOrder(standingOrder.getHash(), standingOrder.getUsername(), standingOrder.getData());
                    standingOrderFacade.persist(account,newStandingOrder);
                }

            }

            for(Account account : foreignAccounts) {
                //accountFacade.addAccount(user.getName(), account);
            }

            System.out.println("#### DONE ####");

        }


    }

}
