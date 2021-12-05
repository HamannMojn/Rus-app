package dk.au.mad21fall.projekt.rus_app.BarView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import dk.au.mad21fall.projekt.rus_app.AddDrinkToTutorView.AddDrinkToTutorActivity;
import dk.au.mad21fall.projekt.rus_app.DrinksView.DrinksActivity;
import dk.au.mad21fall.projekt.rus_app.LeaderBoardView.LeaderBoardActivity;
import dk.au.mad21fall.projekt.rus_app.Models.Tutor;
import dk.au.mad21fall.projekt.rus_app.R;
import dk.au.mad21fall.projekt.rus_app.TutorView.TutorActivity;

public class BarActivity extends AppCompatActivity {

    private Button drinksBtn, tutorBtn, leaderboardBtn;
    private RecyclerView barView;
    private BarActivityViewModel barViewModel;
    private ArrayList<Tutor> displayTutors = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private BarAdapter barAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar);

        barViewModel = new ViewModelProvider(this).get(BarActivityViewModel.class);

        drinksBtn = findViewById(R.id.gotoDrinksBtn);
        tutorBtn = findViewById(R.id.gotoTutorBtn);
        leaderboardBtn = findViewById(R.id.gotoLeaderboardBtn);
        recyclerView = findViewById(R.id.barTutorView);
        barView = findViewById(R.id.barTutorView);

        drinksBtn.setOnClickListener(view -> drinks());
        tutorBtn.setOnClickListener(view -> tutor());
        leaderboardBtn.setOnClickListener(view -> leaderboard());

        buildRecyclerView();

        barAdapter.setOnItemClickListener(new BarAdapter.BarItemClickedListener() {
            @Override
            public void onBarClicked(Tutor tutor) {
                addDrinksToTutor();
            }
        });

        barViewModel.getTutors().observe(this, new Observer<ArrayList<Tutor>>() {
            @Override
            public void onChanged(ArrayList<Tutor> tutors) {
                displayTutors = tutors;
                barAdapter.Tutor(displayTutors);
                changeScreen();
            }
        });
    }

    private void addDrinksToTutor() {
        Intent addDrinksToTutorActivity = new Intent(this, AddDrinkToTutorActivity.class);
        startActivity(addDrinksToTutorActivity);
    }

    private void changeScreen() {
        if(displayTutors.isEmpty())
        {
            recyclerView.setVisibility(View.GONE);
        }
        else{
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    void buildRecyclerView(){
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(this,3 );
        barAdapter = new BarAdapter(displayTutors);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(barAdapter);

        barAdapter.setOnItemClickListener(new BarAdapter.BarItemClickedListener() {
            @Override
            public void onBarClicked(Tutor tutor) {

            }
        });
    }



    private void drinks(){
        Intent drinksActivity = new Intent(this, DrinksActivity.class);
        startActivity(drinksActivity);
    }

    private void tutor(){
        Intent tutorActivity = new Intent(this, TutorActivity.class);
        startActivity(tutorActivity);
    }

    private void leaderboard(){
        Intent leaderboardActivity = new Intent(this, LeaderBoardActivity.class);
        startActivity(leaderboardActivity);
    }

}