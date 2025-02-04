package com.example.ru_pizza;

public enum Topping{

    SAUSAGE("SAUSAGE"),
    PEPPERONI("PEPPERONI"),
    GREEN_PEPPER("GREEN PEPPER"),
    ONION("ONION"),
    MUSHROOM("MUSHROOM"),
    BBQ_CHICKEN("BBQ CHICKEN"),
    PROVOLONE("PROVOLONE"),
    CHEDDAR("CHEDDAR"),
    BEEF("BEEF"),
    HAM("HAM"),
    PINEAPPLE("PINEAPPLE"),
    OLIVES("OLIVES"),
    BACON("BACON"),
    SPINACH("SPINACH");



    private final String topping;

    Topping(String topping){
        this.topping = topping;
    }

    public String getTopping() {
        return topping;
    }

    @Override
    public String toString() {
        return this.topping;
    }
}
