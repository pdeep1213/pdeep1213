package com.example.ru_pizza;

/**
 * The PizzaFactory interface defines the methods for creating different types of pizzas.
 * @author Deep Patel, Manan Patel
 */
public interface PizzaFactory {

    /**
     * Creates a Deluxe pizza.
     *
     * @return a new Deluxe pizza
     */
    Pizza createDeluxe();

    /**
     * Creates a Meatzza pizza.
     *
     * @return a new Meatzza pizza
     */
    Pizza createMeatzza();

    /**
     * Creates a BBQChicken pizza.
     *
     * @return a new BBQChicken pizza
     */
    Pizza createBBQChicken();

    /**
     * Creates a Build Your Own pizza.
     *
     * @return a new Build Your Own pizza
     */
    Pizza createBuildYourOwn();
}

