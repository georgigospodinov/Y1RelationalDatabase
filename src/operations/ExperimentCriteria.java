package operations;

import tables.Experiment;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Scanner;

/**
 * Retrieve and print
 * the number of Samples run
 * using a specific experiment.
 *
 * @author 150009974
 * @version 1.0
 */
public class ExperimentCriteria {

    private static EntityManager entityManager;

    private static int readExperimentName () {

        System.out.println ("Enter the name of the experiment for which you would like to retrieve information.");
        System.out.println ("(eg. proton.a.and)");

        Scanner in = new Scanner ( System.in );
        String name;
        int expId = -1;
        boolean success = false;

        Query findingExperiment = entityManager.createQuery ( "SELECT e FROM Experiment e" );
        List <Experiment> experiments = findingExperiment.getResultList ();
        int numberOfExperiments = experiments.size ();
        int counter;

        while ( !success ) {

            name = in.nextLine ();
            counter = 0;

            for ( Experiment e : experiments ) {
                if ( e.getExpName ().equals ( name ) ) {
                    expId = e.getExperimentId ();
                    success = true;
                    break;
                }

                else counter++;
            }

            if ( counter == numberOfExperiments )//if the experiment was not found
                System.out.println ("No such experiment found, please try again.");

        }

        System.out.println ("Experiment found.");

        return expId;

    }

    public static void main ( String[] args ) {

        entityManager = EntityManagerDefinition.emForValidation ();

        int expId = readExperimentName ();
        Query query = entityManager.createQuery ( "SELECT s FROM Sample s WHERE exp.experimentId="+ expId );
        //size of the query result
        System.out.println ("\nNumber of samples run on this experiment: "+query.getResultList ().size ());

    }
}
