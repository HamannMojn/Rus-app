package dk.au.mad21fall.projekt.rus_app.DrinksView;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

//import com.bumptech.glide.Glide;

import dk.au.mad21fall.projekt.rus_app.R;
import dk.au.mad21fall.projekt.rus_app.Models.Drinks;

import java.util.ArrayList;
import java.util.List;

/*public class DrinksAdapter extends RecyclerView.Adapter<DrinksAdapter.DrinkViewHolder>
{
    //interface for handling when a Person item is clicked
    public interface IDrinkItemClickedListener
    {
        void onDrinkClicked(Drinks drinks);
    }

    //Callback interface for user actions on each item
    private IDrinkItemClickedListener listener;

    //Data in the adapter
    private List<Drinks> DrinkList = new ArrayList<>();

    //constructor
    public DrinksAdapter(ArrayList<Drinks> drinkViewHolder)
    {
        DrinkList = drinkViewHolder;
    }

    //A method for updating the list - Causes the adapter/recyclerview to update
    public void updateDrinkList(ArrayList<Drinks> lists)
    {
        DrinkList = lists;
        notifyDataSetChanged();
    }


    //override this method to create the viewholder object first time they are requested
    //Using the inflater from parent, to get the view and then use view holders constructor
    @NonNull
    @Override
    public DrinkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        DrinkViewHolder vh = new DrinkViewHolder(v);
        return vh;
    }

    //override this to fill in data from Drinks object at position into the view holder
    @Override
    public void onBindViewHolder(@NonNull DrinkViewHolder holder, int position)
    {
        Drinks currentDrink = DrinkList.get(position);

        //String MoviePosterURL = DrinkList.get(position).getPosterURL();
        //Glide.with(holder.imgIcon.getContext()).load(currentMovie.getPosterURL()).into(holder.imgIcon);

        holder.txtDrinkName.setText(currentDrink.getName());
        holder.txtDrinkPrice.setText(currentDrink.getPrice() + ".kr");
    }

    //returns size of DrinkList
    @Override
    public int getItemCount()
    {
        return DrinkList.size();
    }

    //the ViewHolder class for holding info about each list item in the RecyclerView
    public class DrinkViewHolder extends RecyclerView.ViewHolder
    {
        //viewholder  ui widget  refrences
        public ImageView imgIcon;
        public TextView txtDrinkName;
        public TextView txtDrinkPrice;

        //custom callback interface for user actions done to the view holder item
        //IDrinkItemClickedListener listener;

        //Constructor of DrinkViewHolder
        public DrinkViewHolder (@NonNull View itemView)
        {
            super(itemView);

            //get refrences from layoutfile
            imgIcon = itemView.findViewById(R.id.imgDrink);
            txtDrinkName = itemView.findViewById(R.id.txtDrinkName);
            txtDrinkPrice = itemView.findViewById(R.id.txtDrinkPrice);


            //Set Click listener for whole list item
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view)
                {
                    Log.d("MainApp", "Hej " + listener);
                    if(listener != null)
                    {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION)
                        {
                            listener.onDrinkClicked(DrinkList.get(position));
                        }
                    }
                }
            });
        }
    }

    public void setOnItemClickListener(IDrinkItemClickedListener listener) { this.listener = listener; }

    public void setDrink(List<Drinks> drinks)
    {
        this.DrinkList = drinks;
        notifyDataSetChanged();
    }
}*/
