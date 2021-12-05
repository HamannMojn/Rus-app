package dk.au.mad21fall.projekt.rus_app.AddDrinkToTutorView;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import dk.au.mad21fall.projekt.rus_app.Models.Drinks;
import dk.au.mad21fall.projekt.rus_app.Models.Purchases;
import dk.au.mad21fall.projekt.rus_app.Repository;

public class AddDrinkToTutorViewModel extends AndroidViewModel {
    private Repository repository;
    private LiveData<Drinks> drinks;
    private MutableLiveData<ArrayList<Drinks>> allDrinks;

    public AddDrinkToTutorViewModel(@NonNull Application application) {
        super(application);
        repository = Repository.getRepository(application);
        allDrinks = repository.getDrinks();
    }

    public LiveData<ArrayList<Drinks>> getDrinks() {
        allDrinks = repository.getDrinks();
        return allDrinks;
    }

    public LiveData<ArrayList<Drinks>> getAllDrinks(){return allDrinks;}

    public void AddPurchase(String drinkId, String tutorID,int amount) {repository.AddPurchase(drinkId,tutorID,amount);
    }

}
