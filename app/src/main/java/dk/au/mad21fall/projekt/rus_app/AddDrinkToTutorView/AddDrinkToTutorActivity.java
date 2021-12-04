package dk.au.mad21fall.projekt.rus_app.AddDrinkToTutorView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import dk.au.mad21fall.projekt.rus_app.Models.Drinks;
import dk.au.mad21fall.projekt.rus_app.R;

public class AddDrinkToTutorActivity extends AppCompatActivity {
    private Button btnAddDrinks;
    private RecyclerView rcvDrinksList;
    private AddDrinkToTutorAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private AddDrinkToTutorViewModel viewModel;

    int count = 0;

    private ArrayList<Drinks> drinksList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adddrinktotutor);
        rcvDrinksList = findViewById(R.id.AddDrinksRcvView);
        viewModel = new ViewModelProvider(this).get(AddDrinkToTutorViewModel.class);

        buildRcv();

        //btnAddDrinks.setOnClickListener(view -> addDrinks());

        viewModel.getDrinks().observe(this, new Observer<ArrayList<Drinks>>() {
            @Override
            public void onChanged(ArrayList<Drinks> drinks) {
                drinksList = drinks;
                adapter.setDrink(drinks);
                adapter.updateDrinksList(drinks);
                changeScreen();
            }
        });
    }


    private void changeScreen() {
        if(drinksList.isEmpty()){
            rcvDrinksList.setVisibility(View.GONE);
        }
        else{
            rcvDrinksList.setVisibility(View.VISIBLE);
        }
    }

    private void buildRcv() {
        rcvDrinksList.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new AddDrinkToTutorAdapter(drinksList);
        rcvDrinksList.setLayoutManager(layoutManager);
        rcvDrinksList.setAdapter(adapter);

        adapter.setOnItemClickListener(new AddDrinkToTutorAdapter.DrinkItemClickedListener() {
            @Override
            public void onDrinkItemClicked(Drinks drinks) {

            }
        });
    }


    private void addDrinks() {
        Log.d("Tissemanden", "tilføjer tis");
    }

}
