package de.pfann.budgetmanager.server.common.configuration;

import java.io.*;
import java.util.Properties;

public class ConfigurationProvider {

    private String path;

    public ConfigurationProvider(String aPath) {
        path = aPath;
    }

    public Properties getProperties() {
        File propertiesFile = new File(path);
        Properties properties = new Properties();

        BufferedInputStream bis = null;
        try {
            bis = new BufferedInputStream(new FileInputStream(propertiesFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            properties.load(bis);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return properties;
    }
}
