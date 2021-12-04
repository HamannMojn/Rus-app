package dk.au.mad21fall.projekt.rus_app.AddDrinkToTutorView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import dk.au.mad21fall.projekt.rus_app.Models.Drinks;
import dk.au.mad21fall.projekt.rus_app.R;

public class AddDrinkToTutorAdapter extends RecyclerView.Adapter<AddDrinkToTutorAdapter.AddDrinksViewHolder>{

    public interface DrinkItemClickedListener{
        void onDrinkItemClicked(Drinks drinks);
    }

    private DrinkItemClickedListener listener;
    private ArrayList<Drinks> DrinksList = new ArrayList<>();

    public AddDrinkToTutorAdapter(ArrayList<Drinks> addDrinkViewHolder){
        DrinksList = addDrinkViewHolder;
    }

    public void updateDrinksList(ArrayList<Drinks> list){
        DrinksList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AddDrinksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dialog_items, parent, false);
        AddDrinkToTutorAdapter.AddDrinksViewHolder vh = new AddDrinkToTutorAdapter.AddDrinksViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull AddDrinksViewHolder holder, int position) {
        Drinks getCurrentDrink = DrinksList.get(position);

        holder.txtDrinkName.setText(getCurrentDrink.getName());
    }


    @Override
    public int getItemCount() {
        return DrinksList.size();
    }

    public class AddDrinksViewHolder extends RecyclerView.ViewHolder{
        public TextView txtDrinkName;

        public AddDrinksViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDrinkName = itemView.findViewById(R.id.bar_DrinkName);

        }
    }

    public void setOnItemClickListener(AddDrinkToTutorAdapter.DrinkItemClickedListener listener){
        this.listener = listener;
    }

    public void setDrink(ArrayList<Drinks> drinks){
        this.DrinksList = drinks;
        notifyDataSetChanged();
    }
}
