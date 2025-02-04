package com.example.javafx_clinicmanager;

/**
 * This class handles the Time of the appointments
 * @author Deep Patel, Manan Patel
 */
public class Timeslot implements Comparable<Timeslot> {
    private int hour;
    private int minute;

    /**
     * Parameterized constructor
     * @param hour hour of appointment
     * @param minute minute of appointment
     */
    public Timeslot(int hour, int minute){
        this.hour = hour;
        this.minute = minute;
    }

    /**
     * getter for Hour
     * @return int hour
     */
    public int getHour() {
        return hour;
    }

    /**
     * Getter for minute
     * @return int minute
     */
    public int getMinute() {
        return minute;
    }


    /**
     * Textual representation of object Timeslot
     * @param o the object to be compared.
     * @return String
     */
    @Override
    public int compareTo(Timeslot o) {
        if(this.hour != o.hour){
            if(this.hour > o.hour) return 1;
            return -1;
        }

        if(this.minute != o.minute){
            if(this.minute > o.minute)return 1;
            return -1;
        }
        return 0;
    }

    /**
     * Checks if this Timeslot is equal to another object by comparing hour and minute fields if the object is an instance of Timeslot.
     * @param other the object to be compared for equality.
     * @return true if both hour and minute match, false otherwise.
     */
    @Override
    public boolean equals(Object other){
        if(other instanceof Timeslot timeslot){
            return this.hour == timeslot.hour && this.minute == timeslot.minute;
        }

        return false;
    }

    /**
     * Converts this Timeslot to a readable 12-hour time format with an AM/PM designation.
     * @return the formatted time as a string.
     */
    @Override
    public String toString(){
        int hour12 = (hour == 0 || hour == 12) ? 12 : hour % 12;
        String period = (hour < 12) ? "AM" : "PM";
        return String.format("%d:%02d %s", hour12, minute, period);
    }
}
