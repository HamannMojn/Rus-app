package dk.au.mad21fall.projekt.rus_app.LeaderBoardView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import dk.au.mad21fall.projekt.rus_app.CreateTeamView.CreateTeamActivity;
import dk.au.mad21fall.projekt.rus_app.Models.Team;
import dk.au.mad21fall.projekt.rus_app.R;

public class LeaderBoardActivity extends AppCompatActivity {
    private LeaderBoardActivityViewModel leaderboardViewModel;
    private LeaderboardAdapter adapter;
    private RecyclerView rcvTeams;
    private ArrayList<Team> teamsList = new ArrayList<>();
    private Button backBtn, createBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);

        adapter = new LeaderboardAdapter(teamsList);
        rcvTeams = findViewById(R.id.leaderboardList);
        rcvTeams.setLayoutManager(new LinearLayoutManager(this));
        rcvTeams.setAdapter(adapter);

        leaderboardViewModel = new ViewModelProvider(this).get(LeaderBoardActivityViewModel.class);
        leaderboardViewModel.getTeams().observe(this, new Observer<ArrayList<Team>>() {
            @Override
            public void onChanged(ArrayList<Team> teams) {
                adapter.updateList(teams);
            }
        });

        backBtn = findViewById(R.id.leaderboardBackBtn);
        createBtn = findViewById(R.id.leaderboardBtnCreateTeam);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { finish(); }
        });

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LeaderBoardActivity.this, CreateTeamActivity.class);
                startActivity(intent);
            }
        });
    }
}