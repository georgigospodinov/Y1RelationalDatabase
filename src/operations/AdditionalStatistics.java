package operations;

import tables.Sample;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Scanner;

/**
 * Retrieve and print
 * number and percentage of samples with a specific group/solvent
 *
 * @author 150009974
 * @version 1.0
 */
public class AdditionalStatistics {

    private static EntityManager entityManager;
    private static List<Sample> samples;

    private static String readColumn () {

        System.out.println ( "Would you like to retrieve statistics for group or solvent?" );

        Scanner in = new Scanner ( System.in );
        String input = null;
        boolean success = false;

        while ( !success ) {

            input = in.nextLine ();

            if ( (!input.equals ( "group" )) && (!input.equals ( "solvent" )) )
                System.out.println ( "Your input is not valid, please enter \'group\' or \'solvent\'" );

            else success = true;
        }

        return input;
    }

    /**
     * @param column - either 'group' or 'solvent' read from the method above
     * @return the specific group/solvent for which statistics need to be generated
     */
    private static String readName ( String column ) {

        boolean lookingForGroup = column.equals ( "group" );
        System.out.print ( "Which " + column + " would you like to view statistics for? (eg. " );
        if ( lookingForGroup )
            System.out.println ( "csjc)" );
        else System.out.println ( "Pyr)" );

        Scanner in = new Scanner ( System.in );
        String name = null;
        boolean success = false;


        while ( !success ) {

            name = in.nextLine ();

            for ( Sample s : samples )
                if ( s.getGroupAbbr ().contains ( name ) || s.getSolventAbbr ().contains ( name ) )
                    success = true;

            if ( !success ) {

                if ( lookingForGroup )
                    System.out.print ( "Group" );
                else System.out.print ( "Solvent" );

                System.out.println ( " not found, please try again." );

            }
        }

        return name;
    }

    public static void main ( String[] args ) {

        entityManager = EntityManagerDefinition.emForValidation ();
        samples = entityManager.createQuery ( "SELECT s FROM Sample s" ).getResultList ();

        String column = readColumn ();
        String name = readName ( column );
        String queryStatement = "SELECT s FROM Sample s WHERE ";

        if ( column.equals ( "group" ) )
            queryStatement += "groupAbbr";

        else queryStatement += "solventAbbr";

        queryStatement += " LIKE \'%" + name + "%\'";

        int numberOfSamplesThatMeetTheCriteria;
        int totalNumberOfSamples = samples.size ();
        List<Sample> meetTheCriteria = entityManager.createQuery ( queryStatement ).getResultList ();
        numberOfSamplesThatMeetTheCriteria = meetTheCriteria.size ();

        int percentage = numberOfSamplesThatMeetTheCriteria * 1000 / totalNumberOfSamples;
        if ( percentage % 10 > 4 )
            percentage += 10;
        percentage /= 10;

        System.out.println ( "Number of samples that contain " + name + " in their " + column + " field: "
                + numberOfSamplesThatMeetTheCriteria + " [" + percentage + "%]" );
        System.out.println ( "Total number of samples in the table: " + totalNumberOfSamples );

    }
}
