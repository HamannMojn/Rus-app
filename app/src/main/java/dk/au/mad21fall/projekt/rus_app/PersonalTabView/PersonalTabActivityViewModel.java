package dk.au.mad21fall.projekt.rus_app.PersonalTabView;

import android.app.Application;
import android.util.Log;

import java.util.ArrayList;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import dk.au.mad21fall.projekt.rus_app.Models.Purchases;
import dk.au.mad21fall.projekt.rus_app.Models.Team;
import dk.au.mad21fall.projekt.rus_app.Models.Tutor;
import dk.au.mad21fall.projekt.rus_app.Repository;

public class PersonalTabActivityViewModel extends AndroidViewModel {
    String TAG = "LeaderboardActivityViewModel";
    MutableLiveData<ArrayList<Purchases>> purchaces;
    Repository repo;

    public PersonalTabActivityViewModel(Application application) {
        super(application);
        repo = Repository.getRepository(application);
        Log.d(TAG,"Getting all purchases from repo");
        Tutor currentTutor = repo.getCurrentTutor();
        purchaces = repo.getPurchasesByTutor(currentTutor.getTutorName());
    }

    public LiveData<ArrayList<Purchases>> getPurchases() {
        return purchaces;
    }
}
