package dk.au.mad21fall.projekt.rus_app.TutorView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

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
    private ImageView dialogImage;
    private TutorAdapter tutorAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private Button addBtn;
    private Button backBtn;
    private int PICK_IMAGE_REQUEST = 28364;
    private Uri filePath;
    private String imageUrl;
    FirebaseStorage storage;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

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
        dialogImage = editDialog.findViewById(R.id.imgDialogEditTutor);
            Glide.with(dialogImage.getContext()).load(tutor.getTutorImage()).into(dialogImage);

        dialogImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });

        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d(TAG, "Cancel pressed in dialog");
            }
        });

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d(TAG, "Save pressed in dialog");
                if(!firstname.getText().toString().isEmpty()) {
                    tutor.setFirstName(firstname.getText().toString());
                }
                if(!lastname.getText().toString().isEmpty()) {
                    tutor.setLastName(lastname.getText().toString());
                }
                if(!tutorName.getText().toString().isEmpty()) {
                    tutor.setTutorName(tutorName.getText().toString());
                }
                if(!email.getText().toString().isEmpty()) {
                    tutor.setEmail(email.getText().toString());
                }
                if(!imageUrl.isEmpty()) {
                    tutor.setTutorImage(imageUrl);
                }

                tutorsViewModel.editTutor(tutor);
            }
        });

        builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                createConfrimDialog(tutor);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void createConfrimDialog(Tutor tutor) {
        AlertDialog.Builder builder = new AlertDialog.Builder(TutorActivity.this, R.style.Theme_AppCompat_Dialog);
        builder.setTitle("Are you sure you want to delete " + tutor.getFirstName());

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d(TAG, "Delete tutor canceled");
            }
        });

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d(TAG, "Delete tutor canceled");
                tutorsViewModel.deleteTutor(tutor);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
        }

        uploadImage();
    }

    // UploadImage method
    private void uploadImage()
    {
        if (filePath != null) {

            // Code for showing progressDialog while uploading
            ProgressDialog progressDialog
                    = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            // Defining the child of storageReference
            StorageReference ref
                    = storageReference
                    .child(
                            "images/"
                                    + UUID.randomUUID().toString());

            // adding listeners on upload
            // or failure of image
            ref.putFile(filePath)
                    .addOnSuccessListener(
                            new OnSuccessListener<UploadTask.TaskSnapshot>() {

                                @Override
                                public void onSuccess(
                                        UploadTask.TaskSnapshot taskSnapshot)
                                {
                                    progressDialog.dismiss();
                                    Toast
                                            .makeText(TutorActivity.this,
                                                    "Image Uploaded!!",
                                                    Toast.LENGTH_SHORT)
                                            .show();
                                    taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(
                                            new OnCompleteListener<Uri>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Uri> task) {
                                                    imageUrl = task.getResult().toString();
                                                    Glide.with(dialogImage.getContext()).load(imageUrl).into(dialogImage);
                                                    Log.d(TAG, "URL: " + imageUrl);
                                                }
                                            });
                                }
                            })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {

                            // Error, Image not uploaded
                            progressDialog.dismiss();
                            Toast
                                    .makeText(TutorActivity.this,
                                            "Failed " + e.getMessage(),
                                            Toast.LENGTH_SHORT)
                                    .show();
                        }
                    })
                    .addOnProgressListener(
                            new OnProgressListener<UploadTask.TaskSnapshot>() {

                                // Progress Listener for loading
                                // percentage on the dialog box
                                @Override
                                public void onProgress(
                                        UploadTask.TaskSnapshot taskSnapshot)
                                {
                                    double progress
                                            = (100.0
                                            * taskSnapshot.getBytesTransferred()
                                            / taskSnapshot.getTotalByteCount());
                                    progressDialog.setMessage(
                                            "Uploaded "
                                                    + (int)progress + "%");
                                }
                            });
        }
    }
}