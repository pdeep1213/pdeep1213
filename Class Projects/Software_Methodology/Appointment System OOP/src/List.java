/**
 * The list class is the listing model for the appointments.
 * @author Deep Patel, Manan Patel
 */
public class List {
    private Appointment[] appointments;
    private int size; //number of appointments in the array

    /**
     * Default Constructor that starts the array with capacity of 4 and size 0
     */
    public List() {
        this.appointments = new Appointment[4];
        this.size = 0;
    }

    /**
     * Used to Get Appointment at Specific Index
     * @param index at which the Appointment is
     * @return The Appointment
     */
    public Appointment getAppointmentatIndex(int index) {
        if (index > size) {
            return null;
        }
        return appointments[index];
    }

    /**
     * Getter for size
     * @return size of the Array appointments
     */
    public int getSize() {
        return size;
    }

    /**
     * helper method to find an appointment if it exists.
     * @param appointment
     * @return returns index if found, otherwise -1 to say not found.
     */
    private int find(Appointment appointment) {
        for (int i = 0; i < size; i++) {
                if (this.appointments[i].equals(appointment)) {
                    return i;
                }
        }
        return -1;
    }

    /**
     * helper method to see if Appointment exists using Profile and Appointment Date
     * @param patient Patient info
     * @param timeslot Time
     * @param appDate Appointment Date
     * @return Index at which the Patients appointment exists
     */
    public int patientApp(Profile patient, Timeslot timeslot, Date appDate) {
        for (int i = 0; i < this.size; i++) {
            if (this.appointments[i].getPatient().equals(patient) && this.appointments[i].gettimeslot().equals(timeslot) && this.appointments[i].getDate().equals(appDate)) {
                return i;
            }
        }

        return -1;
    }


    /**
     * Helper method to increase the capacity of the appointments array by 4.
     * This method creates a new array that is larger by 4 elements than the
     * current appointments array, copies the existing appointments to the new array,
     * and then replaces the old array with the new one. This is used to dynamically
     * expand the storage capacity when more appointments need to be added.
     */
    private void grow() {
        Appointment[] newAppointments = new Appointment[appointments.length + 4];
        for (int i = 0; i < appointments.length; i++) {
            newAppointments[i] = this.appointments[i];
        }
        appointments = newAppointments;
    }

    /**
     * Checks if the specified appointment exists in the appointments array.
     * This method calls the find() method to determine whether the given
     * appointment is present in the array.
     *
     * @param appointment the Appointment object to be checked for existence.
     * @return boolean - returns true if the appointment exists, false otherwise.
     */
    public boolean contains(Appointment appointment) {
        return find(appointment) != -1;
    }

    /**
     * Checks if the specified provider is available for a given timeslot and date.
     * This method iterates through the current list of appointments to see if the
     * provider has already been booked for the same timeslot on the given date.
     *
     * @param provider the Provider whose availability is being checked.
     * @param slot the Timeslot during which the provider's availability is to be verified.
     * @param appDate the Date of the appointment being checked.
     * @return boolean - returns true if the provider is available for the given
     *         timeslot and date, false otherwise.
     */
    public boolean providerAvail(Provider provider, Timeslot slot, Date appDate){
        for(int i = 0; i < this.size;i++){
            if(this.appointments[i].getProvider().equals(provider) && this.appointments[i].gettimeslot().equals(slot) && this.appointments[i].getDate().equals(appDate)){
                return false;
            }
        }

        return true;
    }

    /**
     * helper method to add new appointment
     * @param appointment
     */
    public void add(Appointment appointment) {
        if (!contains(appointment)) {
                if (size == appointments.length) {
                    grow();
                }
            appointments[size++] = appointment;
            }
        }


    /**
     * This method is used to clear the list
     */
    public void clear(){
        for(int i = 0;i < size;i++){
            appointments[i] = null;
        }
        size = 0;
    }

