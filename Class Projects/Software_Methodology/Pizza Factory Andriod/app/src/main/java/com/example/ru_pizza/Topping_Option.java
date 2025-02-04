package com.example.ru_pizza;


/**
 * Represents a topping option for a pizza, including its name and an associated image.
 * This class provides getter methods to access these properties.
 * It is used in features like topping selection or menu display.
 *
 * @author Deep Patel, Manan Patel
 */
public class Topping_Option {
    private String name;
    private int image;

    /**
     * Constructs a Topping_Option object with the specified name and image.
     *
     * @param name The name of the topping.
     * @param image The resource ID for the topping's image.
     */
    public Topping_Option(String name, int image){
        this.name = name;
        this.image = image;
    }

    /**
     * Returns the name of the topping.
     *
     * @return The name of the topping.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the resource ID of the topping's image.
     *
     * @return The resource ID of the image.
     */
    public int getImage() {
        return image;
    }
}
