/**
 * The MedicalRecord class represents collection of Patients.
 * Using 1-D array to store those Profiles.
 * @author Deep Patel, Manan Patel
 */
public class MedicalRecord {
    private Patient[] patients;
    private int size;

    /**
     * Constructor to start the medical record with default size.
     * Initial Size = 2
     * Number of Patients = 0
     */
    public MedicalRecord(){
        patients = new Patient[2];
        this.size = 0;
    }

    /**
     * Gets the current number of patients in medical record.
     * @return number of patients stored in record
     */
    public int getSize() {
        return size;
    }

    /**
     * Gets the patients object at given index in medical record.
     * @param index the index of patient to retrieve
     * @return The Patient object at given index, or null if the index is out of bounds
     */
    public Patient getPatient(int index){
        if(index > size){
            return null;
        }
        return patients[index];
    }

    /**
     * Gets the index in medical record of given profile.
     * @param patient Profile of patient that we are looking for
     * @return index of where the patient is found, -1 if not found
     */
    public int findPatient(Profile patient){
        for(int i = 0; i < size;i++){
            if(this.patients[i].getProfile().equals(patient)){
                return i;
            }
        }
        return -1;
    }

    /**
     * Adds a new patient object to the medical record.
     * If array is full, the size is doubled.
     * @param newPatient patient object to be added to the Medical Record
     * @return true if the patient was added, false otherwise
     */
    public boolean add(Patient newPatient) {
        if (size < patients.length) {
            patients[size] = newPatient; // add patient at the current size index
            size++; // increment size
            return true; // successfully added
        } else {
            Patient[] temp = this.patients;
            patients = new Patient[this.size * 2];
            System.arraycopy(temp, 0, patients, 0, temp.length);
            patients[size] = newPatient; // add patient at the current size index
            size++; // increment size
            return true; // successfully added
        }
    }
}