    /**
     * helper method to remove an appointment
     * @param appointment
     */
    public void remove(Appointment appointment) {
        if (contains(appointment)) {
            int index = find(appointment);
            if (index != -1) {
                for (int i = index; i < size - 1; i++) {
                    appointments[i] = appointments[i + 1];
                }
                appointments[size - 1] = null;
                size--;
            }
        }
    }

    /**
     * Orders the list by patient profile, data/timeslot for Command PP
     */
    public void printByPatient() {
        sortByAppointmentTime();
        sortByAppointmentDate();
        sortByPatientProfile();
    }

    /**
     * Orders the list by county, data/timeslot for Command PL
     */
    public void printByLocation() {
        sortByAppointmentTime();
        sortByAppointmentDate();
        sortByLocation();
    }

    /**
     * Orders the list by data/timeslot, provider name for Command PA
     */
    public void printByAppointment() {
        sortByProvider();
        sortByAppointmentTime();
        sortByAppointmentDate();
    }

    /**
     * Sorts The list by patient using Bubble Sort
     */
    public void sortByPatientProfile() {
        for (int i = 1; i < appointments.length; i++) {
            Appointment checker = appointments[i];

            int pointer = i - 1;
            while (pointer >= 0 && appointments[pointer] != null && checker != null && appointments[pointer].getPatient().compareTo(checker.getPatient()) > 0) {
                appointments[pointer + 1] = appointments[pointer];
                pointer--;
            }
            appointments[pointer + 1] = checker;
        }
    }

    /**
     * Sorts The list by Location of Provider using Bubble Sort
     */
    public void sortByLocation() {
        for (int i = 1; i < appointments.length; i++) {
            Appointment checker = appointments[i];

            int pointer = i - 1;
            while (pointer >= 0 && appointments[pointer] != null && checker != null && appointments[pointer].getProvider().getLocation().getCounty().compareTo(checker.getProvider().getLocation().getCounty()) > 0) {
                appointments[pointer + 1] = appointments[pointer];
                pointer--;
            }
            appointments[pointer + 1] = checker;
        }
    }

    /**
     * Sorts The list by Appointment Date using Bubble Sort
     */
    public void sortByAppointmentDate() {
        for (int i = 1; i < appointments.length; i++) {
            Appointment checker = appointments[i];

            int pointer = i - 1;
            while (pointer >= 0 && appointments[pointer] != null && checker != null && appointments[pointer].getDate().compareTo(checker.getDate()) > 0) {
                appointments[pointer + 1] = appointments[pointer];
                pointer--;
            }
            appointments[pointer + 1] = checker;
        }
    }

    /**
     * Sorts The list by Appointment Time using Bubble Sort
     */
    public void sortByAppointmentTime() {
        for (int i = 1; i < appointments.length; i++) {
            Appointment checker = appointments[i];

            int pointer = i - 1;
            while (pointer >= 0 && appointments[pointer] != null  && checker != null && appointments[pointer].gettimeslot().compareTo(checker.gettimeslot()) > 0) {
                appointments[pointer + 1] = appointments[pointer];
                pointer--;
            }
            appointments[pointer + 1] = checker;
        }
    }


    /**
     * Sorts The list by provider's last name using Bubble Sort
     */
    public void sortByProvider() {
        // Insertion sort algorithm
        for (int i = 1; i < appointments.length; i++) {
            Appointment checker = appointments[i];
            int pointer = i - 1;

            // Sorting by provider's last name (assuming getLast_name() is a method in Provider)
            while (pointer >= 0 && appointments[pointer] != null && checker != null &&
                    appointments[pointer].getProvider().getLast_name().compareTo(checker.getProvider().getLast_name()) > 0) {
                appointments[pointer + 1] = appointments[pointer];  // Shift the larger element to the right
                pointer--;
            }
            appointments[pointer + 1] = checker; // Insert the current checker into its correct position
        }
    }
}
