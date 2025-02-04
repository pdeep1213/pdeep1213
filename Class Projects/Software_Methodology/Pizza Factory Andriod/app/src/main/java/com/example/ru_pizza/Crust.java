package com.example.ru_pizza;

/**
 * The Crust enum defines the different types of pizza crusts available in the pizza ordering system.
 * @author Deep Patel, Manan Patel
 */
public enum Crust {

    DEEP_DISH("DEEP DISH"),
    BROOKLYN("BROOKLYN"),
    PAN("PAN"),
    THIN("THIN"),
    STUFFED("STUFFED"),
    HAND_TOSSED("HAND-TOSSED");

    // The string of the crust type

    private final String type;

    /**
     * Constructor to start the crust type.
     *
     * @param type The string representing the crust type.
     */
    Crust(String type){
        this.type = type;
    }

    /**
     * Returns the string of the crust type.
     *
     * @return The crust type as a string.
     */
    public String getType() {
        return type;
    }

    /**
     * Returns the string of the crust type.
     *
     * @return The crust type as a string.
     */
    @Override
    public String toString() {
        return this.type;
    }
}
