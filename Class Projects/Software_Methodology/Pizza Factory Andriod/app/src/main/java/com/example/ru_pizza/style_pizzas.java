package com.example.ru_pizza;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * The style_pizzas activity displays a list of available pizza options based on the selected style
 * (e.g., "New York Style" or "Chicago Style"). Each pizza option includes an image, name, and description.
 * Users can view detailed options in a RecyclerView and navigate to their cart to review the current order.
 *
 * @author Deep Patel, Manan Patel
 */
public class style_pizzas extends AppCompatActivity {
    SharedResources resources = SharedResources.getInstance();
    String style;
    ArrayList<Option> options = new ArrayList<>();

    private final int[] optionImages ={R.drawable.deluxe,R.drawable.meatzza,R.drawable.bbq};

    /**
     * Initializes the activity, sets up the layout, and populates the RecyclerView
     * with pizza options based on the selected style.
     *
     * @param savedInstanceState The saved state of the activity, if available.
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.style_pizzas);
        RecyclerView rc_view = findViewById(R.id.rc_view);
        TextView header = findViewById(R.id.header);
        Intent intent = getIntent();
        style = intent.getStringExtra("STYLE");
        Toast.makeText(this, style, Toast.LENGTH_SHORT).show();
        header.setText(style);
        setupPizzas();
        OptionAdapter optionAdapter = new OptionAdapter(this,options);
        rc_view.setAdapter(optionAdapter);
        rc_view.setLayoutManager(new LinearLayoutManager(this));

    }

    /**
     * Populates the list of pizza options using predefined names, descriptions, and images.
     */
    private void setupPizzas(){
        String[] name =getResources().getStringArray(R.array.PizzaTypes);
        String[] description =getResources().getStringArray(R.array.PizzaDes);

        for(int i = 0; i < name.length;i++){
            options.add(new Option(name[i],description[i],optionImages[i]));
        }
    }

    /**
     * Handles the event when the user clicks on the "Cart" button.
     *
     * @param view The view that triggered the click event.
     */
    public void onCartClick(View view){
        Intent intent = new Intent(this,current_order_activity.class);
        startActivity(intent);
    }

}
