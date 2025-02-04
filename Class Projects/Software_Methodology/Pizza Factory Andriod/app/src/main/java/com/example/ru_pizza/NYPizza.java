package com.example.ru_pizza;

/**
 * This class is a concrete implementation of the PizzaFactory interface.
 * It creates different types of pizzas with NY-style crusts.
 * @author Deep Patel, Manan Patel
 */
public class NYPizza implements PizzaFactory {

    /**
     * Creates a Deluxe pizza with a thin crust.
     *
     * @return New Deluxe pizza with a thin crust.
     */
    @Override
    public Pizza createDeluxe() {
        return new Deluxe(Crust.BROOKLYN);
    }

    /**
     * Creates a Meatzza pizza with a brooklyn crust.
     *
     * @return New Meatzza pizza with a brooklyn crust.
     */
    @Override
    public Pizza createMeatzza() {
        return new Meatzza(Crust.HAND_TOSSED);
    }

    /**
     * Creates a BBQChicken pizza with a hand-tossed crust.
     *
     * @return New BBQChicken pizza with a hand-tossed crust.
     */
    @Override
    public Pizza createBBQChicken() {
        return new BBQChicken(Crust.THIN);
    }

    /**
     * Creates a BuildYourOwn pizza with a hand-tossed crust.
     *
     * @return New BuildYourOwn pizza with a hand-tossed crust.
    */
    @Override
    public Pizza createBuildYourOwn() {

        return new BuildYourOwn(Crust.HAND_TOSSED);
    }
}
