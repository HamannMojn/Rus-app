package dk.au.mad21fall.projekt.rus_app;

import android.app.Application;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import dk.au.mad21fall.projekt.rus_app.Models.Drinks;
import dk.au.mad21fall.projekt.rus_app.Models.Purchaces;
import dk.au.mad21fall.projekt.rus_app.Models.Team;
import dk.au.mad21fall.projekt.rus_app.Models.Tutor;

public class Repository {
    public static Repository instance = null;
    String TAG = "REPO";

    //firebase authentication and firestore
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    MutableLiveData<ArrayList<Tutor>> tutors;
    MutableLiveData<ArrayList<Team>> teams;
    MutableLiveData<ArrayList<Purchaces>> purchaces;
    MutableLiveData<ArrayList<Drinks>> drinks;
    MutableLiveData<Tutor> tutor;

    public Repository() {
        tutors = new MutableLiveData<>();
        teams = new MutableLiveData<>();
        purchaces = new MutableLiveData<>();
        drinks = new MutableLiveData<>();
        tutor = new MutableLiveData<>();
    }

    public static Repository getRepository(Application application) {
        if(instance == null)
        {
            instance = new Repository();
        }
        return instance;
    }

    public void getTutors() {
        db.collection("tutors").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshot, @Nullable FirebaseFirestoreException error) {
                ArrayList<Tutor> updatedTutors = new ArrayList<>();
                if(snapshot!=null && !snapshot.isEmpty()){
                    for(DocumentSnapshot doc : snapshot.getDocuments()){
                        Tutor t = doc.toObject(Tutor.class);
                        if(t!=null) {
                            updatedTutors.add(t);
                        }
                    }
                    tutors.setValue(updatedTutors);
                }
            }
        });
    }
}
