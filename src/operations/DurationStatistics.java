package operations;

import tables.Sample;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

/**
 * Used to retrieve
 * shortest, longest and total duration
 * from the Sample table.
 *
 * @author 150009974
 * @version 1.0
 */
public class DurationStatistics {

    //takes duration in secs and returns it in a String hh:mm:ss
    private static String secondsToString ( int durationInSec ) {

        String duration = "";
        int hours, minutes, seconds;

        seconds = durationInSec%60;
        durationInSec /= 60;

        minutes = durationInSec%60;
        durationInSec /= 60;

        hours = durationInSec;

        if ( hours < 10 )
            duration += "0";
        duration += hours + ":";

        if ( minutes < 10 )
            duration += "0";
        duration += minutes + ":";

        if ( seconds < 10 )
            duration += "0";
        duration += seconds;

        return duration;
    }

    public static void main ( String[] args ) {

        EntityManager entityManager = EntityManagerDefinition.emForValidation ();
        Query getSamples = entityManager.createQuery ( "SELECT s FROM Sample s" );
        List<Sample> samples = getSamples.getResultList ();

        //starting search
        int shortestDuration = Integer.MAX_VALUE;
        int longestDuration = 0;
        int totalDuration = 0;

        for ( Sample s : samples ) {

            int currentSampleDuration = s.getDurationInSeconds ();

            if ( currentSampleDuration < shortestDuration )
                shortestDuration = currentSampleDuration;

            if ( currentSampleDuration > longestDuration )
                longestDuration = currentSampleDuration;

            totalDuration += currentSampleDuration;

        }

        System.out.println ( "Duration statistics for current data in DB:" );
        System.out.println ( "Shortest duration: " + secondsToString ( shortestDuration ) );
        System.out.println ( "Longest duration: " + secondsToString ( longestDuration ) );
        System.out.println ( "Total duration: " + secondsToString ( totalDuration ) );

    }
}
