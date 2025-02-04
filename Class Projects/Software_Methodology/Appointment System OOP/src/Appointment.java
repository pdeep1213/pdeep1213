/**
 * The Appointment class models an appointment in a scheduling system. It implements the Comparable<Appointment> interface, allowing appointments to be compared and sorted based on defined criteria
 * @author Deep Patel, Manan Patel
 */
public class Appointment implements Comparable <Appointment> {
    private Date date;
    private Timeslot timeslot;
    private Profile patient;
    private Provider provider;

    /**
     * Parameterized Constructor with 4 Parameters
     * @param date The date of the appointment
     * @param timeslot the timeslot of the appointment
     * @param patient the info of the Patient
     * @param provider info of provider
     */
    public Appointment(Date date, Timeslot timeslot, Profile patient, Provider provider){
        this.date = date;
        this.timeslot = timeslot;
        this.patient = patient;
        this.provider = provider;
    }

    /**
     * Getter for Date
     * @return Appointment Date
     */
    public Date getDate(){
        return this.date;
    }

    /**
     * Setter for Date
     * @param date for Appointment
     */
    public void setDate(Date date){
        this.date = date;
    }

    /**
     * Getter for Time
     * @return Time of Appointment
     */
    public Timeslot gettimeslot(){
        return this.timeslot;
    }

    /**
     * Setter for Time
     * @param timeslot
     */
    public void setTimeslot(Timeslot timeslot){
        this.timeslot = timeslot;
    }

    /**
     * Getter for Patient
     * @return Patient profile
     */
    public Profile getPatient(){
        return this.patient;
    }

    /**
     * Setter for Patient
     * @param patient
     */
    public void setPatient(Profile patient){
        this.patient = patient;
    }

    /**
     * Getter for Provider
     * @return Provider info
     */
    public Provider getProvider(){
        return this.provider;
    }


    /**
     * Two appointments are equal if they have the same Date, Time, and Patient
     * @param obj is the object to be compared with
     * @return Ture if the appointments are the same, False if the appointments are different
     */
    @Override
    public boolean equals(Object obj){
        if(obj instanceof Appointment appointment){
            return this.date.equals(appointment.date) && this.timeslot.equals(appointment.timeslot) && this.patient.equals(appointment.patient);
        }

        return false;
    }

    /**
     *
     * @return A textual representation of the Event object
     */
    @Override
    public String toString() {
        return this.date.toString() + " " + this.timeslot.toString() + " " + this.patient.toString() + " " + this.provider.toString();
    }

    /**
     * Compares two appointment based on date, patient info, time, and provider.
     * @param other the object to be compared.
     * @return 1 if this appointment is greater, -1 if this appointment is smaller and 0 if they are equal
     */
    @Override
    public int compareTo(Appointment other) {
        if(!date.equals(other.getDate())) return date.compareTo(other.getDate());
        if(!timeslot.equals(other.timeslot))  return timeslot.compareTo(other.timeslot);
        if(!patient.equals(other.getPatient())) return patient.compareTo(other.patient);
        return provider.compareTo(other.provider);
    }
}
