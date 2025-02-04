package com.example.ru_pizza;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Displays the details of the selected pizza option and allows the user to add it to the cart.
 * This activity dynamically updates the screen based on the selected pizza type and style
 * (e.g., "New York Style" or "Chicago Style"). Users can select the pizza size and review
 * its crust and toppings before adding it to their current order.
 *
 * Uses SharedResources to manage the selected pizza style and current order.
 *
 * @author Deep Pate, Manan Patel
 */

public class OptionSelectedActivity extends AppCompatActivity {
    SharedResources resources = SharedResources.getInstance();
    private String type,style;
    TextView tv_crust,tv_type,tv_toppings;
    private Spinner spinner;
    private ArrayAdapter<Size> adapter;
    PizzaFactory pizzaFactory;
    Pizza to_add;

    /**
     * Initializes the activity, sets up the layout, and populates the screen with details of
     * the selected pizza. Also sets up the spinner for selecting the pizza size.
     *
     * @param savedInstanceState The saved state of the activity, if available.
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selected_pizza);
        Button add_to_cart = findViewById(R.id.add_to_cart);
        tv_crust = findViewById(R.id.tv_crust);
        tv_type = findViewById(R.id.tv_type);
        tv_toppings = findViewById(R.id.tv_toppings);spinner = findViewById(R.id.spinner);
        adapter = new ArrayAdapter<>(this,androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,Size.values());
        spinner.setAdapter(adapter);
        Intent intent = getIntent();
        type = intent.getStringExtra("TYPE");
        style = resources.getStyle();
        setupScreen();
    }

    /**
     * Handles the event when the user clicks the "Add to Cart" button.
     * Sets the selected size for the pizza, adds it to the current order, and
     * displays a confirmation message.
     *
     * @param view The view that triggered the click event.
     */
    public void add_to_cart_click(View view){
        to_add.setSize((Size) spinner.getSelectedItem());
        Order order = resources.getCurrentOrder();
        order.addPizza(to_add);
        resources.setCurrentOrder(order);
        Toast.makeText(this, "Added to Cart", Toast.LENGTH_SHORT).show();
    }

    /**
     * Configures the screen based on the selected pizza style and type.
     * Uses the appropriate PizzaFactory to create the selected pizza and updates the
     */
    private void setupScreen(){
        if(style.equals("New York Style")) pizzaFactory = new NYPizza();
        if(style.equals("Chicago Style")) pizzaFactory = new ChicagoPizza();

        if(type.equals("Deluxe")) to_add = pizzaFactory.createDeluxe();
        else if(type.equals("Meatzza")) to_add = pizzaFactory.createMeatzza();
        else to_add = pizzaFactory.createBBQChicken();

        tv_type.setText(style);
        tv_crust.setText(to_add.getCrust().toString());
        tv_toppings.setText(to_add.getToppings().toString());
    }
}
