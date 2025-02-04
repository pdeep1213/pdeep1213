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
            return this.fname.equals(other.fname)
                    && this.lname.equals(other.lname)
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

    /**
     * Testbed main method.
     * @param args
     */
    public static void main(String[] args){
        // Creating test profiles
        Profile profile1 = new Profile("Alice", "Smith",
                new Date(95, 4, 15)); // May 15, 1995
        Profile profile2 = new Profile("Bob", "Smith",
                new Date(90, 7, 20));   // August 20, 1990
        Profile profile3 = new Profile("Alice", "Smith",
                new Date(92, 1, 10)); // February 10, 1992
        Profile profile4 = new Profile("Charlie", "Brown",
                new Date(88, 11, 30)); // December 30, 1988
        Profile profile5 = new Profile("Alice", "Smith",
                new Date(95, 4, 15)); // May 15, 1995 (duplicate)

        // Testing compareTo() method
        System.out.println("Comparing Profiles:");
        System.out.println(profile4.compareTo(profile1)); // Should be -1 (different last name)
        System.out.println(profile1.compareTo(profile2)); // Should be -1 (different first name)
        System.out.println(profile3.compareTo(profile1)); // Should be -1 (different dob)
        System.out.println(profile1.compareTo(profile4)); // Should be 1 (different last name)
        System.out.println(profile2.compareTo(profile1)); // Should be 1 (different first name)
        System.out.println(profile1.compareTo(profile3)); // Should be 1 (different dob)
        System.out.println(profile1.compareTo(profile5)); // Should be 0 (equal)
    }
}
