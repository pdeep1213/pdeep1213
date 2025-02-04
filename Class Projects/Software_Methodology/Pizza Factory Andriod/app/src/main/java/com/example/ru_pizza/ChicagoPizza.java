package com.example.ru_pizza;

/**
 * This class is a concrete implementation of the PizzaFactory interface.
 * It creates different types of pizzas with Chicago-style crusts.
 * @author Deep Patel, Manan Patel
 */
public class ChicagoPizza implements PizzaFactory{

    /**
     * Creates a Deluxe pizza with a deep-dish crust.
     *
     * @return New Deluxe pizza with a deep-dish crust.
     */
    @Override
    public Pizza createDeluxe() {
        return new Deluxe(Crust.DEEP_DISH);
    }

    /**
     * Creates a Meatzza pizza with a stuffed crust.
     *
     * @return New Meatzza pizza with a stuffed crust.
     */
    @Override
    public Pizza createMeatzza() {
        return new Meatzza(Crust.STUFFED);
    }

    /**
     * Creates a BBQChicken pizza with a pan crust.
     *
     * @return New BBQChicken pizza with a pan crust.
     */
    @Override
    public Pizza createBBQChicken() {
        return new BBQChicken(Crust.PAN);
    }

    /**
            * Creates a BuildYourOwn pizza with a pan crust.
            *
            * @return New BuildYourOwn pizza with a pan crust.
     */
    @Override
    public Pizza createBuildYourOwn() {
        return new BuildYourOwn(Crust.PAN);
    }
}
