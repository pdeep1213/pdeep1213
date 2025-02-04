package com.example.ru_pizza;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Adapter class for a RecyclerView that displays a list of topping options.
 * Uses a ViewHolder pattern to optimize RecyclerView performance.
 *
 * @author Deep Patel, Manan Patel
 */
class ToppingAdapter extends RecyclerView.Adapter<ToppingAdapter.OptionHolder>{

    private Context context;
    private ArrayList<Topping_Option> options;

    /**
     * Constructs a ToppingAdapter with the specified context and list of topping options.
     *
     * @param context The context of the activity or fragment using the adapter.
     * @param options The list of Topping_Option objects to display in the RecyclerView.
     */
    public ToppingAdapter(Context context, ArrayList<Topping_Option> options){
        this.context = context;
        this.options = options;
    }

    /**
     * Inflates the row layout for each item in the RecyclerView and creates a ViewHolder.
     *
     * @param parent The parent ViewGroup into which the new view will be added.
     * @param viewType The type of the new view (not used in this implementation).
     * @return A new OptionHolder containing the inflated view.
     */
    @NonNull
    @Override
    public OptionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.topping_row_view,parent,false);
        return new OptionHolder(view);
    }

    /**
     * Binds the data from the Topping_Option object at the specified position to the ViewHolder.
     *
     * @param holder The ViewHolder to be updated with the data.
     * @param position The position of the Topping_Option object in the list.
     */
    @Override
    public void onBindViewHolder(@NonNull OptionHolder holder, int position) {
        holder.im_topping.setImageResource(options.get(position).getImage());
        holder.tv_name_topping.setText(options.get(position).getName());
    }

    /**
     * Returns the total number of items in the options list.
     *
     * @return The size of the options list.
     */
    @Override
    public int getItemCount() {
        return options.size();
    }

    /**
     * ViewHolder class for the ToppingAdapter. Holds references to the views in a single row item,
     * including the name, image, checkbox, and parent layout. Handles click events to add or remove
     * toppings from the selected toppings list in SharedResources.
     */
    public static class OptionHolder extends RecyclerView.ViewHolder {

        private TextView tv_name_topping;
        private ImageView im_topping;
        private CheckBox selected_box;
        private ConstraintLayout parentLayout;

        /**
         * Initializes the ViewHolder by binding views and setting up click listeners.
         *
         * @param itemView The view for a single row item in the RecyclerView.
         */
        public OptionHolder(@NonNull View itemView) {
            super(itemView);
            SharedResources resources = SharedResources.getInstance();
            tv_name_topping = itemView.findViewById(R.id.tv_name_topping);
            im_topping = itemView.findViewById(R.id.im_topping);
            parentLayout = itemView.findViewById(R.id.rowLayout);

                parentLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                    if(!topping_exists()){
                        if(resources.getCurrent_toppings().size() == 7){
                            AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
                            builder.setMessage("Maximum 7 Topping Allowed");
                            builder.setTitle("Topping Error");
                            builder.setCancelable(false);
                            builder.setNegativeButton("Ok", (DialogInterface.OnClickListener) (dialog, which) -> {
                                // If user click no then dialog box is canceled.
                                dialog.cancel();
                            });
                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                        } else {
                            ArrayList<Topping> toppings = resources.getCurrent_toppings();
                            toppings.add(getTopping());
                            resources.setCurrent_toppings(toppings);
                            Toast.makeText(itemView.getContext(), "Added " + getTopping().toString(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            ArrayList<Topping> toppings = resources.getCurrent_toppings();
                            toppings.remove(getTopping());
                            resources.setCurrent_toppings(toppings);
                            Toast.makeText(itemView.getContext(),"Removed " + getTopping().toString(),Toast.LENGTH_SHORT).show();
                        }


                    }

                    private boolean topping_exists(){
                        for(int i = 0; i < resources.getCurrent_toppings().size();i++){
                            if(getTopping().equals(resources.getCurrent_toppings().get(i))){return true;}
                        }

                        return false;
                    }
                    private Topping getTopping(){
                        Topping topping;

                        if(tv_name_topping.getText().toString().equals("Green Pepper")) return Topping.GREEN_PEPPER;
                        if(tv_name_topping.getText().toString().equals("BBQ Chicken")) return Topping.BBQ_CHICKEN;
                        else topping = Topping.valueOf((tv_name_topping.getText()).toString().toUpperCase());

                        return topping;
                    }
                });

        }



    }
}
