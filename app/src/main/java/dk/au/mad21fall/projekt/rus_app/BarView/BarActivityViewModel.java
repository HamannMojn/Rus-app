package dk.au.mad21fall.projekt.rus_app.BarView;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.ViewModel;

import dk.au.mad21fall.projekt.rus_app.Repository;

public class BarActivityViewModel extends ViewModel {
    Repository repo;
    String TAG = "DrinksActivityViewModel";

    public void RequestFromApi(String drinkName, Context context) {
        Log.d(TAG, "RequestFromApi: Requesting drink" + drinkName);
        repo.RequestDrinkFromAPI(drinkName, context);
    }
}

