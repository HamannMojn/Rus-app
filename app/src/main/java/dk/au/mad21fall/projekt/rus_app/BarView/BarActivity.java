package dk.au.mad21fall.projekt.rus_app.BarView;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import dk.au.mad21fall.projekt.rus_app.DrinksView.DrinksActivity;
import dk.au.mad21fall.projekt.rus_app.LeaderBoardView.LeaderBoardActivity;
import dk.au.mad21fall.projekt.rus_app.R;
import dk.au.mad21fall.projekt.rus_app.TutorView.TutorActivity;

public class BarActivity extends AppCompatActivity {

    Button drinksBtn, tutorBtn, leaderboardBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar);

        drinksBtn = findViewById(R.id.gotoDrinksBtn);
        tutorBtn = findViewById(R.id.gotoTutorBtn);
        leaderboardBtn = findViewById(R.id.gotoLeaderboardBtn);

        drinksBtn.setOnClickListener(view -> drinks());
        tutorBtn.setOnClickListener(view -> tutor());
        leaderboardBtn.setOnClickListener(view -> leaderboard());
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