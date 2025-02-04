package com.example.javafx_clinicmanager;
/**
 * This is enum class for Imaging rooms
 * @author Deep Patel, Manan Patel
 */
public enum Radiology {
    CATSCAN("CATSCAN"),
    XRAY("XRAY"),
    ULTRASOUND("ULTRASOUND");


    private final String type;

    /**
     * Parameterized constructor
     * @param type of Room
     */
    Radiology(String type){
        this.type = type;
    }

    /**
     * Getter for Type of room
     * @return String room
     */
    public String getType() {
        return type;
    }
}
