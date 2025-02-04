package com.example.ru_pizza;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * The main activity serves as the entry point to the application. It provides the user
 * with options to select pizza styles (New York, Chicago, Build Your Own), view the current cart,
 * or view all past orders. Navigates to respective activities based on user interaction.
 *
 * This activity uses SharedResources to manage shared data across activities.
 *
 * @author Deep Patel, Manan Patel
 */
public class MainActivity extends AppCompatActivity {

    private Order currentOrder;
    SharedResources resources = SharedResources.getInstance();


    /**
     * Initializes the main activity and sets up the layout.
     *
     * @param savedInstanceState The saved state of the activity, if available.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }




    /**
     * Handles the event when the user selects "New York Style" pizzas.
     *
     * @param view The view that triggered the click event.
     */
    public void OnNYClick(View view){
        Intent intent = new Intent(this, style_pizzas.class);
        resources.setStyle("New York Style");
        intent.putExtra("STYLE","New York Style");
        startActivity(intent);
    }

    /**
     * Handles the event when the user selects "Chicago Style" pizzas.
     * Sets the pizza style to "Chicago Style" in SharedResources and navigates to the
     * style_pizzas activity.
     *
     * @param view The view that triggered the click event.
     */
    public void OnCHClick(View view){
        Intent intent = new Intent(this, style_pizzas.class);
        resources.setStyle("Chicago Style");
        intent.putExtra("STYLE","Chicago Style");
        startActivity(intent);
    }

    /**
     * Handles the event when the user selects "Build Your Own" pizzas.
     * Navigates to the BuildYourOwnActivity.
     *
     * @param view The view that triggered the click event.
     */
    public void OnBYOClick(View view){
        Intent intent = new Intent(this, BuildYourOwnAcivity.class);
        startActivity(intent);
    }

    /**
     * Handles the event when the user clicks on the "Cart" button.
     * Navigates to the current_order_activity to view the current order.
     *
     * @param view The view that triggered the click event.
     */
    public void onCartClick(View view){
        Intent intent = new Intent(this,current_order_activity.class);
        startActivity(intent);
    }

    /**
     * Handles the event when the user clicks on the "All Orders" button.
     * Navigates to the All_Order_Activity to view all past orders.
     *
     * @param view The view that triggered the click event.
     */
    public void onALlOrderClick(View view){
        Intent intent = new Intent(this,All_Order_Activity.class);
        startActivity(intent);
    }

}