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
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
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
    boolean isTutor;

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

    public boolean getCurrentUserIsTutor() {
        FirebaseUser firebaseUser = auth.getCurrentUser();
        isTutor = false;

        db.collection("tutors").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshot, @Nullable FirebaseFirestoreException error) {
                if(snapshot!=null && !snapshot.isEmpty()){
                    for(DocumentSnapshot doc : snapshot.getDocuments()){
                        Tutor t = doc.toObject(Tutor.class);
                        t.setId(doc.getId());
                        if(t!=null) {
                            if(t.getEmail() == firebaseUser.getEmail()) {
                                isTutor = true;
                            }
                        }
                    }
                }
            }
        });

        return isTutor;
    }

    public boolean getCurrentUserIsAdmin() {
        FirebaseUser firebaseUser = auth.getCurrentUser();
        isTutor = false;

        db.collection("tutors").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshot, @Nullable FirebaseFirestoreException error) {
                if(snapshot!=null && !snapshot.isEmpty()){
                    for(DocumentSnapshot doc : snapshot.getDocuments()){
                        Tutor t = doc.toObject(Tutor.class);
                        t.setId(doc.getId());
                        if(t!=null) {
                            if(t.getEmail() == firebaseUser.getEmail() && t.isAdmin()) {
                                isTutor = true;
                            }
                        }
                    }
                }
            }
        });

        return isTutor;
    }

    public MutableLiveData<ArrayList<Tutor>> getTutors() {
        db.collection("tutors").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshot, @Nullable FirebaseFirestoreException error) {
                ArrayList<Tutor> updatedTutors = new ArrayList<>();
                if(snapshot!=null && !snapshot.isEmpty()){
                    for(DocumentSnapshot doc : snapshot.getDocuments()){
                        Tutor t = doc.toObject(Tutor.class);
                        t.setId(doc.getId());
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

    public MutableLiveData<ArrayList<Team>> getTeams() {
        db.collection("teams").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshot, @Nullable FirebaseFirestoreException error) {
                ArrayList<Team> updatedTeams = new ArrayList<>();
                if(snapshot!=null && !snapshot.isEmpty()){
                    for(DocumentSnapshot doc : snapshot.getDocuments()){
                        Team t = doc.toObject(Team.class);
                        t.setId(doc.getId());
                        Log.d("GETTEAMS", t.getId());
                        if(t!=null) {
                            updatedTeams.add(t);
                        }
                    }
                    teams.setValue(updatedTeams);
                }
            }
        });
        return teams;
    }
    public void deleteTeam(Team team){
        db.collection("teams").document(team.getId())
                .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d(TAG, "Succesfully deleted"+team.getName());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "Error deleting"+team.getName(), e);
            }
        });
    }
    public void addTeam(Team team) {
        Log.d(TAG, "AddTeam: Adding team: " + team.getName());
        db.collection("teams").add(team);
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
                addDrink(drinkFromJSON(response, context));
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
    private Drinks drinkFromJSON(JSONObject response, Context context) {
        Drinks drinkItem = new Drinks();
        drinkItem.setPrice(0.0);
        try{
            drinkItem.setName(response.getJSONArray("drinks").getJSONObject(0).getString("strDrink"));
            drinkItem.setThumbnailURL(response.getJSONArray("drinks").getJSONObject(0).getString("strDrinkThumb"));
        }
        catch (org.json.JSONException e) {
            Log.d(TAG, "Error: " + e);
            Toast.makeText(context, "Error adding drink", Toast.LENGTH_SHORT).show();
            return null;
        }

        Log.d(TAG, "drinkFromJSON:" + drinkItem.getName() + drinkItem.getThumbnailURL());
        return drinkItem;
    }

    //This function add drinks to the database
    public void addDrink(Drinks drink) {
        if(drink == null)
        {
            Log.d(TAG, "AddDrink: fail adding drink");
        }
        else
        {
            Log.d(TAG, "AddDrink: Adding drink: " + drink);
            db.collection("drinks").add(drink);
        }
    }

    public void addTutor(Tutor tutor) {
        db.collection("tutors").add(tutor)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "added tutor: " + tutor.getFirstName());
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "Adding tutor failed");
            }
        });
    }

    public void editTutor(Tutor tutor) {
        db.collection("tutors").document(tutor.getId())
                .set(tutor).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d(TAG, "updated tutor: " + tutor.getFirstName());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "updating tutor failed");
            }
        });
    }

    public void deleteTutor(Tutor tutor) {
        db.collection("tutors").document(tutor.getId())
                .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d(TAG, "Succesfully deleted "+tutor.getFirstName());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "Error deleting"+tutor.getFirstName(), e);
            }
        });
    }

    public void editDrink(Drinks drink) {
        db.collection("drinks").document(drink.getId()).set(drink);
    }

    public MutableLiveData<ArrayList<Drinks>> getDrinks() {
        db.collection("drinks").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshot, @Nullable FirebaseFirestoreException error) {
                ArrayList<Drinks> updatedDrinks = new ArrayList<>();
                if(snapshot!=null && !snapshot.isEmpty()){
                    for(DocumentSnapshot doc : snapshot.getDocuments()){
                        Drinks d = doc.toObject(Drinks.class);
                        d.setId(doc.getId());
                        if(d!=null) {
                            updatedDrinks.add(d);
                        }
                    }
                    drinks.setValue(updatedDrinks);
                }
            }
        });
        Log.d(TAG, "getDrinks: ");
        return drinks;
    }

    public void deleteDrink(Drinks drink) {
        db.collection("drinks").document(drink.getId())
                .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d(TAG, "Succesfully deleted"+drink.getName());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "Error deleting"+drink.getName(), e);
            }
        });
    }
}
