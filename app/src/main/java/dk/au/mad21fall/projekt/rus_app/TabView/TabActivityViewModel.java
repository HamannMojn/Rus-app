package dk.au.mad21fall.projekt.rus_app.TabView;

import android.app.Application;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import dk.au.mad21fall.projekt.rus_app.Models.Purchases;
import dk.au.mad21fall.projekt.rus_app.Models.Tutor;
import dk.au.mad21fall.projekt.rus_app.Repository;

public class TabActivityViewModel extends AndroidViewModel {
    String TAG = "LeaderboardActivityViewModel";
    MutableLiveData<ArrayList<Purchases>> purchaces;
    Repository repo;

    public TabActivityViewModel(Application application) {
        super(application);
        repo = Repository.getRepository(application);
        Log.d(TAG,"Getting all purchases from repo");
        purchaces = repo.getPurchases();
    }

    public LiveData<ArrayList<Purchases>> getPurchases() {
        return purchaces;
    }
}
