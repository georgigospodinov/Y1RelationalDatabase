package operations;

import tables.Sample;
import tables.Spectrometer;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Scanner;

/**
 * Retrieve and print
 * sample id, solvent abbreviation and experiment name
 * of all samples run on a specific spectrometer.
 *
 * @author 150009974
 * @version 1.2
 */
public class SpectrometerCriteria {

    private static EntityManager entityManager;

    private static int readSpecName () {

        System.out.println ("Enter the name of the spectrometer for which you would like to retrieve information.");
        System.out.println ("(eg. Alec)");

        Scanner in = new Scanner ( System.in );
        String name;
        int specId = -1;
        boolean success = false;

        Query findingSpectrometer = entityManager.createQuery ( "SELECT spec FROM Spectrometer spec" );
        List <Spectrometer> spectrometers = findingSpectrometer.getResultList ();
        int numberOfSpectrometers = spectrometers.size ();
        int counter;

        while ( !success ) {

            counter = 0;

            name = in.nextLine ();
            //find if there is such a spectrometer
            for ( Spectrometer spec : spectrometers ) {
                if ( spec.getSpectrometerName ().equals ( name ) ) {
                    specId = spec.getSpectrometerId ();
                    success = true;
                    break;
                }
                else counter++;
            }

            if ( counter == numberOfSpectrometers )//if the spectrometer was not found
                System.out.println ("No such spectrometer was found, please try again.");

        }

        System.out.println ("Spectrometer found!");

        return specId;
    }

    public static void main ( String[] args ) {

        entityManager = EntityManagerDefinition.emForValidation ();

        int theID = readSpecName ();

        Query query = entityManager.createQuery ( "SELECT s FROM Sample s WHERE spec.spectrometerId="+theID );
        List<Sample> samples = query.getResultList ();

        for ( Sample s : samples ) {

            System.out.println ("Sample id: " + s.getSampleId ());
            System.out.println ("Solvent abbreviation: " + s.getSolventAbbr ());
            System.out.println ("Experiment name: " + s.getExp ().getExpName () + "\n");

        }
    }
}
