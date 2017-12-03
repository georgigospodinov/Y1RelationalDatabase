package tables;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Represents a Spectrometer that is used when a Sample is tested.
 * Each Sample is associated with one Spectrometer.
 *
 * @author 150009974
 * @version 1.0
 */
@Entity
public class Spectrometer {

    @Id
    private int spectrometerId;
    private String spectrometerName;
    private int spectrometerCapacity;

    public Spectrometer () {
    }

    public Spectrometer ( String spectrometerId ) {
        try {
            this.spectrometerId = Integer.parseInt ( spectrometerId );
        }
        catch ( NumberFormatException e ) {
            //reported by Record class
        }
    }

    public int getSpectrometerId () {
        return spectrometerId;
    }

    public String getSpectrometerName () {
        return spectrometerName;
    }

    public void setSpectrometerName ( String spectrometerName ) {
        this.spectrometerName = spectrometerName;
    }

    public int getSpectrometerCapacity () {
        return spectrometerCapacity;
    }

    public void setSpectrometerCapacity ( String spectrometerCapacity ) {
        try {
            this.spectrometerCapacity = Integer.parseInt ( spectrometerCapacity );
        }
        catch ( NumberFormatException e ) {
            //reported by Record class
        }
    }

    @Override
    public String toString () {

        String info = "";
        info += "Id: " + spectrometerId + "\n";
        info += "Name: " + spectrometerName + "\n";
        info += "Capacity: " + spectrometerCapacity + "\n";

        return info;

    }
}
