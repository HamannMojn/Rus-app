package dk.au.mad21fall.projekt.rus_app.AddDrinkToTutorView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
    private Button btnPlus, btnMinus;
    private TextView txtAmount;

    int count = 0;

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

        btnMinus = (Button) v.findViewById(R.id.remove_DrinkFromTutor);
        btnPlus = (Button) v.findViewById(R.id.add_drinkToTutor);
        txtAmount = v.findViewById(R.id.bar_DrinksAmount);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull AddDrinksViewHolder holder, int position) {
        Drinks getCurrentDrink = DrinksList.get(position);
        holder.txtAmount.setText(getCurrentDrink.getAmount());

        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;
                getCurrentDrink.setAmount(String.valueOf(count));
                Log.d("Count", String.valueOf(count));
                getCurrentDrink.getAmount();
            }
        });

        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count--;
                getCurrentDrink.setAmount(String.valueOf(count));
                Log.d("Count", String.valueOf(count));
            }
        });

        holder.txtDrinkName.setText(getCurrentDrink.getName());
    }


    @Override
    public int getItemCount() {
        return DrinksList.size();
    }

    public class AddDrinksViewHolder extends RecyclerView.ViewHolder{
        public TextView txtDrinkName;
        public TextView txtAmount;


        public AddDrinksViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDrinkName = itemView.findViewById(R.id.bar_DrinkName);
            txtAmount = itemView.findViewById(R.id.bar_DrinksAmount);
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
