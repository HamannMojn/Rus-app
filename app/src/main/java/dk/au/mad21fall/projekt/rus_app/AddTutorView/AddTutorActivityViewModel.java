package dk.au.mad21fall.projekt.rus_app.AddTutorView;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;
import dk.au.mad21fall.projekt.rus_app.Models.Drinks;
import dk.au.mad21fall.projekt.rus_app.Models.Tutor;
import dk.au.mad21fall.projekt.rus_app.Repository;

public class AddTutorActivityViewModel extends AndroidViewModel {
    private Repository repository;

    public AddTutorActivityViewModel(@NonNull Application application) {
        super(application);
        repository = Repository.getRepository(application);
    }

    public void addTutor(Tutor tutor) {
        repository.addTutor(tutor);
    }
}
