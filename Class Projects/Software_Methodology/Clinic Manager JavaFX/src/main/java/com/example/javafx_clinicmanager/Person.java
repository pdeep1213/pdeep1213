package com.example.javafx_clinicmanager;

/**
 * This is a Super class Person for subclasses Patient and Provider
 * @author Deep Patel, Manan Patel
 */
public class Person implements Comparable<Person>{
    protected Profile profile;

    /**
     * Parameterized constructor
     * @param profile profile of Person
     */
    public Person(Profile profile){
        this.profile = profile;
    }

    /**
     * getter for Profile
     * @return
     */
    public Profile getProfile() {
        return profile;
    }

    /**
     * Checks if a Profile is equal to another Profile.
     * Two profiles are equal if they have the same last name, first name, and date of birth.
     * @param obj An object to be compared with a profile
     * @return true if the profiles are equal and false otherwise
     */
    @Override
    public boolean equals(Object obj){
        if(obj instanceof Person person){
            return this.profile.equals(person.profile);
        }
        return false;
    }

    /**
     * Returns Profile in a string format.
     * @return a string in format "FirstName LastName DateOfBirth"
     */
    @Override
    public String toString(){
        return this.profile.toString();
    }


    /**
     * Compares a Profile with another Profile.
     * Comparison is based on last name, then first name, then date of birth.
     * @param o the other Profile to be compared with first one
     * @return 1 if other profile comes before the first one, -1 if other profile comes after the first one, and 0 if both are the same.
     */
    @Override
    public int compareTo(Person o) {
        return this.profile.compareTo(o.profile);
    }
}
