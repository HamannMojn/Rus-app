package dk.au.mad21fall.projekt.rus_app.AddDrinkToTutorView;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import dk.au.mad21fall.projekt.rus_app.Models.Drinks;
import dk.au.mad21fall.projekt.rus_app.Repository;

public class AddDrinkToTutorViewModel extends ViewModel {
    private Repository repository;
    private LiveData<Drinks> drinks;
    private MutableLiveData<ArrayList<Drinks>> allDrinks;
    public AddDrinkToTutorViewModel() {
        repository = new Repository();
    }

    public LiveData<ArrayList<Drinks>> getDrinks() {
        allDrinks = repository.getDrinks();
        return allDrinks;
    }

}
