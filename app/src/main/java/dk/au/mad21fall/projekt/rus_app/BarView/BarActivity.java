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

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import dk.au.mad21fall.projekt.rus_app.AddDrinkToTutorView.AddDrinkToTutorActivity;
import dk.au.mad21fall.projekt.rus_app.DrinksView.DrinksActivity;
import dk.au.mad21fall.projekt.rus_app.LeaderBoardView.LeaderBoardActivity;
import dk.au.mad21fall.projekt.rus_app.MainView.MainActivity;
import dk.au.mad21fall.projekt.rus_app.Models.Tutor;
import dk.au.mad21fall.projekt.rus_app.R;
import dk.au.mad21fall.projekt.rus_app.TabView.TabActivity;
import dk.au.mad21fall.projekt.rus_app.TutorView.TutorActivity;

public class BarActivity extends AppCompatActivity {

    private Button drinksBtn, tutorBtn, leaderboardBtn, BtnSignOut;
    private RecyclerView barView;
    private BarActivityViewModel barViewModel;
    private ArrayList<Tutor> displayTutors = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private BarAdapter barAdapter;
    Tutor currentTutor = new Tutor();
    FirebaseAuth auth;

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
        removeButtons();

        BtnSignOut = findViewById(R.id.BtnSignOutTutor);

        BtnSignOut.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                SignOut();
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

        barViewModel.getCurrentTutor().observe(this, new Observer<Tutor>() {
            @Override
            public void onChanged(Tutor tutor) {
                currentTutor = tutor;
            }
        });
    }

    private void SignOut() {
        if(auth == null){
            auth = FirebaseAuth.getInstance();
        }
        auth.signOut();
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
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

    private void removeButtons(){
        Log.d("balalala", "balalala" + currentTutor.isAdmin());
        if(currentTutor.isAdmin()){
            tutorBtn.setVisibility(View.GONE);
            drinksBtn.setVisibility(View.GONE);
        }
        else{
            tutorBtn.setVisibility(View.VISIBLE);
            drinksBtn.setVisibility(View.VISIBLE);
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
                Intent intentDrinkToTutor = new Intent(BarActivity.this, AddDrinkToTutorActivity.class);
                intentDrinkToTutor.putExtra("intent_id",tutor.getTutorName());
                Log.d("TAG", "onBarClicked: " + tutor.getTutorName());
                startActivity(intentDrinkToTutor);
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