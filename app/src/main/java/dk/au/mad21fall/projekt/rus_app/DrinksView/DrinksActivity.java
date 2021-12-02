package dk.au.mad21fall.projekt.rus_app.DrinksView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import dk.au.mad21fall.projekt.rus_app.Models.Drinks;
import dk.au.mad21fall.projekt.rus_app.R;

public class DrinksActivity extends AppCompatActivity {


    //widgets
    private Button btnBack, btnAddDrink;
    private TextView txtAddDrink;
    private DrinksAdapter adapter;
    private RecyclerView rcvList;

    //Dependencies
    private DrinksActivityViewModel drinkViewModel;

    //Data
    private ArrayList<Drinks> drinks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drinks);

        drinkViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(DrinksActivityViewModel.class);
        drinkViewModel.getAllDrinks().observe(this, new Observer<ArrayList<Drinks>>() {
            @Override
            public void onChanged(ArrayList<Drinks> drinks) {adapter.setDrink(drinks);}
        });

        adapter.updateDrinkList(drinks);
        adapter = new DrinksAdapter(drinks);
        setupUi();
    }

    private void setupUi() {
        //Find widgets
        btnBack = findViewById(R.id.btnGoBack);
        btnAddDrink = findViewById(R.id.btnAddDrink);
        txtAddDrink = findViewById(R.id.txtAddDrink);
        rcvList = findViewById(R.id.rcvDrinks);
        rcvList.setAdapter(adapter);

        //Setup buttons
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnAddDrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txtAddDrink.getText().toString().isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "You have to enter a drink", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    drinkViewModel.requestDrinkFromAPI(txtAddDrink.getText().toString(), getApplicationContext());
                    Log.d("MainApp","Onclick: AddedMovie");
                }
            }
        });
    }
}