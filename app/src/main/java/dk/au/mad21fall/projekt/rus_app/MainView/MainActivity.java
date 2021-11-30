package dk.au.mad21fall.projekt.rus_app.MainView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import dk.au.mad21fall.projekt.rus_app.R;
import dk.au.mad21fall.projekt.rus_app.Models.Tutor;

public class MainActivity extends AppCompatActivity {
    private MainActivityViewModel viewModel;
    Button SodMenPek;
    ArrayList<Tutor> displayTutors = new ArrayList<>();
    TextView txtMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        viewModel.getTutors().observe(this, new Observer<ArrayList<Tutor>>() {
            @Override
            public void onChanged(ArrayList<Tutor> tutors) {
                displayTutors = tutors;
            }
        });
        txtMain = findViewById(R.id.txtMain);
        SodMenPek = findViewById(R.id.btnAdd);
        SodMenPek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add();
                updateUI();
            }
        });

    }

    private void add() {
        viewModel.addRandomFag();
    }

    private void updateUI(){
        if(displayTutors.size() < 1) {
            txtMain.setText("Morten er grim");
        } else {
            String display = ("Morten er stadig grim, men der er " + displayTutors.size() + " tutors\n");
            for(Tutor t : displayTutors){
                display += "\n" + t.getTutorName() + " " + t.getFirstName() + " " + t.getLastName();
            }
            txtMain.setText(display);
        }


    }
}