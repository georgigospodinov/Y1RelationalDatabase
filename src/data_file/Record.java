package data_file;

import tables.Experiment;
import tables.Sample;
import tables.Spectrometer;

import java.util.Date;

/**
 * Represents a row in the input file.
 * Generates an instance for each table (Sample, Experiment, Spectrometer).
 * Has elements from my Record class from Practical 1.
 *
 * @author 150009974
 * @version 1.0
 */
public class Record implements CommonConstants {

    //Sample essentials
    private String sampleId = NOTHING;
    private String recordSubmitTime = NOTHING;
    private String sampleHolderNumber = NOTHING;
    private String sampleDuration = NOTHING;
    private boolean durationValid;
    private int durationValue = 0;

    //Experiment essentials
    private String expId = NOTHING;
    private String expName = NOTHING;
    private String expDescription = NOTHING;

    //Sample supplementary
    private String userId = NOTHING;
    private String groupAbbr = NOTHING;
    private String solventAbbr = NOTHING;

    //Spectrometer essentials
    private String spectrometerId = NOTHING;
    private String spectrometerName = NOTHING;
    private String spectrometerCapacity = NOTHING;

    private Sample sample;
    private Experiment experiment;
    private Spectrometer spectrometer;

    private String[] fields;

    private String getFieldAt ( int index ) {

        if ( index == NOT_IN_FILE )
            return NOT_DEFINED;

        else {

            String field = fields[index];

            if ( field.length () == 0 )
                return EMPTY_FIELD;//this way all Strings will have values

            else return field;

        }

    }

    //deal with bad formats
    private void reportBadNumberFormat ( String field, String toCheck ) {

        if ( toCheck.equals ( NOT_DEFINED ) )
            //this error is already logged by ColumnNames
            return;

        try {
            Integer.parseInt ( toCheck );
        }
        catch ( NumberFormatException e ) {

            String msg = "Line: " + InputFile.getLastLineRead () + ", field: " + field + "\n";

            if ( toCheck.equals ( EMPTY_FIELD ) )
                msg += "Field lacks data.\n";

            else msg += "Expected a number but found: \"" + toCheck + "\"\n";

            ErrorWriter.log ( msg );

        }

    }

    //common among sampleDuration error logging
    private String addFormat ( String msg ) {

        msg += "Bad sampleDuration format.\n";
        msg += "Expected format is hh:mm:ss. ";
        msg += "Found \"" + sampleDuration + "\"\n";

        return msg;

    }

    private void handleDuration () {

        //correct sampleDuration format is hh:mm:ss
        String msg = "Line: " + InputFile.getLastLineRead ()+ ", field: " + SAMPLE_DURATION + "\n";

        if ( sampleDuration.equals ( EMPTY_FIELD ) ) {

            msg += "Field lacks data.\n";
            ErrorWriter.log ( msg );

        }

        else if ( sampleDuration.equals ( NOT_DEFINED ) )
            return;//this error is already reported by ErrorWriter

        else if ( sampleDuration.length () != 8 ||
                sampleDuration.charAt ( 2 ) != ':' || sampleDuration.charAt ( 5 ) != ':' ) {

            msg = addFormat ( msg );
            ErrorWriter.log ( msg );

        }

        else {

            String hh = sampleDuration.substring ( 0, 2 );
            String mm = sampleDuration.substring ( 3, 5 );
            String ss = sampleDuration.substring ( 6, 8 );

            int h = 0, m = 0, s = 0;


            try {
                h = Integer.parseInt ( hh );
                m = Integer.parseInt ( mm );
                s = Integer.parseInt ( ss );
                durationValid = true;
            }
            catch ( NumberFormatException e ) {

                msg = addFormat ( msg );
                ErrorWriter.log ( msg );
                durationValid = false;

            }

            // if ( try was successful )
            if ( durationValid )
                //and if the time was valid
                if ( h >= 0 && m >= 0 && m < 60 && s >= 0 && s < 60 )
                    durationValue = ((h * 60) + m) * 60 + s;

        }

    }

