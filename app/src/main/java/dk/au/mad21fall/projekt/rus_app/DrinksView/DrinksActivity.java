package dk.au.mad21fall.projekt.rus_app.DrinksView;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import dk.au.mad21fall.projekt.rus_app.R;

public class DrinksActivity extends AppCompatActivity {


    //widgets
    private Button btnBack;
    //Dependencies
    private DrinksActivityViewModel drinkViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drinks);
        
        setupUi();
    }

    private void setupUi() {
        btnBack = findViewById(R.id.btnGoBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    //THIS IS ONLY FOR MY OWN ASS TO REMEMBER
    private void RequestAPIStuff(String drinkName)
    {
        drinkViewModel.requestDrinkFromAPI(drinkName, getApplicationContext());
    }
}