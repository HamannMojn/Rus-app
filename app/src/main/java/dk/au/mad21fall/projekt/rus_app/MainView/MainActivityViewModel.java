package dk.au.mad21fall.projekt.rus_app.MainView;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import dk.au.mad21fall.projekt.rus_app.Models.Instructor;

public class MainActivityViewModel  extends AndroidViewModel {
    private LiveData<List<Instructor>> instructors;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        //GetInstructorsFromFirebase
        //AssignToLiveData
    }

    LiveData<List<Instructor>> getInstructors() {return instructors;}
}
