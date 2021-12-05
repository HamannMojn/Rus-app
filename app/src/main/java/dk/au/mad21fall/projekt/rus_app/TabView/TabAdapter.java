package dk.au.mad21fall.projekt.rus_app.TabView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
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
    private String TAG = "TabAdapter";
    private PersonalTabAdapter adapter;
    private RecyclerView recyclerView;
    private Context context;

    public TabAdapter(ArrayList<purchaseForTutor> _purchases, Context _context) {
        purchases = _purchases;
        context = _context;
    }

    public Context getContext() {return this.getContext();}

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

        adapter.updateList(purchace.getPurchases());
        holder.rcvPurchases.setLayoutManager(new LinearLayoutManager(context));
        holder.rcvPurchases.setAdapter(adapter);
        holder.tutorName.setText(purchace.getTutorName());
    }

    @Override
    public int getItemCount() {
        return purchases.size();
    }

    public void updateList(ArrayList<purchaseForTutor> list){
        Log.d(TAG, "" + list.size());
        this.purchases = list;
        notifyDataSetChanged();
    }

    public class TabViewHolder extends RecyclerView.ViewHolder {
        TextView tutorName;
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
