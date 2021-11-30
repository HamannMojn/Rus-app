package dk.au.mad21fall.projekt.rus_app.DrinksView;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import dk.au.mad21fall.projekt.rus_app.Models.Drinks;
import dk.au.mad21fall.projekt.rus_app.Repository;

public class DrinksActivityViewModel extends AndroidViewModel {
private Repository repository;
private LiveData<Drinks> drinks;

    public DrinksActivityViewModel(@NonNull Application application) {
        super(application);
        repository = Repository.getRepository(application);
        allDrinks = repository.getDrinks();
    }

    LiveData<List<Drinks>> getAllDrinks(){return allDrinks;}

    public void addDrink(Drinks drink) {
        repository.addDrink(drink);
    }

    public void editDrink(Drinks drink){
        repository.editDrink(drink);
    }

    public void requestDrinkFromAPI(String drinkName){repository.getDrinkFromApi(drinkName, false);}
}
