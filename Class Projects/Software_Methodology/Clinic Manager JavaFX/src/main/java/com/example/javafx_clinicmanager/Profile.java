package com.example.javafx_clinicmanager;


/**
 * The Profile class defines a Profile with a first name, last name, and date of birth.
 * Compares Profiles based on last name, first name, and date of birth.
 * @author Deep Patel, Manan Patel
 */
public class Profile implements Comparable<Profile>{
    private String fname;
    private String lname;
    private Date dob;

    /**
     * Constructor to create a Profile object.
     * @param fname The first name associated with Profile
     * @param lname The last name associated with Profile
     * @param dob The date of birth associated with Profile
     */
    public Profile(String fname, String lname, Date dob){
        this.dob = dob;
        this.fname = fname;
        this.lname = lname;
    }

    public Date getDob() {
        return dob;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    /**
     * Checks if a Profile is equal to another Profile.
     * Two profiles are equal if they have the same last name, first name, and date of birth.
     * @param obj An object to be compared with a profile
     * @return true if the profiles are equal and false otherwise
     */
    @Override
    public boolean equals(Object obj){
        if(obj instanceof Profile){
            Profile other = (Profile) obj;
            return this.fname.equalsIgnoreCase(other.fname)
                    && this.lname.equalsIgnoreCase(other.lname)
                    && this.dob.equals(other.dob);
        }
        return false;
    }

    /**
     * Returns Profile in a string format.
     * @return a string in format "FirstName LastName DateOfBirth"
     */
    @Override
    public String toString(){
        return this.fname + " " + this.lname + " " + this.dob.toString();
    }

    /**
     * Compares a Profile with another Profile.
     * Comparison is based on last name, then first name, then date of birth.
     * @param other the other Profile to be compared with first one
     * @return 1 if other profile comes before the first one, -1 if other profile comes after the first one, and 0 if both are the same.
     */
    @Override
    public int compareTo(Profile other) {
        if(this.lname.compareTo(other.lname) == 0){
            if(this.fname.compareTo(other.fname) == 0){
                if(this.dob.compareTo(other.dob) == 0){
                    return 0;
                } else {
                    if(this.dob.compareTo(other.dob) > 0){ return 1;}
                    else return -1;
                }
            } else {
                if(this.fname.compareTo(other.fname) > 0) return 1;
                else return -1;
            }
        } else {
            if(this.lname.compareTo(other.lname) > 0) return 1;
            else return -1;
        }
    }


}
