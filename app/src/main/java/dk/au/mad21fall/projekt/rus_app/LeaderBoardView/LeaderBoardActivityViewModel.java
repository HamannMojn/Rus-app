package dk.au.mad21fall.projekt.rus_app.LeaderBoardView;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collections;

import dk.au.mad21fall.projekt.rus_app.Models.Team;
import dk.au.mad21fall.projekt.rus_app.Repository;


public class LeaderBoardActivityViewModel extends AndroidViewModel {
    String TAG = "LeaderboardActivityViewModel";
    MutableLiveData<ArrayList<Team>> teams;
    Repository repo;

    public LeaderBoardActivityViewModel(Application application) {
        super(application);
        repo = Repository.getRepository(application);
        Log.d(TAG,"Getting all teams from repo");
        teams = repo.getTeams();
    }

    public LiveData<ArrayList<Team>> getTeams() {
        return teams;
    }

    public void deleteTeam(Team team) { repo.deleteTeam(team); }

}
