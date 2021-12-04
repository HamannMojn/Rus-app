package dk.au.mad21fall.projekt.rus_app.TutorView;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dk.au.mad21fall.projekt.rus_app.AddTutorView.AddTutorActivity;
import dk.au.mad21fall.projekt.rus_app.DrinksView.DrinksActivity;
import dk.au.mad21fall.projekt.rus_app.Models.Tutor;
import dk.au.mad21fall.projekt.rus_app.R;

public class TutorActivity extends AppCompatActivity {
    private String TAG = "TUTOR_VIEW";
    private TutorActivityViewModel tutorsViewModel;
    private ArrayList<Tutor> displayTutors = new ArrayList<>();
    private RecyclerView recyclerView;
    private TutorAdapter tutorAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private Button addBtn;
    private Button backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor);

        tutorsViewModel = new ViewModelProvider(this).get(TutorActivityViewModel.class);
        recyclerView = findViewById(R.id.rcvDrinks);
        addBtn = findViewById(R.id.btnAdd);
        backBtn = findViewById(R.id.btnBack);

        buildRecyclerView();

        tutorsViewModel.getTutors().observe(this, new Observer<ArrayList<Tutor>>() {
            @Override
            public void onChanged(ArrayList<Tutor> tutors) {
                displayTutors = tutors;
                tutorAdapter.Tutor(displayTutors);
                changeScreen();
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTutor();
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    void addTutor() {
        Intent drinksActivity = new Intent(this, AddTutorActivity.class);
        startActivity(drinksActivity);
    }

    void changeScreen() {
        if (displayTutors.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    void buildRecyclerView() {
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        tutorAdapter = new TutorAdapter(displayTutors);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(tutorAdapter);

        tutorAdapter.setOnItemClickListener(new TutorAdapter.TutorItemClickedListener() {
            @Override
            public void onTutorClicked(Tutor tutor) {

            }
        });
    }
}