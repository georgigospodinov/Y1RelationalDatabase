package initializations;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * copied from studres (catch instead of throw exception)
 *
 * This will read the database connection parameters from a file named
 * database.properties and build the database connection URL
 *
 * @author shyam.reyal
 */
public class ReadDBProperties {

    //Input file containing database connection properties
    private static final String PROP_FILE_NAME = "database.properties";

    public static String[] getProperties () {
        /*
         * Load the information in the file into a Properties object
         * This is an example of reading semi-structured data using an object
         * model
         */
        FileInputStream propInputStream;
        String[] infos = new String[3];

        try {

            propInputStream = new FileInputStream ( PROP_FILE_NAME );
            Properties properties = new Properties ();
            properties.load ( propInputStream );
            propInputStream.close ();

            String host = properties.getProperty ( "host" );
            String port = properties.getProperty ( "port" );
            String username = properties.getProperty ( "username" );
            String password = properties.getProperty ( "password" );
            String db = properties.getProperty ( "db" );

            /*
             * This will build the database URL
             */

            //noinspection StringBufferReplaceableByString
            StringBuilder builder = new StringBuilder ();
            builder.append ( "jdbc:mysql://" );
            builder.append ( host );
            builder.append ( ":" );
            builder.append ( port );
            builder.append ( "/" );
            builder.append ( db );
            String URL = builder.toString ();

            infos[0] = URL;
            infos[1] = username;
            infos[2] = password;
        }
        catch ( FileNotFoundException e ) {
            System.out.println ("Settings file was not found.");
            System.out.println ("Please, make sure that the file is named \"database.properties\"");
            System.out.println ("and that it's in the project directory.");
            System.out.println ("   -ReadDBProperties.getProperties()");
        }
        catch ( IOException e ) {
            System.out.println ( "Failed to access the setup file." );
            System.out.println ("   -ReadDBProperties.getProperties()");
        }

        return infos;
    }
}
