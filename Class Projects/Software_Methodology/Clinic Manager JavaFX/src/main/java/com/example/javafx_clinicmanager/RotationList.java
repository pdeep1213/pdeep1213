package com.example.javafx_clinicmanager;


/**
 * This class represents the circular linked list to manage rotation of technician.
 * @author Deep Patel,Manan Patel
 */
public class RotationList {
    private Technician technician;
    private Location location;
    private RotationList next;

    /**
     * Default constructor to start the RotationList
     */
    public RotationList(){
        this.technician = null;
        this.next = null;
    }

    /**
     * Initializes the list with given technician
     * @param technician technician to start the list
     */
    public RotationList(Technician technician){
        this.technician = technician;
        this.next = null;
    }

    /**
     * To get the technician at a node
     * @return the technician at a node
     */
    public Technician getTechnician(){
        return this.technician;
    }

    /**
     * Sets the reference to next node in list
     * @param next the next node in list
     */
    public void setNext(RotationList next) {
        this.next = next;
    }

    /**
     * Get the next node in list
     * @return the next node in list
     */
    public RotationList getNext(){
        return this.next;
    }

    /**
     * Add a new technician to the circular linked list, start if empty, otherwise add at the end.
     * @param technician technician to be added to the list
     */
    public void addTechnician(Technician technician) {
        RotationList newNode = new RotationList(technician);

        if (this.technician == null) { // List is empty, initialize with this technician
            this.technician = technician;
            this.next = this; // Points to itself (circular)
        } else {
            RotationList temp = this;
            // Traverse to the last node (the one that points back to the first)
            while (temp.next != this) {
                temp = temp.next;
            }
            temp.next = newNode; // Link last node to new node
            newNode.next = this; // Link new node back to the start (circular)
        }
    }

}
