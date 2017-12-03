package operations;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Scanner;

/**
 * Retrieves and prints all records from a chosen table in the database.
 *
 * @author 150009974
 * @version 1.4
 */
public class RetrieveAll {

    private static String readTableName () {

        System.out.println ("Enter the name of the table from which you would like to retrieve data:");
        Scanner in = new Scanner ( System.in );
        String tableName = null;
        boolean success = false;

        while ( !success ) {

            tableName = in.nextLine ();

            if ( tableName.equals ( "Sample" ) || tableName.equals ( "Experiment" ) || tableName.equals ( "Spectrometer" ) ) {
                System.out.println ("Table found!");
                success = true;
            }

            else System.out.println ("Table name not recognized, please try again.");

        }


        return tableName;
    }

    public static void main ( String[] args ) {

        EntityManager entityManager = EntityManagerDefinition.emForValidation ();

        String table = readTableName ();
        System.out.println ("Accessing tuples...");
        Query query = entityManager.createQuery ( "SELECT o FROM "+table+" o" );
        List<Object> tuples = query.getResultList ();

        System.out.println ( table + "s:" );
        for ( Object tuple : tuples )
            System.out.println ( tuple.toString () );

    }
}
