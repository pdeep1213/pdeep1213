package com.example.ru_pizza;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Adapter class for a RecyclerView that displays a list of pizza options.
 * Each option includes an image, name, and description. Clicking on an item
 * opens the OptionSelectedActivity and passes the selected option's type.
 *
 * Uses a ViewHolder pattern to optimize RecyclerView performance.
 *
 * @author Deep Patel, Manan Patel
 */
class OptionAdapter extends RecyclerView.Adapter<OptionAdapter.OptionHolder>{

    private Context context;
    private ArrayList<Option> options;

    /**
     * Constructs an OptionAdapter with the specified context and list of options.
     *
     * @param context The context of the activity or fragment using the adapter.
     * @param options The list of Option objects to display in the RecyclerView.
     */
    public OptionAdapter(Context context, ArrayList<Option> options){
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
        View view = inflater.inflate(R.layout.row_view,parent,false);
        return new OptionHolder(view);
    }

    /**
     * Binds the data from the Option object at the specified position to the ViewHolder.
     *
     * @param holder The ViewHolder to be updated with the data.
     * @param position The position of the Option object in the list.
     */
    @Override
    public void onBindViewHolder(@NonNull OptionHolder holder, int position) {
        holder.im_pizza.setImageResource(options.get(position).getImage());
        holder.tv_name.setText(options.get(position).getName());
        holder.tv_desciption.setText(options.get(position).getDescription());
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
     * ViewHolder class for the OptionAdapter. Holds references to the views in a single row item,
     * including the name, description, image, and parent layout. Handles click events to navigate
     * to the OptionSelectedActivity.
     */
    public static class OptionHolder extends RecyclerView.ViewHolder {
        private TextView tv_name, tv_desciption;
        private ImageView im_pizza;
        private ConstraintLayout parentLayout;

        /**
         * Initializes the ViewHolder by binding views and setting up click listeners.
         *
         * @param itemView The view for a single row item in the RecyclerView.
         */
        public OptionHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_desciption = itemView.findViewById(R.id.tv_description);
            im_pizza = itemView.findViewById(R.id.im_pizza);
            parentLayout = itemView.findViewById(R.id.rowLayout);

            parentLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(itemView.getContext(),OptionSelectedActivity.class);
                    intent.putExtra("TYPE",tv_name.getText());
                    itemView.getContext().startActivity(intent);
                }
              });
        }
    }



}
