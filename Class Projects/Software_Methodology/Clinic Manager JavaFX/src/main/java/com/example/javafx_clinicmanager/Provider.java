package com.example.javafx_clinicmanager;

/**
 * This is a superclass for Doctor and Technician, and is also subclass of Person
 * @author Deep Patel, Manan Patel
 */
public abstract class Provider extends Person{
    private Location location;

    /**
     * Parameterized Constructor
     * @param location location of the appointment
     * @param profile profile of the Provider
     */
    public Provider(Location location, Profile profile){
        super(profile);
        this.location = location;
    }

    /**
     * Getter for location
     * @return Location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Abstract method for rate of provider, implemented in subclasses
     * @return int rate
     */
    public abstract int rate();

}