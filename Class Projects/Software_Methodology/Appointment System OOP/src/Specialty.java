/**
 * The Specialty enum represents different field in medical and contain charge for each.
 * @author Deep Patel, Manan Patel
 */
public enum Specialty {
    FAMILY("Family",250),
    PEDIATRICIAN("Pediatrician",300),
    ALLERGIST("Allergist",350);

    private final String speciality;
    private final int charge;

    /**
     * Constructor for the specialty enum.
     * @param speciality name of the specialty
     * @param charge cost associated with the specialty visit
     */
    Specialty(String speciality, int charge){
        this.speciality = speciality;
        this.charge = charge;
    }

    /**
     * Gets the charge for given specialty.
     * @return a charge
     */
    public int getCharge() {
        return charge;
    }

    /**
     * Gets the specialty for given specialty.
     * @return a specialty
     */
    public String getSpeciality(){
        return this.speciality;
    }
}
