package data_file;

/**
 * Finds and saves the names and indexes of the columns in the input file.
 *
 * @author 150009974
 * @version 1.0
 */
public class ColumnNames implements CommonConstants {

    private static int numberOfFields = 13;
    private static String[] data;
    private static boolean lost = true;

    //for Sample class
    private static int sampleIdIndex;
    private static int recordSubmitTimeIndex;
    private static int sampleHolderNumberIndex;
    private static int sampleDurationIndex;
    private static int userIdIndex;
    private static int groupAbbrIndex;
    private static int solventAbbrIndex;

    //for Experiment class
    private static int expIdIndex;
    private static int expNameIndex;
    private static int expDescriptionIndex;

    //for Spectrometer class
    private static int spectrometerIdIndex;
    private static int spectrometerNameIndex;
    private static int spectrometerCapacityIndex;

    //checks to see if at least one column was found
    //otherwise it's probably bad format.
    private static void reportLost () {

        if ( lost ) {

            String msg = "No columns have been found. ";
            msg += "No output file will be generated.\n";
            msg += "Please, make sure that the input file is correctly formatted.\n";
            ErrorWriter.log ( msg );

        }

    }

    private static int find ( String fieldToFind ) {

        int index = NOT_IN_FILE;

        for ( int i = 0; i < numberOfFields; i++ ) {

            String s = data[i];
            if ( s.equals ( fieldToFind ) ) {

                index = i;
                lost = false;
                break;

            }

        }

        if ( index == NOT_IN_FILE ) {

            String msg = "";

            if ( fieldToFind.equals ( SAMPLE_DURATION ) ) {

                msg += "The \"duration\" column was not found.\n";
                msg += "No durations will be tracked.\n";

            }

            if ( !fieldToFind.equals ( SAMPLE_DURATION ) ) {

                msg += "Column \"" + fieldToFind + "\" was not found in input file.\n";
                msg += "Output file will have \"" + NOT_DEFINED + "\" values.\n";

            }

            msg += "MAY LEAD TO UNINFORMATIVE \"formatted\" file!!!\n";
            msg += "MAY CAUSE FURTHER ERRORS TO BE LOGGED IN THIS FILE!(due to column mismatches)\n";
            ErrorWriter.log ( msg );

        }

        return index;

    }

    //the constructor from practical 1
    public static void defineColumns ( String line ) {

        data = line.split ( "," );
        numberOfFields = data.length;

        sampleIdIndex = find ( SAMPLE_ID );
        recordSubmitTimeIndex = find ( RECORD_SUBMIT_TIME );
        sampleHolderNumberIndex = find ( SAMPLE_HOLDER_NUMBER );
        sampleDurationIndex = find ( SAMPLE_DURATION );

        expIdIndex = find ( EXP_ID );
        expNameIndex = find ( EXP_NAME );
        expDescriptionIndex = find ( EXP_DESCRIPTION );

        userIdIndex = find ( USER_ID );
        groupAbbrIndex = find ( GROUP_ABBR );
        solventAbbrIndex = find ( SOLVENT_ABBR );

        spectrometerIdIndex = find ( SPECTROMETER_ID );
        spectrometerNameIndex = find ( SPECTROMETER_NAME );
        spectrometerCapacityIndex = find ( SPECTROMETER_CAPACITY );

        reportLost ();

    }

    public static int getSpectrometerCapacityIndex () {
        return spectrometerCapacityIndex;
    }
    public static int getSpectrometerNameIndex () {
        return spectrometerNameIndex;
    }
    public static int getSpectrometerIdIndex () {
        return spectrometerIdIndex;
    }

    public static int getExpDescriptionIndex () {
        return expDescriptionIndex;
    }
    public static int getExpNameIndex () {
        return expNameIndex;
    }
    public static int getExpIdIndex () {
        return expIdIndex;
    }

    public static int getSolventAbbrIndex () {
        return solventAbbrIndex;
    }
    public static int getGroupAbbrIndex () {
        return groupAbbrIndex;
    }
    public static int getUserIdIndex () {
        return userIdIndex;
    }

    public static int getSampleDurationIndex () {
        return sampleDurationIndex;
    }
    public static int getSampleHolderNumberIndex () {
        return sampleHolderNumberIndex;
    }
    public static int getRecordSubmitTimeIndex () {
        return recordSubmitTimeIndex;
    }
    public static int getSampleIdIndex () {
        return sampleIdIndex;
    }
}
