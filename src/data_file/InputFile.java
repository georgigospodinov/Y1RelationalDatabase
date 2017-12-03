package data_file;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Used for reading the input data file.
 * Has multiple small try-catch blocks in the methods.
 *
 * @author 150009974
 * @version 1.0
 */
public class InputFile {

    private static BufferedReader inFile = null;
    private static String name = null;
    private static int lastLineRead = 0;

    public static void setName ( String fileName ) {
        name = fileName;
    }
    public static void open (  ) {

        try {
            inFile = new BufferedReader ( new FileReader ( name + ".csv" ) );
            System.out.println ("Input file successfully opened.");
        }
        catch ( FileNotFoundException e ) {
            System.out.println ( "Could not open input file." );
            String msg = "Input file was not found.";
            ErrorWriter.log ( msg );
        }

    }

    public static String getLine () {

        String line = null;

        try {
            line = inFile.readLine ();
            lastLineRead++;
        }
        catch ( IOException e ) {
            System.out.println ( "Reading from the file failed." );

            String msg = "Failed to read from file.";
            msg += "Last line read is: " + lastLineRead;
            ErrorWriter.log ( msg );
        }

        return line;
    }

    public static void close () {

        try {
            inFile.close ();
        }
        catch ( IOException e ) {
            System.out.println ( "Could not close input file." );

            String msg = "Input file failed to close";
            ErrorWriter.log ( msg );
        }

    }

    public static String getName () {
        return name;
    }
    public static int getLastLineRead () {
        return lastLineRead;
    }

}
