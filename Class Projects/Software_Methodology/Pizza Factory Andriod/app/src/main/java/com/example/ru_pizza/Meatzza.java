package com.example.ru_pizza;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * The Meatzza class represents a Meatzza pizza, extending the Pizza class. This class includes a predefined set of toppings, it can be customized with a Crust and Size, and the price varies depending on the selected size.
 * @author Deep Patel, Manan Patel
 */
public class Meatzza extends Pizza{
    private static final double SMALL_PRICE = 17.99;
    private static final double MEDIUM_PRICE = 19.99;
    private static final double LARGE_PRICE = 21.99;

    private Crust crust; //Crust is a Enum class
    private Size size;


    /**
     * Creates a Deluxe pizza with the specified crust and size.
     *
     * @param crust crust type for the pizza.
     */
    public Meatzza(Crust crust){
        super(new ArrayList<>(Arrays.asList(Topping.SAUSAGE,Topping.PEPPERONI,Topping.BEEF,Topping.HAM)),crust);
    }

    /**
     * Calculates the price of the Meatzza pizza based on its size.
     *
     * @return price of the pizza.
     */
    @Override
    public double price() {
        if(super.getSize().equals(Size.SMALL)) return SMALL_PRICE;
        if(super.getSize().equals(Size.MEDIUM)) return MEDIUM_PRICE;
        if(super.getSize().equals(Size.LARGE)) return LARGE_PRICE;
        return 0.00;
        }
}
