package com.example.ru_pizza;

import java.util.ArrayList;


/**
 * SharedResources is a singleton class that manages shared data across the application.
 * It provides access to the current order, all orders, current toppings, and selected pizza style.
 * This ensures a centralized way to manage and share resources between activities.
 *
 * The singleton pattern ensures that only one instance of this class exists during the app's lifecycle.
 *
 * @author Deep Pate, Manan Patel
 */
public final class SharedResources {
    private static SharedResources resources;
    private Order currentOrder;
    private ArrayList<Order> all_Orders;
    private ArrayList<Topping> current_toppings;
    private String style = "NY";


    /**
     * Private constructor to prevent instantiation from outside the class.
     * Enforces the singleton pattern.
     */
    private SharedResources(){}

    /**
     * Returns the singleton instance of SharedResources. If the instance doesn't exist,
     * it creates a new one.
     *
     * @return The single instance of SharedResources.
     */
    public static synchronized SharedResources getInstance(){
        if(resources == null) resources = new SharedResources();
        return resources;
    }

    /**
     * Retrieves the current order. If no current order exists, it initializes a new one.
     *
     * @return The current order.
     */
    public Order getCurrentOrder() {
        if(currentOrder == null) currentOrder = new Order();
        return currentOrder;
    }

    /**
     * Updates the current order.
     *
     * @param currentOrder The new current order to be set.
     */
    public void setCurrentOrder(Order currentOrder) {
        this.currentOrder = currentOrder;
    }

    /**
     * Retrieves the list of all orders. If no orders exist, it initializes an empty list.
     *
     * @return The list of all orders.
     */
    public ArrayList<Order> getAll_Orders() {
        if(this.all_Orders == null) all_Orders = new ArrayList<>();
        return all_Orders;
    }

    /**
     * Updates the list of all orders.
     *
     * @param all_Orders The new list of orders to be set.
     */
    public void setAll_Orders(ArrayList<Order> all_Orders) {
        this.all_Orders = all_Orders;
    }

    /**
     * Retrieves the currently selected pizza style.
     *
     * @return The current pizza style as a string.
     */
    public String getStyle() {
        return this.style;
    }

    /**
     * Updates the selected pizza style.
     *
     * @param style The new pizza style to be set.
     */
    public void setStyle(String style){
        this.style = style;
    }

    /**
     * Retrieves the list of currently selected toppings. If no toppings exist, it initializes
     * an empty list.
     *
     * @return The list of currently selected toppings.
     */
    public ArrayList<Topping> getCurrent_toppings(){
        if(current_toppings == null) current_toppings = new ArrayList<>();
        return current_toppings;
    }

    /**
     * Updates the list of currently selected toppings.
     *
     * @param current_toppings The new list of toppings to be set.
     */
    public void setCurrent_toppings(ArrayList<Topping> current_toppings){this.current_toppings = current_toppings;}
}
