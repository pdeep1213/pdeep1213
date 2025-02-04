package com.example.javafx_clinicmanager;

/**
 * This is the Doctor class that hold Doctor info, it is an extension of Provider class, it holds the speciality and npi number of the Docotor
 * @author Deep Patel, Manan Patel
 */
public class Doctor extends Provider {

    private Specialty specialty;//encapsulate the rate per visit based on specialty
    private String npi; //National Provider Identification unique to the doctor

    /**
     * Default Constructor
     * @param specialty Specialty of the Doctor
     * @param npi NPI number of Doctor
     * @param location Location of the Doctor
     * @param profile Profile of the Doctor
     */
    public Doctor(Specialty specialty, String npi, Location location, Profile profile){
        super(location,profile);
        this.specialty = specialty;
        this.npi = npi;
    }

    /**
     * Getter for Specialty of Doctor
     * @return Specialty
     */
    public Specialty getSpecialty() {
        return specialty;
    }

    /**
     * Getter for NPI number
     * @return int NPI
     */
    public String getNpi() {
        return npi;
    }

    /**
     * Helper method to get the rate per visit of the Doctor
     * @return int rate
     */
    @Override
    public int rate() {
        switch (this.specialty) {
            case FAMILY -> {
                return 250;
            }
            case ALLERGIST -> {
                return 350;
            }
            case PEDIATRICIAN -> {
                return 300;
            }
            default -> {
                return 0;
            }
        }
    }

    /**
     * A textual representation of the Doctor object
     * @return
     */
    @Override
    public String toString(){
        return "[" + this.profile.toString() + ", " + this.getLocation().toString() + "][" + this.specialty + ", #" + this.npi + "]";
    }
}
