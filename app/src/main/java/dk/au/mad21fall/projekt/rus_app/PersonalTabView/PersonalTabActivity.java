package dk.au.mad21fall.projekt.rus_app.PersonalTabView;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import dk.au.mad21fall.projekt.rus_app.MainView.MainActivity;
import dk.au.mad21fall.projekt.rus_app.Models.Purchases;
import dk.au.mad21fall.projekt.rus_app.Models.Tutor;
import dk.au.mad21fall.projekt.rus_app.R;

public class PersonalTabActivity extends AppCompatActivity {
    private PersonalTabAdapter adapter;
    private PersonalTabActivityViewModel viewModel;
    private RecyclerView rcvPurchases;
    private Tutor currentTutor = new Tutor();
    private ArrayList<Purchases> tutorPurchaces = new ArrayList<>();
    private Button backBtn, btnSignOut;
    private TextView fullAmount;
    private Double fullPrice;
    private String TAG = "PersonalTabActivity";
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_tab);

        adapter = new PersonalTabAdapter(tutorPurchaces);
        rcvPurchases = findViewById(R.id.rvcTabTutor);
        rcvPurchases.setLayoutManager(new LinearLayoutManager(this));
        rcvPurchases.setAdapter(adapter);

        fullPrice = 0.0;

        fullAmount = findViewById(R.id.txtFullAmountTab);

        viewModel = new ViewModelProvider(this).get(PersonalTabActivityViewModel.class);

        viewModel.getCurrentTutor().observe(this, new Observer<Tutor>() {
            @Override
            public void onChanged(Tutor tutor) {
                currentTutor = tutor;
            }
        });

        viewModel.getPurchases(currentTutor.getTutorName()).observe(this, new Observer<ArrayList<Purchases>>() {
            @Override
            public void onChanged(ArrayList<Purchases> purchases) {
                adapter.updateList(purchases);
                fullPrice = 0.0;
                for(Purchases p : purchases) {
                    fullPrice += p.getDrinkPrice() * p.getAmount();
                }
                Log.d(TAG, "Full price: " + fullPrice);
                fullAmount.setText("I Alt: " + Double.toString(fullPrice) + " kr");
            }
        });

        backBtn = findViewById(R.id.btnBackTab);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnSignOut = findViewById(R.id.btnSignOut);
        btnSignOut.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                SignOut();
            }
        });
    }

    private void SignOut() {
        if(auth == null){
            auth = FirebaseAuth.getInstance();
        }
        auth.signOut();
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}