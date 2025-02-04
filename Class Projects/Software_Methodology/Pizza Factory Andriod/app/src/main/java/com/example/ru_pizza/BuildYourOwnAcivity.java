package com.example.ru_pizza;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


/**
 * This activity allows users to build their own custom pizza by selecting the size, style,
 * and toppings. Users can review their selected toppings and add the custom pizza to the cart.
 * The activity uses spinners for size and style selection and a RecyclerView for toppings.
 *
 * Implements functionality for placing an order with user-selected preferences.
 *
 * @author Deep Pate, Manan Patel
 */
public class BuildYourOwnAcivity extends AppCompatActivity {
    SharedResources resources = SharedResources.getInstance();

    private ArrayAdapter<Size> adapter;
    private ArrayAdapter<String> adapter_2;
    private RecyclerView topping_rcview;

    Spinner size,style;

    ArrayList<Topping_Option> options =  new ArrayList<>();

    private final int[] optionImages = {R.drawable.sausage,R.drawable.pepperoni,R.drawable.green_pepper,R.drawable.onion,R.drawable.mushroom,R.drawable.bbq_chicken,R.drawable.provolone,R.drawable.cheedar,R.drawable.beef,R.drawable.ham};

    /**
     * Initializes the activity, sets up the layout, and populates the spinners and RecyclerView
     * with available options for pizza size, style, and toppings.
     *
     * @param savedInstanceState The saved state of the activity, if available.
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.build_your_own_view);

        resources.setCurrent_toppings(new ArrayList<>());

        size = findViewById(R.id.spinner_BYO);
        adapter = new ArrayAdapter<>(this,androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,Size.values());
        size.setAdapter(adapter);

        style = findViewById(R.id.spinner_style);
        adapter_2 = new ArrayAdapter<>(this,androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, new String[]{"New York Style", "Chicago Style"});
        style.setAdapter(adapter_2);

        topping_rcview = findViewById(R.id.topping_rcview);
        setupToppings();
        ToppingAdapter toppingAdapter = new ToppingAdapter(this, options);
        topping_rcview.setAdapter(toppingAdapter);
        topping_rcview.setLayoutManager(new LinearLayoutManager(this));


    }

    /**
     * Populates the list of topping options by pairing topping names with their respective images.
     */
    private void setupToppings(){
        String[] name = getResources().getStringArray(R.array.Topping_names);
        for(int i = 0; i < name.length;i++){
            options.add(new Topping_Option(name[i],optionImages[i]));
        }
    }

    /**
     * Handles the event when the user clicks the "Add to Cart" button.
     * Displays a confirmation dialog showing selected toppings and allows the user
     * to confirm or cancel placing the order.
     *
     * @param view The view that triggered the click event.
     */

    public void add_to_cart_click(View view){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(resources.getCurrent_toppings().toString());
        builder.setTitle("Place Order with Selected Toppings");
        builder.setCancelable(false);
        builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
            // If user click no then dialog box is canceled.
            dialog.cancel();
        });
        builder.setPositiveButton("Yes",(DialogInterface.OnClickListener) (dialog, which) -> {
            // If user click no then dialog box is canceled.
            PizzaFactory pizzaFactory;

            if(style.getSelectedItem().toString().equals("New York Stle")) pizzaFactory = new NYPizza();
            else pizzaFactory = new ChicagoPizza();

            Pizza to_add = pizzaFactory.createBuildYourOwn();
            to_add.setSize((Size)size.getSelectedItem());
            to_add.setToppings(resources.getCurrent_toppings());
            Order currentOrder = resources.getCurrentOrder();
            currentOrder.addPizza(to_add);
            resources.setCurrentOrder(currentOrder);
            recreate();
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }



}
