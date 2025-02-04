package com.example.ru_pizza;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * This class represents a custom pizza that allows users to select their toppings and crust.
 * The maximum number of toppings is limited to 7, and any attempt to exceed this will throw an exception.
 * @author Deep Patel, Manan Patel
 */
public class BuildYourOwn extends Pizza{
    private static final double BASE_SMALL_PRICE = 8.99;
    private static final double BASE_MEDIUM_PRICE = 10.99;
    private static final double BASE_LARGE_PRICE = 12.99;
    private static final double TOPPING_PRICE = 1.69;

    /**
     * Constructor to create a BuildYourOwn pizza with a specified size, crust, and toppings.
     * A maximum of 7 toppings can be added; otherwise, an IllegalArgumentException is thrown.
     *
     * @param size size of the pizza
     * @param toppings list of toppings
     * @param crust type of crust
     */
    public BuildYourOwn(Size size, ArrayList<Topping> toppings, Crust crust) {
        super(toppings, crust, size);
        if (toppings.size() > 7) {
            throw new IllegalArgumentException("Maximum of 7 toppings allowed.");
        }
    }

    /**
     * Adding more topping to the BuildYourOwn pizza.
     * If the number of toppings exceeds 7, an IllegalArgumentException is thrown.
     *
     * @param topping topping to be added
     */
    public void addTopping(Topping topping){
        super.getToppings().add(topping);
    }

    /**
     * Calculates the price of the pizza based on its size and number of topping.
     *
     * @return The price of the pizza based on its size and topping
     */
    @Override
    public double price() {

        double basePrice;
        switch (super.getSize()) {
            case SMALL: basePrice = BASE_SMALL_PRICE; break;
            case MEDIUM: basePrice = BASE_MEDIUM_PRICE; break;
            case LARGE: basePrice = BASE_LARGE_PRICE; break;
            default: throw new IllegalStateException("Unexpected value: " + getSize());
        }
        return basePrice + (TOPPING_PRICE * super.getToppings().size());
    }

    /**
     * Constructor to create a BuildYourOwn pizza with a specified crust and default size and toppings.
     *
     * @param crust type of crust for the pizza
     */
    public BuildYourOwn(Crust crust){
        super(crust);
    }
}
