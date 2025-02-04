/**
 * The Provider enum represents provider with their last name, location, and specialty.
 * @author Deep Patel, Manan Patel
 */
public enum Provider {
    PATEL("Patel",Location.BRIDGEWATER,Specialty.FAMILY),
    LIM("Lim",Location.BRIDGEWATER,Specialty.PEDIATRICIAN),
    ZIMNES("Zimnes", Location.CLARK, Specialty.FAMILY),
    HARPER("Harper",Location.CLARK,Specialty.FAMILY),
    KAUR("Kaur",Location.PRINCETON,Specialty.ALLERGIST),
    TAYLOR("Taylor",Location.PISCATAWAY,Specialty.PEDIATRICIAN),
    RAMESH("Ramesh",Location.MORRRISTOWN,Specialty.ALLERGIST),
    CERAVOLO("Ceravolo",Location.EDISON,Specialty.PEDIATRICIAN);

    private final String last_name;
    private final Location location;
    private final Specialty specialty;

    /**
     * Constructor for the provider enum.
     * @param last_name Last name of the provider
     * @param location name of city where they practice
     * @param specialty provider's specialty
     */
    Provider(String last_name, Location location, Specialty specialty){
        this.last_name = last_name;
        this.location = location;
        this.specialty = specialty;
    }

    /**
     * Gets the location for given provider.
     * @return a location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Gets the specialty for given provider.
     * @return a specialty
     */
    public Specialty getSpecialty() {
        return specialty;
    }

    /**
     * Gets the last name for given provider.
     * @return a last name
     */
    public String getLast_name() {
        return last_name;
    }

    /**
     * Returns provider info in a string format.
     * @return a string in format "LastName Location Specialty"
     */
    @Override
    public String toString(){
        return "[" + this.last_name +"," +  this.location.toString() + "," + this.specialty.getSpeciality() + "]";
    }
}
