package dk.au.mad21fall.projekt.rus_app.TabView;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dk.au.mad21fall.projekt.rus_app.Models.Purchases;
import dk.au.mad21fall.projekt.rus_app.Models.purchaseForTutor;
import dk.au.mad21fall.projekt.rus_app.PersonalTabView.PersonalTabAdapter;
import dk.au.mad21fall.projekt.rus_app.R;

public class TabAdapter extends RecyclerView.Adapter<TabAdapter.TabViewHolder> {
    private ArrayList<purchaseForTutor> purchases = new ArrayList<>();
    private String TAG = "PersonalTabAdapter";
    private PersonalTabAdapter adapter;
    private RecyclerView recyclerView;

    public TabAdapter(ArrayList<purchaseForTutor> _purchases) {
        purchases = _purchases;
    }

    @NonNull
    @Override
    public TabAdapter.TabViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.tab_item, parent, false);
        return new TabAdapter.TabViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TabAdapter.TabViewHolder holder, @SuppressLint("RecyclerView") int position) {
        purchaseForTutor purchace = purchases.get(position);
        int placement = position+1;

        holder.rcvPurchases.setLayoutManager(new LinearLayoutManager(this));
        holder.rcvPurchases.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return purchases.size();
    }

    public void updateList(ArrayList<purchaseForTutor> list){
        this.purchases = list;
        notifyDataSetChanged();
    }

    public class TabViewHolder extends RecyclerView.ViewHolder {
        TextView tutorName;
        PersonalTabAdapter adapter;
        RecyclerView rcvPurchases;
        ArrayList<Purchases> purchases = new ArrayList<>();

        public TabViewHolder(@NonNull View itemView) {
            super(itemView);

            tutorName = itemView.findViewById(R.id.txtTutorTabName);

            adapter = new PersonalTabAdapter(purchases);
            rcvPurchases = itemView.findViewById(R.id.rctTutorTab);
        }
    }
}
