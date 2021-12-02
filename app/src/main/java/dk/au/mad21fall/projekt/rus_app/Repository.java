package dk.au.mad21fall.projekt.rus_app;

import android.app.Application;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import dk.au.mad21fall.projekt.rus_app.Models.Drinks;

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
    //MutableLiveData<ArrayList<Purchaces>> purchaces;
    MutableLiveData<ArrayList<Drinks>> drinks;
    MutableLiveData<Tutor> tutor;

    public Repository() {
        tutors = new MutableLiveData<>();
        teams = new MutableLiveData<>();
        //purchaces = new MutableLiveData<>();
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

    public MutableLiveData<ArrayList<Tutor>> getTutors() {
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
        return tutors;
    }

    public MutableLiveData<ArrayList<Team>> getTeams() {
        db.collection("teams").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshot, @Nullable FirebaseFirestoreException error) {
                ArrayList<Team> updatedTeams = new ArrayList<>();
                if(snapshot!=null && !snapshot.isEmpty()){
                    for(DocumentSnapshot doc : snapshot.getDocuments()){
                        Team t = doc.toObject(Team.class);
                        if(t!=null) {
                            updatedTeams.add(t);
                        }
                    }
                    teams.setValue(updatedTeams);
                }
            }
        });
        return teams;
    }

}
