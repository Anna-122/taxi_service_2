package edu.goncharova.configuration;


import edu.goncharova.utils.ConfigReader;

import java.util.Properties;

public class DatabaseTestConfig {
    private static final String CONFIG_FILE_NAME_TEST = "application.properties";

    private final static String DATABASE_URL_PROPERTY_NAME_TEST = "database.url.test";
    private final static String DATABASE_USER_PROPERTY_NAME_TEST = "database.username.test";
    private final static String DATABASE_PASSWORD_PROPERTY_NAME_TEST = "database.password.test";

    private static final Properties DATABASE_CONFIG_TEST;
    public static final String DATABASE_URL_TEST;
    public static final String DATABASE_USER_TEST;
    public static final String DATABASE_PASSWORD_TEST;

    static {
        DATABASE_CONFIG_TEST = ConfigReader.readConfig(CONFIG_FILE_NAME_TEST);
        DATABASE_URL_TEST = System.getProperty(DATABASE_URL_PROPERTY_NAME_TEST, DATABASE_CONFIG_TEST.getProperty(DATABASE_URL_PROPERTY_NAME_TEST));
        DATABASE_USER_TEST = System.getProperty(DATABASE_USER_PROPERTY_NAME_TEST, DATABASE_CONFIG_TEST.getProperty(DATABASE_USER_PROPERTY_NAME_TEST));
        DATABASE_PASSWORD_TEST = System.getProperty(DATABASE_PASSWORD_PROPERTY_NAME_TEST, DATABASE_CONFIG_TEST.getProperty(DATABASE_PASSWORD_PROPERTY_NAME_TEST));
    }

    private DatabaseTestConfig() {
    }
}