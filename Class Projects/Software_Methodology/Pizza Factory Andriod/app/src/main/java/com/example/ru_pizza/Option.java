package com.example.ru_pizza;

/**
 * Represents an option for a pizza, including its name, description, and an associated image.
 * This class provides getter methods to access these properties.
 *
 * @author Deep Patel, Manan Patel
 */
public class Option {
    private String name;
    private String description;
    private int image;

    /**
     * Creates an Option object with the specified name, description, and image.
     *
     * @param name The name of the option.
     * @param description A brief description of the option.
     * @param image The resource ID for the option's image.
     */
    public Option(String name, String description, int image){
        this.name = name;
        this.description = description;
        this.image = image;
    }

    /**
     * Returns the name of the option.
     *
     * @return The name of the option.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the description of the option.
     *
     * @return The description of the option.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the resource ID of the option's image.
     *
     * @return The resource ID of the image.
     */
    public int getImage() {
        return image;
    }
}
