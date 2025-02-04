package com.example.ru_pizza;

import java.util.ArrayList;

/**
 * The Order class shows a customer's order in the Pizza Factory application.
 * Order has of one or more pizzas, calculate the total price, and gives the order details.
 * @author Deep Patel, Manan Patel
 */
public class Order {
    private static int lastOrderID = 0;
    private int number; //order number
    private ArrayList<Pizza> pizzas; //can use List<E> instead of ArrayList<E>

    /**
     * Creates a new order with a unique order number and an empty list of pizzas.
     */
    public Order(){
        this.number = createOrderID();
        this.pizzas = new ArrayList<Pizza>();
    }

    /**
     * Adds a pizza to the current order.
     *
     * @param pizza pizza to be added to order
     */
    public void addPizza(Pizza pizza){
        this.pizzas.add(pizza);
    }

    /**
     * Retrieves the order number.
     *
     * @return unique order number
     */
    public int getNumber(){
        return this.number;
    }

    /**
     * Gives the list of pizzas in the order.
     *
     * @return list of pizzas in the order
     */
    public ArrayList<Pizza> getPizzas(){
        return this.pizzas;
    }

    /**
     * Creates ID by incrementing the static variable lastOrderID.
     *
     * @return newly generated order ID
     */
    private int createOrderID(){
         return ++lastOrderID;
    }

    /**
     * Calculates the total price of the current order based on the order.
     *
     * @return total price of the order
     */
    public double getCurrentOrderPrice(){
        double charge = 0;
        for(int i = 0; i < pizzas.size();i++){
            charge += pizzas.get(i).getPrice();
        }
        return charge;
    }

    /**
     * Provides a string representation of the order, including the order number, number of pizzas, total price, and details of the pizzas in the order.
     *
     * @return string format the order
     */
    @Override
    public String toString(){
        if(!this.pizzas.isEmpty()) {return "Order Number : " + this.number + " Items : " + pizzas.size() + " Order Total : " + getCurrentOrderPrice() +"\n********Pizzas********\n" + this.pizzas.toString();}
        else return "No Orders Yet\n";
    }
}
