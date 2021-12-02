package dk.au.mad21fall.projekt.rus_app.DrinksView;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import dk.au.mad21fall.projekt.rus_app.Models.Drinks;
import dk.au.mad21fall.projekt.rus_app.Repository;

public class DrinksActivityViewModel extends AndroidViewModel {
private Repository repository;
private LiveData<Drinks> drinks;
private MutableLiveData<ArrayList<Drinks>> allDrinks;

    public DrinksActivityViewModel(@NonNull Application application) {
        super(application);
        repository = Repository.getRepository(application);
        allDrinks = repository.getDrinks();
    }

    public LiveData<ArrayList<Drinks>> getAllDrinks(){return allDrinks;}

    public void addDrink(Drinks drink) { repository.addDrink(drink);
    }

    public void editDrink(Drinks drink){
        repository.editDrink(drink);
    }

    public void requestDrinkFromAPI(String drinkName, Context context){ repository.RequestDrinkFromAPI(drinkName, context);}
}
