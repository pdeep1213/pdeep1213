package com.example.ru_pizza;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

/**
 * This activity displays the current order in a list view, allowing users to review the pizzas
 * they have added. Users can remove individual pizzas or proceed to checkout.
 * The activity calculates and displays the order's subtotal, tax, and total price.
 *
 * Implements AdapterView.OnItemClickListener to handle list item click events.
 *
 * @author Manan Patel, Deep Patel
 */
public class current_order_activity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    SharedResources resources = SharedResources.getInstance();
    ListView current_order_list;
    TextView pre_tax,tax,total;
    private ArrayAdapter<Pizza> adapter;

    /**
     * Initializes the activity, sets up the layout, and binds the current order's pizzas
     * to the ListView. It also calculates and displays the order's subtotal, tax, and total price.
     *
     * @param savedInstanceState The saved state of the activity, if available.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.current_cart_view);
        current_order_list = findViewById(R.id.current_order_list);
        pre_tax = findViewById(R.id.pre_tax);
        tax = findViewById(R.id.tax);
        total = findViewById((R.id.total));
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, resources.getCurrentOrder().getPizzas());
        current_order_list.setAdapter(adapter);
        current_order_list.setOnItemClickListener(this);
        setupPrice();

    }



    /**
     * Handles click events on items in the ListView. Displays a confirmation dialog to remove the selected pizza from the order.
     *
     * @param parent The AdapterView where the click happened.
     * @param view The view that was clicked.
     * @param position The position of the clicked item in the adapter.
     * @param id The row ID of the clicked item.
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Remove");
        alert.setMessage("Would you live to remove the Pizza");
        //anonymous inner class to handle the onClick event of YES or NO.
        alert.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                resources.getCurrentOrder().getPizzas().remove(parent.getAdapter().getItem(position));
                recreate();
            }
        }).setNegativeButton("no", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }

    /**
     * Handles the event when the user clicks the "Checkout" button.
     *
     * @param view The view that triggered the click event.
     */
    public void onCheckout(View view){
        resources.getAll_Orders().add(resources.getCurrentOrder());
        resources.setCurrentOrder(new Order());
        recreate();
    }


    /**
     * Calculates and displays the order's subtotal, tax, and total price.
     * The tax rate is set at 6.625% of the subtotal.
     */
    private void setupPrice(){
        double subtotal = resources.getCurrentOrder().getCurrentOrderPrice();
        DecimalFormat df = new DecimalFormat("$#.##");
        pre_tax.setText(df.format(subtotal));
        tax.setText(df.format(subtotal * 0.06625));
        total.setText(df.format(subtotal+subtotal*0.06625));
    }




}