    //checks to see if the recordSubmitTime represents a Date
    private void handleSubmitTime () {

        try {
            Date d = new Date ( recordSubmitTime );
        }
        catch ( IllegalArgumentException e ) {
            String msg = "Line: " + InputFile.getLastLineRead () + "\n";
            msg += "Invalid date";
            ErrorWriter.log ( msg );
        }

    }

    private void assignValues () {

        //sample essentials
        sampleId = getFieldAt ( ColumnNames.getSampleIdIndex () );
        reportBadNumberFormat ( SAMPLE_ID, sampleId );

        recordSubmitTime = getFieldAt ( ColumnNames.getRecordSubmitTimeIndex () );
        handleSubmitTime ();

        sampleHolderNumber = getFieldAt ( ColumnNames.getSampleHolderNumberIndex () );
        reportBadNumberFormat ( SAMPLE_HOLDER_NUMBER, sampleHolderNumber );

        sampleDuration = getFieldAt ( ColumnNames.getSampleDurationIndex () );
        handleDuration ();


        //Experiment essentials
        expId = getFieldAt ( ColumnNames.getExpIdIndex () );
        reportBadNumberFormat ( EXP_ID, expId );

        expName = getFieldAt ( ColumnNames.getExpNameIndex () );
        expDescription = getFieldAt ( ColumnNames.getExpDescriptionIndex () );


        //Sample supplementary
        userId = getFieldAt ( ColumnNames.getUserIdIndex () );
        reportBadNumberFormat ( USER_ID, userId );

        groupAbbr = getFieldAt ( ColumnNames.getGroupAbbrIndex () );
        solventAbbr = getFieldAt ( ColumnNames.getSolventAbbrIndex () );

        //Spectrometer essentials
        spectrometerId = getFieldAt ( ColumnNames.getSpectrometerIdIndex () );
        reportBadNumberFormat ( SPECTROMETER_ID, sampleId );

        spectrometerName = getFieldAt ( ColumnNames.getSpectrometerNameIndex () );
        spectrometerCapacity = getFieldAt ( ColumnNames.getSpectrometerCapacityIndex () );
        reportBadNumberFormat ( SPECTROMETER_CAPACITY, spectrometerCapacity );

    }

    public Record ( String line ) {

        fields = line.split ( "," );

        if ( fields.length > 0 ) {//the record is not empty

            assignValues ();
            defineSpectrometer ();
            defineExperiment ();
            defineSample ();

        }

        else {

            String msg = "Bad record data at line " + InputFile.getLastLineRead () + ".\n";
            msg += "Record is ignored.\n";
            ErrorWriter.log ( msg );

        }

    }

    //more like this
    public void defineSample () {

        sample = new Sample ( sampleId );
        sample.setRecordSubmitTime ( recordSubmitTime );
        sample.setSampleHolderNumber ( sampleHolderNumber );
        sample.setDuration ( sampleDuration );
        if ( durationValid )
        sample.setDurationInSeconds ( durationValue );

        sample.setUserId ( userId );
        sample.setGroupAbbr ( groupAbbr );
        sample.setSolventAbbr ( solventAbbr );

    }

    public void defineExperiment () {

        experiment = new Experiment ( expId );
        experiment.setExpName (expName);
        experiment.setExpDescription ( expDescription );

    }

    public void defineSpectrometer () {

        spectrometer = new Spectrometer ( spectrometerId );
        spectrometer.setSpectrometerName ( spectrometerName );
        spectrometer.setSpectrometerCapacity ( spectrometerCapacity );

    }

    public Sample getSample () {
        return sample;
    }

    public Experiment getExperiment () {
        return experiment;
    }

    public Spectrometer getSpectrometer () {
        return spectrometer;
    }
}
