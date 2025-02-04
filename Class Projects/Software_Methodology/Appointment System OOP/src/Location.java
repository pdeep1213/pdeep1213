/**
 * The Location enum represents various city location with county and zip code, of medical service providers practice.
 * @author Deep Patel, Manan Patel
 */
public enum Location {
    BRIDGEWATER("BRIDGEWATER","Somerset","08807"),
    EDISON("EDISON","Middlesex","08817"),
    PISCATAWAY("PISCATAWAY","Middlesex","08854"),
    PRINCETON("PRINCETON","Mercer","08542"),
    MORRRISTOWN("MORRRISTOWN","Morris","07960"),
    CLARK("CLARK","Union","07066");

    private final String city;
    private final String county;
    private final String zip;

    /**
     * Constructor for the location enum.
     * @param city name of the city associated with medical service provider location
     * @param county name of the county associated with medical service provider location
     * @param zip zip code associated with medical service provider location
     */
    Location(String city, String county, String zip){
        this.city = city;
        this.county = county;
        this.zip = zip;
    }

    /**
     * Gets the city for given location.
     * @return a city
     */
    public String getCity() {
        return city;
    }

    /**
     * Gets the county for given location.
     * @return a county
     */
    public String getCounty() {
        return county;
    }

    /**
     * Gets the zip code for given location.
     * @return a zip code
     */
    public String getZip() {
        return zip;
    }

    /**
     * Returns location in a string format.
     * @return a string in format "CityName CountyName ZipCode"
     */
    @Override
    public String toString(){
        return this.city + "," + this.county + " " + this.zip;
    }
}