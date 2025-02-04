package com.example.javafx_clinicmanager;
import java.text.DecimalFormat;

/**
 * This the subclass of Provider
 * @author Deep Patel, Manan Patel
 */
public class Technician extends Provider{
    private int ratePerVisit;

    /**
     * Parameterized constructor
     * @param location location of appointment
     * @param profile profile of technician
     * @param ratePerVisit rate of technician
     */
    public Technician(Location location, Profile profile, int ratePerVisit ) {
        super(location, profile);
        this.ratePerVisit = ratePerVisit;
    }

    /**
     * Getter for rate of Technician
     * @return int rate
     */
    @Override
    public int rate() {
        return ratePerVisit;
    }

    /**
     * Textual representation of Technician object
     * @return String
     */
    @Override
    public String toString(){
        DecimalFormat df = new DecimalFormat("0.00");
        String rate = df.format(ratePerVisit);

        return "[" + this.profile.toString() + ", " + this.getLocation().toString() + "][rate: $" + rate + "]";
    }
}
