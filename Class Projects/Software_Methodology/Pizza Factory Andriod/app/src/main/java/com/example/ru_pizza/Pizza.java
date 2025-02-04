package com.example.ru_pizza;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * The Pizza class represents a pizza with various attributes such as toppings, crust, size, and price.
 * @author Deep Patel, Manan Patel
 */
public abstract class Pizza {
    private ArrayList<Topping> toppings; //Topping is a Enum class
    private Crust crust; //Crust is a Enum class
    private Size size; //Size is a Enum class

    /**
     * Calculates the price of the pizza.
     *
     * @return price of the pizza
     */
    public abstract double price();

    /**
     * Constructor to initialize a pizza with toppings, crust, and size.
     *
     * @param toppings list of toppings on the pizza
     * @param crust crust type of the pizza
     * @param size size of the pizza
     */
    public Pizza(ArrayList<Topping> toppings, Crust crust, Size size){
        this.toppings = toppings;
        this.crust = crust;
        this.size = size;
    }

    /**
     * Constructor to initialize a pizza with toppings and crust, without a specific size.
     *
     * @param toppings list of toppings on the pizza
     * @param crust crust type of the pizza
     */
    public Pizza(ArrayList<Topping> toppings, Crust crust){
        this.toppings = toppings;
        this.crust = crust;
    }

    /**
     * Constructor to initialize a pizza with only a crust, without toppings or size.
     *
     * @param crust crust type of the pizza
     */
    public Pizza(Crust crust){
        this.crust = crust;
    }

    /**
     * Sets the size of the pizza.
     *
     * @param size the size of the pizza
     */
    public void setSize(Size size) {
        this.size = size;
    }

    /**
     * Sets the list of toppings on the pizza.
     *
     * @param toppings list of toppings for pizza
     */
    public void setToppings(ArrayList<Topping> toppings){
        this.toppings = toppings;
    }

    /**
     * Gets the size of the pizza.
     *
     * @return size of the pizza
     */
    public Size getSize(){
        return this.size;
    }

    /**
     * Gets the list of toppings on the pizza.
     *
     * @return list of toppings on the pizza
     */
    public ArrayList<Topping> getToppings(){
        return this.toppings;
    }

    /**
     * Gets the price of the pizza by calling the price() method.
     *
     * @return price of the pizza
     */
    public double getPrice(){
        return price();
    }

    /**
     * Gets the crust of the pizza by calling the method.
     *
     * @return crust of the pizza
     */
    public Crust getCrust() {
        return crust;
    }

    /**
     * Provides a string representation of the pizza, including its style, toppings, size, crust, and price.
     *
     * @return string containing the details of the pizza
     */
    @Override
    public String toString(){
        DecimalFormat df = new DecimalFormat("#.##");
        if(this.size != null) {return  pizza_style() +"\nPizza Topping : " + this.toppings.toString() + "\nSize : " + this.size.toString() + "\nCrust : " + this.crust.toString() + "\nPrice : " + df.format(price()) + "\n";}
        else return "Please Select Pizza Size\n";
    }

    /**
     * Determines the style of the pizza (Chicago or NY style) based on the crust.
     *
     * @return style of the pizza as a string (Chicago Style Pizza or NY Style Pizza)
     */
    private String pizza_style(){
        PizzaFactory pizzaFactory = new ChicagoPizza();
        if(this.crust.equals(pizzaFactory.createBBQChicken().crust) || this.crust.equals(pizzaFactory.createDeluxe().crust) || this.crust.equals(pizzaFactory.createMeatzza().crust) || this.crust.equals(pizzaFactory.createBuildYourOwn().crust)){
            return "Chicago Style Pizza";
        }

        return "NY Style Pizza";
    }
}
