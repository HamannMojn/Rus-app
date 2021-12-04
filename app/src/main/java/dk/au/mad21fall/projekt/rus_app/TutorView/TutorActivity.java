package dk.au.mad21fall.projekt.rus_app.TutorView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dk.au.mad21fall.projekt.rus_app.AddTutorView.AddTutorActivity;
import dk.au.mad21fall.projekt.rus_app.DrinksView.DrinksActivity;
import dk.au.mad21fall.projekt.rus_app.Models.Drinks;
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
                Log.d(TAG, "edit tutor: " + tutor.getFirstName());
                CreateEditDrinkDialog(tutor);
            }
        });
    }

    private void CreateEditDrinkDialog(Tutor tutor) {
        AlertDialog.Builder builder = new AlertDialog.Builder(TutorActivity.this, R.style.Theme_AppCompat_Dialog);
        final View editDialog = getLayoutInflater().inflate(R.layout.dialog_edit_tutor, null);
        builder.setView(editDialog);
        builder.setTitle("Edit Tutor");

        //Find views
        EditText firstname = editDialog.findViewById(R.id.txtDialogEditFirstname);
        firstname.setText(tutor.getFirstName());
        EditText lastname = editDialog.findViewById(R.id.txtDialogEditLastname);
        lastname.setText(tutor.getLastName());
        EditText tutorName = editDialog.findViewById(R.id.txtDialogEditTutorName);
        tutorName.setText(tutor.getTutorName());
        EditText email = editDialog.findViewById(R.id.txtDialogEditEmail);
        email.setText(tutor.getEmail());
        ImageView image = editDialog.findViewById(R.id.imgDialogEditTutor);
        Glide.with(image.getContext()).load(tutor.getTutorImage()).into(image);


        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(), "Cancel pressed", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Toast.makeText(getApplicationContext(), "Cancel pressed", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //createConfrimDialog(drink);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}