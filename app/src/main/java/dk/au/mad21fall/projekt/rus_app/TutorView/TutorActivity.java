package dk.au.mad21fall.projekt.rus_app.TutorView;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import dk.au.mad21fall.projekt.rus_app.Models.Tutor;
import dk.au.mad21fall.projekt.rus_app.R;

public class TutorActivity extends AppCompatActivity {
    private String TAG = "TUTOR_VIEW";
    private TutorActivityViewModel tutorsViewModel;
    private ArrayList<Tutor> displayTutors = new ArrayList<>();
    private TextView txtMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor);

        tutorsViewModel = new ViewModelProvider(this).get(TutorActivityViewModel.class);

        tutorsViewModel.getTutors().observe(this, new Observer<ArrayList<Tutor>>() {
            @Override
            public void onChanged(ArrayList<Tutor> tutors) {
                displayTutors = tutors;
                updateUI();
            }
        });

        txtMain = findViewById(R.id.txtMain);

        updateUI();
    }

    private void updateUI() {
        if(displayTutors.size() > 0) {
            String display = "";
            for (Tutor t : displayTutors) {
                Log.d(TAG, "added: " + t.getFirstName());
                display += t.getFirstName() + " " + t.getLastName() + " " + t.getTutorName() + "\n";
            }
            txtMain.setText(display);
        } else {
            txtMain.setText("hello sir, no tutors here OK?");
        }
    }


}