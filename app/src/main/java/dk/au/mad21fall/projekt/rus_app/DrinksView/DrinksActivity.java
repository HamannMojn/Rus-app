package dk.au.mad21fall.projekt.rus_app.DrinksView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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

import java.util.ArrayList;
import java.util.UUID;

import javax.annotation.Nullable;

import dk.au.mad21fall.projekt.rus_app.Models.Drinks;
import dk.au.mad21fall.projekt.rus_app.R;

public class DrinksActivity extends AppCompatActivity {
    String TAG = "DRINKSACTIVITY";
    private int PICK_IMAGE_REQUEST = 28364;


    //widgets
    private Button btnBack, btnAddDrink;
    private TextView txtAddDrink;
    private DrinksAdapter adapter;
    private RecyclerView rcvList;
    private ImageView image;

    //Dependencies
    private DrinksActivityViewModel drinkViewModel;
    FirebaseStorage storage;
    StorageReference storageReference;

    //Data
    private ArrayList<Drinks> drinks = new ArrayList<>();
    private Uri filePath;
    private String imageUrl;
    private boolean thumbnailChanged = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drinks);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        drinkViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(DrinksActivityViewModel.class);
        drinkViewModel.getAllDrinks().observe(this, new Observer<ArrayList<Drinks>>() {
            @Override
            public void onChanged(@Nullable ArrayList<Drinks> drinks) {
                adapter.setDrink(drinks);
                adapter.updateDrinkList(drinks);
            }
        });

        setupUi();
    }

    private void setupUi() {
        //Find widgets
        btnBack = findViewById(R.id.btnGoBack);
        btnAddDrink = findViewById(R.id.btnAddDrink);
        txtAddDrink = findViewById(R.id.txtAddName);

        //Setting up adapter
        adapter = new DrinksAdapter(drinks);
        rcvList = findViewById(R.id.rcvDrinks);
        rcvList.setLayoutManager(new GridLayoutManager(this, 3));
        rcvList.setAdapter(adapter);


        adapter.setOnItemClickListener(new DrinksAdapter.IDrinkItemClickedListener() {
            @Override
            public void onDrinkClicked(Drinks drinks) {
                Log.d(TAG, "onDrinkClicked: " + drinks.getName());
                CreateEditDrinkDialog(drinks);
            }
        });

        //Setup buttons
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //This button has two functions
        //The first is to open a dialog, to add a drink without the api
        //The second is to lookup in the api for a drink from name
        btnAddDrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txtAddDrink.getText().toString().isEmpty())
                {
                    CreateAddDrinkDialog();
                }
                else
                {
                    drinkViewModel.requestDrinkFromAPI(txtAddDrink.getText().toString(), getApplicationContext());
                }
            }
        });
    }

    private void CreateEditDrinkDialog(Drinks drink) {
        AlertDialog.Builder builder = new AlertDialog.Builder(DrinksActivity.this, R.style.Theme_AppCompat_Dialog);
        final View editDialog = getLayoutInflater().inflate(R.layout.dialog_adddrink, null);
        builder.setView(editDialog);
        builder.setTitle("TMP_Edit Drink");

        //Find views
        EditText drinkName = editDialog.findViewById(R.id.txtDialogEditFirstname);
        drinkName.setText(drink.getName());
        EditText drinkPrice = editDialog.findViewById(R.id.txtDialogEditPrice);
        drinkPrice.setText(drink.getPrice() +"");
        image = editDialog.findViewById(R.id.imgDialogAddDrink);

        Glide.with(image.getContext()).load(drink.getThumbnailURL()).into(image);


        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(), "Cancel pressed", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Drinks tempdrink = drink;
                tempdrink.setName(drinkName.getText().toString());
                tempdrink.setPrice(Double.parseDouble(drinkPrice.getText().toString()));
                if(thumbnailChanged == true)
                {
                    tempdrink.setThumbnailURL(imageUrl);
                    thumbnailChanged = false;
                }
                drinkViewModel.editDrink(tempdrink);
            }
        });

        builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                createConfrimDialog(drink);
            }
        });

        ImageView drinkImage = editDialog.findViewById(R.id.imgDialogAddDrink);
        drinkImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    //Open Dialog to add new drink without using the API
    private void CreateAddDrinkDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(DrinksActivity.this, R.style.Theme_AppCompat_Dialog);
        final View editDialog = getLayoutInflater().inflate(R.layout.dialog_adddrink, null);
        builder.setView(editDialog);
        builder.setTitle("TMP_Add Drink");

        image = editDialog.findViewById(R.id.imgDialogAddDrink);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                EditText drinkName = editDialog.findViewById(R.id.txtDialogEditFirstname);
                EditText drinkPrice = editDialog.findViewById(R.id.txtDialogEditPrice);

                Drinks newDrink = new Drinks();
                try {
                    newDrink.setName(drinkName.getText().toString());
                    newDrink.setPrice(Double.parseDouble(drinkPrice.getText().toString()));
                    newDrink.setThumbnailURL(imageUrl);
                    drinkViewModel.addDrink(newDrink);
                }catch(NumberFormatException e){
                    Toast.makeText(getApplicationContext(), "Can't add empty drink", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(), "Cancel pressed", Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void createConfrimDialog(Drinks drink) {
        AlertDialog.Builder builder = new AlertDialog.Builder(DrinksActivity.this, R.style.Theme_AppCompat_Dialog);
        builder.setTitle("TMP_Are you sure you want to delete " + drink.getName());

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(), "Cancel pressed", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                drinkViewModel.deleteDrink(drink);
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
                                            .makeText(DrinksActivity.this,
                                                    "Image Uploaded!!",
                                                    Toast.LENGTH_SHORT)
                                            .show();
                                    taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(
                                            new OnCompleteListener<Uri>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Uri> task) {
                                                    imageUrl = task.getResult().toString();
                                                    Glide.with(image.getContext()).load(imageUrl).into(image);
                                                    thumbnailChanged = true;
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
                                    .makeText(DrinksActivity.this,
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