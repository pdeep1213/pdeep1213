/**
 * The Visit class keeps track of visit using linked list to store those visits.
 * Visit contains reference to Appointment and connects to next visit in list.
 * @author Deep Patel, Manan Patel
 */
public class Visit {
    private Appointment appointment; //a reference to an appointment object
    private Visit next; //a ref. to the next appointment object in the list

    /**
     * Constructs a Visit instance with appointment
     * @param appointment appointment associated with this visit
     */
    public Visit(Appointment appointment){
        this.appointment = appointment;
        this.next = null;
    }

    /**
     * Copy constructor to create another visit
     * @param visit copy visit object
     */
    public Visit(Visit visit){
        this.appointment = visit.getAppointment();
        this.next = visit.next;
    }

    /**
     * Gets appointment associated with this visit.
     * @return appointment of this visit
     */
    public Appointment getAppointment() {
        return appointment;
    }

    /**
     * Gets next visit in list.
     * @return next visit
     */
    public Visit getNext() {
        return next;
    }

    /**
     * Sets next visit in list.
     * @return goes to next visit in list
     */
    public void setNext(Visit visit){
        this.next = visit;
    }
}
