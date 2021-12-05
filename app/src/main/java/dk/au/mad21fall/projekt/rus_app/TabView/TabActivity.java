package dk.au.mad21fall.projekt.rus_app.TabView;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dk.au.mad21fall.projekt.rus_app.Models.Purchases;
import dk.au.mad21fall.projekt.rus_app.PersonalTabView.PersonalTabActivityViewModel;
import dk.au.mad21fall.projekt.rus_app.PersonalTabView.PersonalTabAdapter;
import dk.au.mad21fall.projekt.rus_app.R;

public class TabActivity extends AppCompatActivity {
    private PersonalTabAdapter adapter;
    private TabActivityViewModel viewModel;
    private ArrayList<Purchases> purchaces = new ArrayList<>();
    private Double fullPrice;
    private RecyclerView rcvPurchases;
    private Button backBtn;
    private TextView fullAmount;
    private String TAG = "PersonalTabActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        adapter = new PersonalTabAdapter(purchaces);
        rcvPurchases = findViewById(R.id.rvcTabTutor);
        rcvPurchases.setLayoutManager(new LinearLayoutManager(this));
        rcvPurchases.setAdapter(adapter);

        fullPrice = 0.0;

        fullAmount = findViewById(R.id.txtFullAmountTab);

        viewModel = new ViewModelProvider(this).get(TabActivityViewModel.class);
        viewModel.getPurchases().observe(this, new Observer<ArrayList<Purchases>>() {
            @Override
            public void onChanged(ArrayList<Purchases> purchases) {
                Collections.sort(purchases, new Comparator<Purchases>() {
                    public int compare(Purchases p1, Purchases p2) {
                        if(p1.getTutorName() != null && p2.getTutorName() != null) {
                            return p1.getTutorName().compareTo(p2.getTutorName());
                        } else {
                            return 0;
                        }
                    }
                });
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
    }
}