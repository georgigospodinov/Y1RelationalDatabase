package data_file;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Used to track exceptions encountered
 * during the execution of the program.
 *
 * @version 1.4
 */
public class ErrorWriter implements CommonConstants {

    private static BufferedWriter errorTracker;

    //opened at the begging of Main.main()
    public static void openFile () {

        String errorFileName = "errors-in-" + InputFile.getName () + ".txt";

        try {
            errorTracker = new BufferedWriter (
                    new FileWriter ( errorFileName ) );

            errorTracker.write ( "Errors encountered when processing \"" +
                    InputFile.getName () + ".csv\":\n\n" );
        }
        catch ( IOException e ) {
            System.out.println ("Failed to open error tracking file.");
            System.out.println ("(file name: " + errorFileName + ")");
            System.out.println ("OR failed to write first statement.");
        }

    }

    //whenever an exception is encountered
    public static void log ( String error ) {

        if ( errorTracker == null )//in case the error tracking file failed to open
            return;

        try {
            errorTracker.write ( error + "\n" );//add a new line for better readability
        }
        catch ( IOException e ) {
            System.out.println ("Failed to write error to error tracking file.");
        }

    }

    //closed in the finally block in Main.main()
    public static void closeFile () {

        if ( errorTracker == null )//in case the error tracking file failed to open
            return;

        log ( "(Single empty fields were not recorded.)" );
        try {
            errorTracker.close ();
        }
        catch ( IOException e ) {
            System.out.println ("Failed to close error tracking file.");
        }
    }

}
