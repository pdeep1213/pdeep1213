package com.example.javafx_clinicmanager;


/**
 * This class is an Extension of Appointment class, it is for Imagining Room appointments
 * @author Deep Patel, Manan Patel
 */
public class Imaging extends Appointment {
    private final Radiology room;


    /**
     * Parameterized Constructor with 4 Parameters
     * @param date     The date of the appointment
     * @param timeslot the timeslot of the appointment
     * @param patient  the info of the Patient
     * @param provider info of provider
     * @param room     Type of Imaging room
     */
    public Imaging(Date date, Timeslot timeslot, Person patient, Person provider, Radiology room) {
        super(date, timeslot, patient, provider);
        this.room = room;
    }

    /**
     * Getter for the type of Room
     * @return String room
     */
    public Radiology getRoom() {
        return room;
    }

    /**
     *  A textual representation of the Imaging object
     */
    @Override
    public String toString() {
        return super.toString() + "[" + this.room.toString() + "]";
    }
}
