package initializations;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * copied from studres, uses ReadDBProperties
 */
/*
 * This class defines everything that's required for JPA to connect to your MySQL service
 * For e.g. Username, password, URL, etc. is all captured here as properties
 */
public class DefineProperties {

    private static String URL;
    private static String username;
    private static String password;

    public static void setup () {

        String[] infos = ReadDBProperties.getProperties ();
        URL = infos[0];
        username = infos[1];
        password = infos[2];

        Logger.getLogger ( "org.hibernate" ).setLevel ( Level.OFF );
    }

    //use ONLY when (creating AND adding) data
    public static Map<String, String> getPropertiesForTableAutoCreation () {

        //causes any existing tables with names as specified in persistence.xml to be dropped
        return getProperties ( "create" );
    }

    //use when reading data
    public static Map<String, String> getPropertiesForTableValidation () {

        return getProperties ( "validate" );
    }

    /*
     * The properties Map contains all required parameters to build an EntityManager
     * See EntityManager being created in E.g.Store.java
     * Read this information from database.properties and populate it
     */
    private static Map<String, String> getProperties ( String hibernate_option ) {

        Map<String, String> properties = new HashMap<String, String> ();
        properties.put ( "hibernate.hbm2ddl.auto", hibernate_option );

        // the driver - this needs to be part of the project's classpath
        // see: https://studres.cs.st-andrews.ac.uk/CS1003/Examples/W06-1-JDBC/READ-ME.txt
        properties.put ( "javax.persistence.jdbc.driver", "com.mysql.jdbc.Driver" );

        properties.put ( "javax.persistence.jdbc.url", URL );
        properties.put ( "javax.persistence.jdbc.user", username );
        properties.put ( "javax.persistence.jdbc.password", password );

        return properties;
    }
}
