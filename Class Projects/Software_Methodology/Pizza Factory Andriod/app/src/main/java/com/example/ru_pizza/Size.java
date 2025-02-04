package com.example.ru_pizza;

/**
 * The Size enum represents the different sizes of a pizza available in the pizza factory.
 * @author Deep Patel, Manan Patel
 */
public enum Size {
    SMALL("SMALL"),
    MEDIUM("MEDIUM"),
    LARGE("LARGE");

    private final String size;

    /**
     * Gets the size with a string representation.
     *
     * @param size string format of the pizza size
     */
    Size(String size){
        this.size = size;
    }

    /**
     * Returns the string representation of the pizza size.
     *
     * @return size as a string
     */
    public String getSize() {
        return size;
    }

    /**
     * Returns the string representation of the pizza size.
     *
     * @return the size as a string
     */
    @Override
    public String toString() {
        return this.size;
    }
}
