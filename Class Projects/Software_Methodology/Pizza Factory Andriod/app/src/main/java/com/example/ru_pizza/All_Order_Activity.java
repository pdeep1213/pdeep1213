package com.example.ru_pizza;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


/**
 * This activity displays all orders in a list view and allows users to remove an order
 * by clicking on it. A confirmation dialog appears before the order is removed.
 * Implements the AdapterView.OnItemClickListener interface to handle item click events.
 *
 * @author Deep Pate, Manan Patel
 */
public class All_Order_Activity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    SharedResources resources = SharedResources.getInstance();
    ListView all_order_list;

    /**
     * Initializes the activity, sets up the layout, and populates the ListView with all orders.
     *
     * @param savedInstanceState The saved state of the activity, if available.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_order_view);
            all_order_list = findViewById(R.id.all_order_list);
            ArrayAdapter<Order> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, resources.getAll_Orders());
            all_order_list.setAdapter(adapter);
            all_order_list.setOnItemClickListener(this);

    }

    /**
     * Handles click events on items in the ListView. Displays a confirmation dialog
     * to remove the selected order from the list.
     *
     * @param parent The AdapterView where the click happened.
     * @param view The view that was clicked.
     * @param position The position of the clicked item in the adapter.
     * @param id The row ID of the clicked item.
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Remove");
        alert.setMessage("Would you live to remove the Order");
        //anonymous inner class to handle the onClick event of YES or NO.
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                resources.getAll_Orders().remove(parent.getAdapter().getItem(position));
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
}
