package dk.au.mad21fall.projekt.rus_app.AddDrinkToTutorView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import javax.annotation.Nullable;

import dk.au.mad21fall.projekt.rus_app.DrinksView.DrinksAdapter;
import dk.au.mad21fall.projekt.rus_app.Models.Drinks;
import dk.au.mad21fall.projekt.rus_app.R;

public class AddDrinkToTutorActivity extends AppCompatActivity {
    String TAG = "ADDDRINKSTOTUTOR";
    private Button btnReturnToMain;
    private RecyclerView rcvDrinksList;
    private DrinksAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private AddDrinkToTutorViewModel viewModel;

    //Data
    int count = 0;
    private ArrayList<Drinks> drinksList = new ArrayList<>();
    public String tutorName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adddrinktotutor);
        Intent data = getIntent();
        tutorName = data.getStringExtra("intent_id");
        Log.d(TAG, "onCreate: TutorId" + tutorName);

        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(AddDrinkToTutorViewModel.class);
        viewModel.getAllDrinks().observe(this, new Observer<ArrayList<Drinks>>() {
            @Override
            public void onChanged(@Nullable ArrayList<Drinks> drinks) {
                adapter.setDrink(drinks);
                adapter.updateDrinkList(drinks);
            }
        });
        setupUi();
    }

    //This setup is setting the recyclerview up with the DrinksAdapter, because they have the same view.
    private void setupUi() {
        //Adapter + Recyclerview setup
        adapter = new DrinksAdapter(drinksList);
        rcvDrinksList = findViewById(R.id.AddDrinksRcvView);
        rcvDrinksList.setLayoutManager(new GridLayoutManager(this, 3));
        rcvDrinksList.setAdapter(adapter);

        adapter.setOnItemClickListener(new DrinksAdapter.IDrinkItemClickedListener() {
            @Override
            public void onDrinkClicked(Drinks drinks) {
                Log.d(TAG, "onDrinkClicked: " + drinks.getName());
                AddDrinkToTutorDialog(drinks);
            }
        });

        //Button Setup
        btnReturnToMain = findViewById(R.id.btnReturnToMain);
        btnReturnToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    //Creating and opening, the Incement/Decrement dialog
    private void AddDrinkToTutorDialog(Drinks drink){
        AlertDialog.Builder builder = new AlertDialog.Builder(AddDrinkToTutorActivity.this, R.style.Theme_AppCompat_Dialog);
        final View AddDrinkToTutorDialog = getLayoutInflater().inflate(R.layout.tutor_addrink_list_item, null);
        builder.setView(AddDrinkToTutorDialog);
        builder.setTitle(drink.getName());
        final int[] amount = {0};
        TextView AmountCounter = AddDrinkToTutorDialog.findViewById(R.id.bar_DrinksAmount);
        Button IncrementButton = AddDrinkToTutorDialog.findViewById(R.id.add_drinkToTutor);
        Button DecrementButton = AddDrinkToTutorDialog.findViewById(R.id.remove_DrinkFromTutor);

        IncrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                amount[0]++;
                AmountCounter.setText(amount[0] + "");
                Log.d(TAG, "Increment onClick: " + amount[0]);
            }
        });

        DecrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(amount[0] == 0) {
                    Toast.makeText(AddDrinkToTutorDialog.getContext(), "TMP_You can't subtract from zero!", Toast.LENGTH_SHORT).show();
                }
                else{
                    amount[0]--;
                    AmountCounter.setText(amount[0] + "");}

                Log.d(TAG, "Decrement onClick: " + amount[0]);
            }
        });

        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(), "Cancel pressed", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                addPurchase(drink.getName(), amount[0], drink.getPrice());
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    //Adding a purchase to the database
    private void addPurchase(String drinkName, int amount, double drinkPrice) {
        viewModel.AddPurchase(drinkName, tutorName, amount, drinkPrice);
    }

}
