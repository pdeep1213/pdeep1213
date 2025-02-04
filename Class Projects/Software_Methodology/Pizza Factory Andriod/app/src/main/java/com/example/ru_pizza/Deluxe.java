package com.example.ru_pizza;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * The Deluxe class represents a deluxe pizza, extending the Pizza class. This class includes a predefined set of toppings, it can be customized with a Crust and Size, and the price varies depending on the selected size.
 * @author Deep Patel, Manan Patel
 */
public class Deluxe extends Pizza{
    private static final double SMALL_PRICE = 16.99;
    private static final double MEDIUM_PRICE = 18.99;
    private static final double LARGE_PRICE = 20.99;

    private Crust crust; //Crust is a Enum class
    private Size size;

    /**
     * Creates a Deluxe pizza with the specified crust and size.
     *
     * @param crust crust type for the pizza.
     * @param size size of the pizza.
     */
    public Deluxe(Crust crust, Size size) {
        super(new ArrayList<>(Arrays.asList(Topping.SAUSAGE,Topping.PEPPERONI,Topping.GREEN_PEPPER,Topping.ONION,Topping.MUSHROOM)), crust, size);
    }

    /**
     * Creates a Deluxe pizza with the specified crust and a default size.
     *
     * @param crust crust type for the pizza.
     */
    public Deluxe(Crust crust){
        super(new ArrayList<>(Arrays.asList(Topping.SAUSAGE,Topping.PEPPERONI,Topping.GREEN_PEPPER,Topping.ONION,Topping.MUSHROOM)),crust);
    }


    /**
     * Returns a string of the Deluxe pizza.
     *
     * @return string format of the Deluxe pizza.
     */
    @Override
    public String toString(){
        return super.toString();
    }

    /**
     * Calculates the price of the Deluxe pizza based on its size.
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
