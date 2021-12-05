package dk.au.mad21fall.projekt.rus_app.LeaderBoardView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
    private ITeamBtnClickedListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);

        adapter = new LeaderboardAdapter(teamsList, listener);
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
        adapter.setOnDeleteClickListener(new ITeamBtnClickedListener() {
            @Override
            public void onDeleteClicked(Team team) {
                createConfrimDialog(team);
            }

            @Override
            public void onTeamClicked(Team team) {
                CreateEditDialog(team);
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

    private void CreateEditDialog(Team team){
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Theme_AppCompat_Dialog);
        final View editDialog = getLayoutInflater().inflate(R.layout.dialog_edit_team, null);
        builder.setView(editDialog);
        builder.setTitle("Edit Team");

        TextView title = editDialog.findViewById(R.id.teamDialogTitle);
        title.setText(team.getName());

        TextView amount = editDialog.findViewById(R.id.teamDialogAmount);
        amount.setText(String.valueOf(team.getAmount()));


        Button increment = editDialog.findViewById(R.id.teamDialogIncrementBtn);
        increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                team.incrementAmount();
                amount.setText(Integer.toString(team.getAmount()));
            }
        });

        Button decrement = editDialog.findViewById(R.id.teamDialogDecrementBtn);
        decrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                team.incrementAmount();
                amount.setText(Integer.toString(team.getAmount()));
            }
        });

        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d("TEAMDIALOG", "Cancel pressed in dialog");
            }
        });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                leaderboardViewModel.updateTeam(team);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void createConfrimDialog(Team team) {
        AlertDialog.Builder builder = new AlertDialog.Builder(LeaderBoardActivity.this, R.style.Theme_AppCompat_Dialog);
        builder.setTitle("Are you sure you want to delete " + team.getName());

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d("LEADERBOARD", "Delete team canceled");
            }
        });

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d("LEADERBOARD", "Delete tutor Succesfulk");
                leaderboardViewModel.deleteTeam(team);
                adapter.remove(team);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public interface ITeamBtnClickedListener
    {
        void onDeleteClicked(Team team);
        void onTeamClicked(Team team);
    }
}