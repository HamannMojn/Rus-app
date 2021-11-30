package dk.au.mad21fall.projekt.rus_app.TutorView;

import android.app.Application;
import android.util.Log;

import java.util.ArrayList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import dk.au.mad21fall.projekt.rus_app.Models.Tutor;
import dk.au.mad21fall.projekt.rus_app.Repository;

public class TutorActivityViewModel extends ViewModel {
    MutableLiveData<ArrayList<Tutor>> tutors;
    Repository repo;
    String TAG = "TutorActivityViewModel";

    public TutorActivityViewModel() {
        repo = new Repository();
    }

    public LiveData<ArrayList<Tutor>> getTutors() {
        Log.d(TAG, "Getting all tutors from repo");
        tutors = repo.getTutors();
        return tutors;
    }

}
