package tables;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Represents an Experiment.
 * Each Sample is associated with one Experiment.
 *
 * @author 150009974
 * @version 1.0
 */
@Entity
public class Experiment {

    @Id
    private int experimentId;
    private String expName;
    private String expDescription;

    public Experiment () {
    }

    public Experiment ( String experimentId ) {
        try {
            this.experimentId = Integer.parseInt ( experimentId );
        }
        catch ( NumberFormatException e ) {
            //reported by Record class
        }
    }

    public int getExperimentId () {
        return experimentId;
    }

    public String getExpName () {
        return expName;
    }
    public void setExpName ( String expName ) {
        this.expName = expName;
    }

    public String getExpDescription () {
        return expDescription;
    }
    public void setExpDescription ( String expDescription ) {
        this.expDescription = expDescription;
    }

    @Override
    public String toString () {

        String info = "";
        info += "Id: " + experimentId + "\n";
        info += "Name: " + expName + "\n";
        info += "Description: " + expDescription + "\n";

        return info;

    }
}
