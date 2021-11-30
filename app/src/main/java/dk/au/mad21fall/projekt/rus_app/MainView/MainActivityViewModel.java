package dk.au.mad21fall.projekt.rus_app.MainView;

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
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import dk.au.mad21fall.projekt.rus_app.Models.Tutor;

public class MainActivityViewModel extends ViewModel {

    MutableLiveData<ArrayList<Tutor>> tutors;

    public LiveData<ArrayList<Tutor>> getTutors() {
        if(tutors == null) {
            loadData();
        }
        return tutors;
    }

    private void loadData() {
        tutors = new MediatorLiveData<ArrayList<Tutor>>();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

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

    public void addRandomFag() {
        Random random = new Random();
        String[] firstNames = new String[]{"Morten", "Magnus", "Jonas", "Kamilla"};
        String[] lastNames = new String[]{"Overgaard", "Christensen", "Kærgaarden", "Kristensen"};
        String[] tutorNames = new String[]{"Volde", "Hold dig væk fra baren", "Bobby", "Ødipus^2"};

        int myRandomNumber = random.nextInt(firstNames.length);

        String first = firstNames[myRandomNumber];
        String last = lastNames[myRandomNumber];
        String tutor = tutorNames[myRandomNumber];

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("tutors").add(new Tutor(first, last, tutor, "FUCK DIG!"))
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                       Log.d("VIEWMODEL", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w("VIEWMODEL", "Error added", e);
            }
        });
    }
}
