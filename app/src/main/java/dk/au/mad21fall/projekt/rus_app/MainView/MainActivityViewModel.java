package dk.au.mad21fall.projekt.rus_app.MainView;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import dk.au.mad21fall.projekt.rus_app.Models.Team;
import dk.au.mad21fall.projekt.rus_app.Models.Tutor;
import dk.au.mad21fall.projekt.rus_app.NotificationService;
import dk.au.mad21fall.projekt.rus_app.Repository;

public class MainActivityViewModel extends AndroidViewModel {
    String TAG = "MainActivityViewModel";
    Repository repo;
    MutableLiveData<Tutor> tutor;
    Context context;

    public MainActivityViewModel(Application application) {
        super(application);
        repo = Repository.getRepository(application);
        tutor = repo.getCurrentTutor();
        context = application.getApplicationContext();
    }

    public void StartService(){
        Intent intent = new Intent(context, NotificationService.class);
        context.startService(intent);
    }

    public LiveData<Tutor> getCurrentTutor() {
        return tutor;
    }
}
