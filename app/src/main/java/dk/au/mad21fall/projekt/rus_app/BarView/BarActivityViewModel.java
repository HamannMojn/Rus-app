package dk.au.mad21fall.projekt.rus_app.BarView;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import dk.au.mad21fall.projekt.rus_app.Models.Tutor;
import dk.au.mad21fall.projekt.rus_app.Repository;

public class BarActivityViewModel extends ViewModel {
    MutableLiveData<ArrayList<Tutor>> tutors;
    MutableLiveData<Tutor> tutor;
    Repository repo;
    String TAG = "BarActivityViewModel";

    public BarActivityViewModel() {
        repo = new Repository();
        tutor = repo.getCurrentTutor();
    }

    public LiveData<ArrayList<Tutor>> getTutors() {
        Log.d(TAG, "Getting all tutors from repo");
        tutors = repo.getTutors();
        return tutors;
    }

    public LiveData<Tutor> getCurrentTutor(){
        return tutor;
    }

}

