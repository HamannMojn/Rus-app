package dk.au.mad21fall.projekt.rus_app.AddTutorView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

import androidx.lifecycle.ViewModelProvider;
import dk.au.mad21fall.projekt.rus_app.DrinksView.DrinksActivityViewModel;
import dk.au.mad21fall.projekt.rus_app.Models.Tutor;
import dk.au.mad21fall.projekt.rus_app.R;

public class AddTutorActivity extends AppCompatActivity {
    private Button addBtn, btnCancel;
    private TextView txtAddTutorName, txtAddFirstname, txtAddLastName, txtAddEmail;
    private ImageView imgProfileImage;
    private CheckBox checkBoxAdmin;
    private String imageUrl;

    private AddTutorActivityViewModel tutorViewModel;

    private int PICK_IMAGE_REQUEST = 132435;
    private Uri filePath;
    FirebaseStorage storage;
    StorageReference storageReference;

    private String TAG = "AddTutorActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tutor);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        tutorViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(AddTutorActivityViewModel.class);

        addBtn = findViewById(R.id.btnAddTutor);
        btnCancel = findViewById(R.id.btnCancel);
        txtAddEmail = findViewById(R.id.txtAddEmail);
        txtAddFirstname = findViewById(R.id.txtAddFirstname);
        txtAddLastName = findViewById(R.id.txtAddLastname);
        txtAddTutorName = findViewById(R.id.txtAddTutorName);
        imgProfileImage = findViewById(R.id.imgTutorImageUpload);
        checkBoxAdmin = findViewById(R.id.checkBoxAdmin);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        imgProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addUser();
            }
        });
    }

    private void addUser() {
        if(txtAddTutorName.getText().toString().isEmpty() || txtAddLastName.getText().toString().isEmpty() || txtAddEmail.getText().toString().isEmpty() || txtAddFirstname.getText().toString().isEmpty() || filePath.toString().isEmpty()) {
            Toast.makeText(AddTutorActivity.this,
                    "Please fill out all fields",
                    Toast.LENGTH_SHORT)
                    .show();
        } else {
            Tutor newTutor = new Tutor(txtAddFirstname.getText().toString(), txtAddLastName.getText().toString(), txtAddTutorName.getText().toString(), txtAddEmail.getText().toString(), checkBoxAdmin.isChecked(), imageUrl);
            tutorViewModel.addTutor(newTutor);
            finish();
        }
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
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imgProfileImage.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
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
                                            .makeText(AddTutorActivity.this,
                                                    "Image Uploaded!!",
                                                    Toast.LENGTH_SHORT)
                                            .show();
                                    taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(
                                            new OnCompleteListener<Uri>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Uri> task) {
                                                    imageUrl = task.getResult().toString();
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
                                    .makeText(AddTutorActivity.this,
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