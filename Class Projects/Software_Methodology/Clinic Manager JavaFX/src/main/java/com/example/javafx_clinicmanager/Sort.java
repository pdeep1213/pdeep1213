package com.example.javafx_clinicmanager;


/**
 * This class does all the sorting for list of appointment, providers, and patients.
 * @author Deep Patel,Manan Patel
 */
public class Sort {

    /**
     * Sorts a list of appointment based on given key
     * @param list list of appointment to sort
     * @param key key to sort by, "D" for date, "P" for provider, "C" for county, "T" for timeslot, "p" patient
     */
    public static void appointment(List<Appointment> list, char key){
        for(int i=0; i<list.size(); i++){
            for(int j=0; j<list.size()-1-i; j++){
                Appointment a = list.get(j);
                Appointment b = list.get(j+1);

                if(swapNeed(a, b, key)){
                    list.swap(j, j+1);
                }
            }
        }
    }

    /**
     * Sort a list of provider in ascending order
     * @param list list of provider to sort
     */
    public static void provider(List<Provider> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = 0; j < list.size() - 1 - i; j++) {
                Provider p1 = list.get(j);
                Provider p2 = list.get(j + 1);

                if (p1.compareTo(p2) > 0) {
                    list.swap(j, j + 1);
                }
            }
        }
    }

    /**
     * sort a list of patient in ascending order
     * @param list list of patient to sort
     */
    public static void patient(List<Patient> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = 0; j < list.size() - 1 - i; j++) {
                Patient p1 = list.get(j);
                Patient p2 = list.get(j + 1);

                if (p1.compareTo(p2) > 0) {
                    list.swap(j, j + 1);
                }
            }
        }
    }

    /**
     * Determines if the appointment needs to be swapped based on given key.
     * @param a1 first appointment
     * @param a2 second appointment
     * @param key the key based on which they see if swap needs to occur, "D" for date, "P" for provider, "C" for county, "T" for timeslot, "p" patient
     * @return true if appointment needs to be swapped, false otherwise
     */
    private static boolean swapNeed(Appointment a1, Appointment a2, char key) {
        switch (key) {
            case 'D': // Sort by date
                return a1.getDate().compareTo(a2.getDate()) > 0;
            case 'P': // Sort by provider
                return a1.getProvider().compareTo(a2.getProvider()) > 0;
            case 'C': //Sort by County
                return (((Provider) a1.getProvider()).getLocation().getCounty()).compareTo(((Provider) a2.getProvider()).getLocation().getCounty()) > 0;
            case 'T': //Sort by Time
                return a1.gettimeslot().compareTo(a2.gettimeslot()) > 0;
            case 'p' :
                return a1.getPatient().getProfile().compareTo(a2.getPatient().getProfile()) > 0;
            default:
                return false;
        }
    }
}