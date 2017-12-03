package operations;

import data_file.ColumnNames;
import data_file.ErrorWriter;
import data_file.InputFile;
import data_file.Record;
import tables.Experiment;
import tables.Sample;
import tables.Spectrometer;

import javax.persistence.EntityManager;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/**
 * Loads the data from the input file and stores it in the DB.
 *
 * @author 150009974
 * @version 1.0
 */
public class StoreData {

    private static EntityManager entityManager;

    private static String readFileName () {

        System.out.println ("Enter file name to read (type only the name, without the .csv extension ):");

        Scanner in = new Scanner ( System.in );
        String name = null;

        boolean success = false;
        while ( !success ) {

            name = in.nextLine ();

            try {
                FileReader reader = new FileReader ( name + ".csv" );
                success = true;
            }
            catch ( FileNotFoundException e ) {
                System.out.println ("Input file was not found.");
                System.out.println ("Please, try again.");
            }
        }

        return name;

    }

    private static void setup () {

        String fileName = readFileName ();
        InputFile.setName ( fileName );
        ErrorWriter.openFile ();
        InputFile.open ();
        ColumnNames.defineColumns ( InputFile.getLine () );
        System.out.println ("Attributes found and defined!");

        entityManager = EntityManagerDefinition.emForCreation ();

        entityManager.getTransaction ().begin ();

        System.out.println ("Working, might take a few minutes...");

    }

    private static void cleanUp () {

        entityManager.getTransaction ().commit ();
        InputFile.close ();
        ErrorWriter.closeFile ();

    }

    public static void main ( String[] args ) {

        setup ();

        String line = InputFile.getLine ();
        while ( line != null  ){

            Record r = new Record ( line );//skips a record

            Experiment experimentToSave = r.getExperiment ();
            Experiment experimentSearchResult;
            experimentSearchResult = entityManager.find ( Experiment.class, experimentToSave.getExperimentId () );
            //check to see if the experiment is already in the table
            if ( experimentSearchResult == null)
                entityManager.persist ( experimentToSave );
            //if it is found, make sure that both objects are pointing to the same data
            else experimentToSave = experimentSearchResult;

            Spectrometer spectrometerToSave = r.getSpectrometer ();
            Spectrometer spectrometerSearchResult;
            spectrometerSearchResult = entityManager.find ( Spectrometer.class, spectrometerToSave.getSpectrometerId () );
            //check to see if the spectrometer is already in the table;
            if ( spectrometerSearchResult == null )
                entityManager.persist ( spectrometerToSave );
            //if it is found, make sure that both objects are pointing to the same data
            else spectrometerToSave = spectrometerSearchResult;

            Sample sampleToSave = r.getSample ();
            sampleToSave.setExp ( experimentToSave );
            sampleToSave.setSpec ( spectrometerToSave );
            Sample sampleSearchResult;
            sampleSearchResult = entityManager.find ( Sample.class, sampleToSave.getSampleId () );
            //check to see if the sample is already in the table
            if ( sampleSearchResult == null )
                entityManager.persist ( sampleToSave );

            line = InputFile.getLine ();

        }

        cleanUp ();

    }
}
