package dk.au.mad21fall.projekt.rus_app.PersonalTabView;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dk.au.mad21fall.projekt.rus_app.Models.Purchases;
import dk.au.mad21fall.projekt.rus_app.R;

public class PersonalTabActivity extends AppCompatActivity {
    private PersonalTabAdapter adapter;
    private PersonalTabActivityViewModel viewModel;
    private RecyclerView rcvPurchases;
    private ArrayList<Purchases> tutorPurchaces = new ArrayList<>();
    private Button backBtn;
    private TextView fullAmount;
    private Double fullPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_tab);

        adapter = new PersonalTabAdapter(tutorPurchaces);
        rcvPurchases = findViewById(R.id.rvcTab);
        rcvPurchases.setLayoutManager(new LinearLayoutManager(this));
        rcvPurchases.setAdapter(adapter);

        fullPrice = 0.0;

        viewModel = new ViewModelProvider(this).get(PersonalTabActivityViewModel.class);
        viewModel.getPurchases().observe(this, new Observer<ArrayList<Purchases>>() {
            @Override
            public void onChanged(ArrayList<Purchases> purchases) {
                adapter.updateList(purchases);
                fullPrice = 0.0;
                for(Purchases p : purchases) {
                    fullPrice += p.getValue() * p.getAmount();
                }
            }
        });

        backBtn = findViewById(R.id.btnCancelTab);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        fullAmount = findViewById(R.id.txtFullAmount);
        fullAmount.setText(Double.toString(fullPrice));
    }
}