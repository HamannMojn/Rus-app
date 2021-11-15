package dk.au.mad21fall.projekt.rus_app.MainView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.Button;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import dk.au.mad21fall.projekt.rus_app.Models.Instructor;
import dk.au.mad21fall.projekt.rus_app.R;

public class MainActivity extends AppCompatActivity {
    private GridView grid;
    private InstructorAdapter adapter;
    private MainActivityViewModel viewModel;
    private ArrayList<Instructor> instructors;
    private Button beverageBtn, leaderboardBtn, instructorsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        grid = findViewById(R.id.grid);
        adapter = new InstructorAdapter(instructors, this);
        grid.setAdapter(adapter);

        viewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory
                        .getInstance(this.getApplication()))
                .get(MainActivityViewModel.class);
        viewModel.getInstructors().observe(this, instructors -> adapter.updateInstructorList(instructors));

        //Add onClickListener to InstructorView

        //Buttons
        beverageBtn = findViewById(R.id.mainBeverageBtn);
        leaderboardBtn = findViewById(R.id.mainLeaderboardBtn);
        instructorsBtn = findViewById(R.id.mainInstructorsBtn);
    }
}