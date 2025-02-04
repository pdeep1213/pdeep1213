package com.example.javafx_clinicmanager;

/**
 * The Patient class defines a Patient with a profile and visits.
 * @author Deep Patel, Manan Patel
 */
public class Patient extends Person {
    private Visit visits; //a linked list of visits (completed appt.)

    /**
     * Default constructor
     * @param profile of patient
     * @param visit appointments of the patient
     */
    public Patient(Profile profile,Visit visit){
        super(profile);
        this.visits = visit;
    }

    /**
     * Default constructor
     * @param profile of patient
     */
    public Patient(Profile profile){
        super(profile);
        this.visits = null;
    }


    /**
     * getter for visits of patients
     * @return visits
     */
    public Visit getVisits() {
            return visits;
    }

    /**
     * Helper method to add visit to existing patients
     * @param visit
     */
    public void addVisits(Visit visit){
        if (this.visits == null) {
            // If the visits list is empty, set the new visit as the first visit
            this.visits = visit;
        } else {
            // Traverse to the end of the visits list
            Visit temp = this.visits;
            while (temp.getNext() != null) {
                temp = temp.getNext();
            }
            // Add the new visit at the end
            temp.setNext(visit);
        }
    }

    /**
     * Method to get charge of patients
     * @return integer charge
     */
    public int charge() {
        Visit temp = this.visits;
        int totalCharge = 0;
        // Iterate over the visits and sum the provider rates.
        while (temp != null) {
            Provider provider = (Provider) temp.getAppointment().getProvider();
            totalCharge += provider.rate();
            temp = temp.getNext();
        }
        return totalCharge;
    }

    /**
     * Compares patient to other patient
     * @param other the object to be compared.
     * @return 1 if greater, -1 if smaller, 0 if equal
     */
//    @Override
//    public int compareTo(Patient other) {
//        //return profile.compareTo(other.profile);
//        return 0;
//    }

    /**
     * Check for equality
     * @param obj
     * @return true or false
     */
    @Override
    public boolean equals(Object obj){
        if(obj instanceof Patient other){
            return this.profile.equals(other.getProfile());
        }
        return false;
    }

    /**
     * textual representation of Patient
     * @return String
     */
    @Override
    public String toString() {
        return profile.toString();
    }

}
