package data_file;

/**
 * like practical 1
 *
 * @author 150009974
 * @version 1.0
 */
public interface CommonConstants {

    //these two constants are used when a column is missing
    int NOT_IN_FILE = -1;
    String NOT_DEFINED = "<not defined>";

    String EMPTY_FIELD = "<empty field>";
    String NOTHING = "";//for default values

    //names of columns in input file
    String SAMPLE_ID = "sample_id";
    String RECORD_SUBMIT_TIME = "record_submit_time";
    String SAMPLE_HOLDER_NUMBER = "sample_holderno";
    String SAMPLE_DURATION = "sample_duration";

    String EXP_ID = "exp_id";
    String EXP_NAME = "exp_name";
    String EXP_DESCRIPTION = "exp_description";

    String SPECTROMETER_ID = "spectrometer_id";
    String SPECTROMETER_NAME = "spectrometer_name";
    String SPECTROMETER_CAPACITY = "spectrometer_capacity";

    String USER_ID = "user_id";
    String GROUP_ABBR = "group_abbr";
    String SOLVENT_ABBR = "solvent_abbr";

}
