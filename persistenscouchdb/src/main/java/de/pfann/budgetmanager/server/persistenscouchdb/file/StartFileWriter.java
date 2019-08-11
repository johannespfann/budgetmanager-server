package de.pfann.budgetmanager.server.persistenscouchdb.file;

import com.google.gson.Gson;
import de.pfann.budgetmanager.server.common.configuration.ConfigurationProvider;
import de.pfann.budgetmanager.server.common.facade.EntryFacade;
import de.pfann.budgetmanager.server.common.facade.StandingOrderFacade;
import de.pfann.budgetmanager.server.common.facade.UserFacade;
import de.pfann.budgetmanager.server.model.Account;
import de.pfann.budgetmanager.server.model.Entry;
import de.pfann.budgetmanager.server.model.StandingOrder;
import de.pfann.budgetmanager.server.model.User;
import de.pfann.budgetmanager.server.persistenscouchdb.core.CouchDbConnectorFactory;
import de.pfann.budgetmanager.server.persistenscouchdb.dao.CDBEntryDaoFactory;
import de.pfann.budgetmanager.server.persistenscouchdb.dao.CDBStandingOrderDaoFactory;
import de.pfann.budgetmanager.server.persistenscouchdb.dao.CDBUserDaoFactory;
import de.pfann.budgetmanager.server.persistenscouchdb.facade.CDBEntryFacade;
import de.pfann.budgetmanager.server.persistenscouchdb.facade.CDBStandingOrderFacade;
import de.pfann.budgetmanager.server.persistenscouchdb.facade.CDBUserFacade;
import org.ektorp.CouchDbInstance;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.ObjectMapperFactory;
import org.ektorp.impl.StdCouchDbInstance;
import org.ektorp.impl.StdObjectMapperFactory;

import java.net.MalformedURLException;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

public class StartFileWriter {

    public static void main(String[] args) throws MalformedURLException {

        ConfigurationProvider configurationProvider = new ConfigurationProvider("filewriter.properties");
        Properties properties = configurationProvider.getProperties();

        StdHttpClient.Builder httpClientBuilder = new StdHttpClient.Builder();
        String couchPrefix = properties.getProperty("couchdb.prefix");
        String port = properties.getProperty("couchdb.port");
        String host = properties.getProperty("couchdb.host");
        String url = host + ":" +  port;
        String username = properties.getProperty("couchdb.user");
        String password = properties.getProperty("couchdb.pw");
        String fileOutputDirectory = properties.getProperty("file.output.directory");

        System.out.println(couchPrefix);
        System.out.println(port);
        System.out.println(host);
        System.out.println(username);
        System.out.println(password);

        System.out.println(fileOutputDirectory);
        httpClientBuilder.url(url)
                .username(username)
                .password(password);

        CouchDbInstance dbInstance = new StdCouchDbInstance(httpClientBuilder.build());
        ObjectMapperFactory objectMapperFactory = new StdObjectMapperFactory();
        CouchDbConnectorFactory couchDbConnectorFactory = new CouchDbConnectorFactory(dbInstance, couchPrefix, objectMapperFactory);

        CDBUserDaoFactory userDaoFactory = new CDBUserDaoFactory(couchDbConnectorFactory);
        CDBEntryDaoFactory entryDaoFactory = new CDBEntryDaoFactory(couchDbConnectorFactory);
        CDBStandingOrderDaoFactory standingOrderDaoFactory = new CDBStandingOrderDaoFactory(couchDbConnectorFactory);

        UserFacade userFacade = new CDBUserFacade(userDaoFactory);
        EntryFacade entryFacade = new CDBEntryFacade(entryDaoFactory);
        StandingOrderFacade standingOrderFacade = new CDBStandingOrderFacade(standingOrderDaoFactory);

        Gson gson = new Gson();

        /**
         * create output directory
         */

        String basePath = fileOutputDirectory;

        LocalDateTime localDateTime = LocalDateTime.now();
        String baseDirectoryName = localDateTime.getDayOfMonth() + "_" + localDateTime.getMonth().getValue() + "_" + localDateTime.getYear() + "_output\\";

        String outputDirectoryPath = basePath + baseDirectoryName;
        FileUtil.createDirectory(outputDirectoryPath);
        System.out.println("create output directory");

        /**
         * create user
         */

        List<User> users = new LinkedList<>();
        users = userFacade.getAllUser();

        for (User user : users) {

            System.out.println("#########" + user.getName());

            /**
             * create directory of user
             */

            String userDirectory = outputDirectoryPath  + user.getName() + "\\";
            FileUtil.createDirectory(userDirectory);

            /**
             * write user-json
             */

            String userInput = gson.toJson(user);
            System.out.println("Create user-directory: " + userDirectory);
            FileUtil.createFile(userDirectory + "user.json", userInput);

            for (Account account : user.getKontos()) {

                System.out.println("###" + account.getName());

                /**
                 * create account directory
                 */

                String accountDirectory = userDirectory + account.getName() + "\\";
                FileUtil.createDirectory(accountDirectory);

                /**
                 * write account-json
                 */

                String accountInput = gson.toJson(account);
                FileUtil.createFile(accountDirectory + "account.json", accountInput);

                /**
                 * write standingorder-json
                 */

                List<StandingOrder> standingOrders = standingOrderFacade.getStandingOrders(account);
                System.out.println("- StandingOrders:");
                String standingOrdersContent = gson.toJson(standingOrders);
                FileUtil.createFile(accountDirectory + "standingorder.json", standingOrdersContent);

                /**
                 * write entries in seperated packages
                 */

                List<Entry> entries = entryFacade.getEntries(account);
                System.out.println("- Entries: " + entries.size());
                PackageBundler packageBundler = new PackageBundler();
                packageBundler.add(entries);
                List<EntryPackage> entryPackages = packageBundler.getEntryPackages();

                for (EntryPackage singlePackage : entryPackages) {
                    String fileName = "entry-" + singlePackage.getLocalDateTime().getYear() + "-" + singlePackage.getLocalDateTime().getMonth().getValue() + ".json";
                    String entryJson = gson.toJson(singlePackage.getEntries());
                    FileUtil.createFile(accountDirectory + "\\" + fileName, entryJson);
                }
            }
        }
    }
}
