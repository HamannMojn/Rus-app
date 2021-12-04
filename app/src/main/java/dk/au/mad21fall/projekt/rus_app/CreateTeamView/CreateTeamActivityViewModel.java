package dk.au.mad21fall.projekt.rus_app.CreateTeamView;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import dk.au.mad21fall.projekt.rus_app.Models.Team;
import dk.au.mad21fall.projekt.rus_app.Repository;

public class CreateTeamActivityViewModel extends AndroidViewModel {

    private Repository repo;

    public CreateTeamActivityViewModel(@NonNull Application application) {
        super(application);
        repo = Repository.getRepository(application);
    }

    public void addTeam(Team team) { repo.addTeam(team);}

}
