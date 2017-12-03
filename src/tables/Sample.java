package tables;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;

/**
 * Represents a Sample from the input file.
 *
 * @author 15009974
 * @version 1.2
 */
@Entity
public class Sample {

    @Id
    private int sampleId;
    private Date recordSubmitTime;
    private int sampleHolderNumber;
    private String duration;
    private int durationInSeconds;

    private int userId;
    private String groupAbbr;
    private String solventAbbr;

    @ManyToOne
    private Spectrometer spec;
    @ManyToOne
    private Experiment exp;

    public Sample () {
    }

    public Sample ( String sampleId ) {
        try {
            this.sampleId = Integer.parseInt ( sampleId );
        }
        catch ( NumberFormatException e ) {
            //reported by Record class
        }
    }

    public int getSampleId () {
        return sampleId;
    }

    public void setSampleId ( int sampleId ) {
        this.sampleId = sampleId;
    }

    public Date getRecordSubmitTime () {
        return recordSubmitTime;
    }

    public void setRecordSubmitTime ( String recordSubmitTime ) {
        Date rst;
        try {
            rst = new Date ( recordSubmitTime );
            this.recordSubmitTime = rst;
        }
        catch ( IllegalArgumentException e ) {
            //reported by the Record class
            this.recordSubmitTime = null;
        }
    }

    public int getSampleHolderNumber () {
        return sampleHolderNumber;
    }

    public void setSampleHolderNumber ( String sampleHolderNumber ) {
        try {
            this.sampleHolderNumber = Integer.parseInt ( sampleHolderNumber );
        }
        catch ( NumberFormatException e ) {
            //reported by the Record class
        }
    }

    public String getDuration () {
        return duration;
    }

    public void setDuration ( String sampleDuration ) {
        this.duration = sampleDuration;
    }

    public int getUserId () {
        return userId;
    }

    public void setUserId ( String userId ) {
        try {
            this.userId = Integer.parseInt ( userId );
        }
        catch ( NumberFormatException e ) {
            //reported by the Record class
        }
    }

    public String getGroupAbbr () {
        return groupAbbr;
    }

    public void setGroupAbbr ( String groupAbbr ) {
        this.groupAbbr = groupAbbr;
    }

    public String getSolventAbbr () {
        return solventAbbr;
    }

    public void setSolventAbbr ( String solventAbbr ) {
        this.solventAbbr = solventAbbr;
    }

    public Spectrometer getSpec () {
        return spec;
    }

    public void setSpec ( Spectrometer usedSpectrometer ) {
        this.spec = usedSpectrometer;
    }

    public Experiment getExp () {
        return exp;
    }

    public void setExp ( Experiment associatedExperiment ) {
        this.exp = associatedExperiment;
    }

    public int getDurationInSeconds () {
        return durationInSeconds;
    }

    public void setDurationInSeconds ( int durationInSeconds ) {
        this.durationInSeconds = durationInSeconds;
    }

    @Override
    public String toString () {

        String info = "";
        info += "Sample id: " + sampleId + "\n";
        info += "Submit time: " + recordSubmitTime.toString () + "\n";
        info += "Holder number: " + sampleHolderNumber + "\n";
        info += "Duration: " + duration + "\n";
        info += "Duration in seconds: " + durationInSeconds + "\n";
        info += "User id: " + userId + "\n";
        info += "Group abbreviation: " + groupAbbr + "\n";
        info += "Solvent abbreviation: " + solventAbbr + "\n";
        info += "Used spectrometer id: " + spec.getSpectrometerId () + "\n";
        info += "Associated experiment id: " + exp.getExperimentId () + "\n";
        return info;

    }

}
