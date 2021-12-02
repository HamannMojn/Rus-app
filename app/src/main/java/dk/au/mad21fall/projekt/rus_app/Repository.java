package dk.au.mad21fall.projekt.rus_app;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.json.JSONObject;

import dk.au.mad21fall.projekt.rus_app.Models.Drinks;

import dk.au.mad21fall.projekt.rus_app.Models.Team;
import dk.au.mad21fall.projekt.rus_app.Models.Tutor;

public class Repository {
    public static Repository instance = null;
    String TAG = "REPO";

    //firebase authentication and firestore
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    MutableLiveData<ArrayList<Tutor>> tutors;

    MutableLiveData<ArrayList<Team>> teams;
    //MutableLiveData<ArrayList<Purchaces>> purchaces;
    MutableLiveData<ArrayList<Drinks>> drinks;
    MutableLiveData<Tutor> tutor;

    //API
    private RequestQueue queue;
    //private Context context;

    public Repository() {
        tutors = new MutableLiveData<>();
        teams = new MutableLiveData<>();
        //purchaces = new MutableLiveData<>();
        drinks = new MutableLiveData<>();
        tutor = new MutableLiveData<>();
        //this.context = context;
    }

    public static Repository getRepository(Application application) {
        if(instance == null)
        {
            instance = new Repository();
        }
        return instance;
    }

    public MutableLiveData<ArrayList<Tutor>> getTutors() {
        db.collection("tutors").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshot, @Nullable FirebaseFirestoreException error) {
                ArrayList<Tutor> updatedTutors = new ArrayList<>();
                if(snapshot!=null && !snapshot.isEmpty()){
                    for(DocumentSnapshot doc : snapshot.getDocuments()){
                        Tutor t = doc.toObject(Tutor.class);
                        if(t!=null) {
                            updatedTutors.add(t);
                        }
                    }
                    tutors.setValue(updatedTutors);
                }
            }
        });
        return tutors;
    }

    public void RequestDrinkFromAPI(String drinkName, Context context)
    {
        queue = Volley.newRequestQueue(context.getApplicationContext());
        String ReqUrl = "https://www.thecocktaildb.com/api/json/v1/1/search.php?s=" + drinkName;
        Log.d(TAG, "RequestDrinkFromAPI: Before request " + ReqUrl);
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, ReqUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, "Response: " + response);
                addDrink(drinkFromJSON(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: Error");
            }
        });
        Log.d(TAG,"Before queueing api request " + jsonRequest);
        queue.add(jsonRequest);
    }

    //Lægger response ind i en Drinks model
    private Drinks drinkFromJSON(JSONObject response) {
        Drinks drinkItem = new Drinks();
        drinkItem.setPrice(0.0);
        try{
            drinkItem.setName(response.getJSONArray("drinks").getJSONObject(0).getString("strDrink"));
            drinkItem.setThumbnailURL(response.getJSONArray("drinks").getJSONObject(0).getString("strDrinkThumb"));
        }
        catch (org.json.JSONException e) {
            Log.e(TAG, "Error: " + e);
        }

        Log.d(TAG, "drinkFromJSON:" + drinkItem.getName() + drinkItem.getThumbnailURL());
        return drinkItem;
    }

    //This function add drinks to the database
    public void addDrink(Drinks drink) {
        Log.d(TAG, "AddDrink: Adding drink: " + drink);
        db.collection("drinks").add(drink);
    }

    public void editDrink(Drinks drink) {
    }

    public MutableLiveData<ArrayList<Drinks>> getDrinks() {
        return null;
    }
}
