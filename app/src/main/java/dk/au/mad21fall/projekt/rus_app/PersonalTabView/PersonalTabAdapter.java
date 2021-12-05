package dk.au.mad21fall.projekt.rus_app.PersonalTabView;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import dk.au.mad21fall.projekt.rus_app.Models.Purchases;
import dk.au.mad21fall.projekt.rus_app.R;

public class PersonalTabAdapter extends RecyclerView.Adapter<PersonalTabAdapter.PersonalTabViewHolder> {
    private List<Purchases> purchases = new ArrayList<>();
    private String TAG = "PersonalTabAdapter";

    public PersonalTabAdapter(ArrayList<Purchases> _purchases) {
        purchases = _purchases;
    }

    @NonNull
    @Override
    public PersonalTabAdapter.PersonalTabViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.tab_list_item, parent, false);
        return new PersonalTabAdapter.PersonalTabViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonalTabAdapter.PersonalTabViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Purchases purchace = purchases.get(position);
        int placement = position+1;

        holder.drinkName.setText(purchace.getDrinkName());
        holder.price.setText(Double.toString(purchace.getDrinkPrice()) + " kr");
        holder.amount.setText(Integer.toString(purchace.getAmount()) + " stk");
        holder.fullPrice.setText(Double.toString(purchace.getDrinkPrice() * purchace.getAmount()) + " kr");
    }

    @Override
    public int getItemCount() {
        return purchases.size();
    }

    public void updateList(List<Purchases> list){
        this.purchases = list;
        notifyDataSetChanged();
    }

    public class PersonalTabViewHolder extends RecyclerView.ViewHolder {
        TextView drinkName, price, amount, fullPrice;

        public PersonalTabViewHolder(@NonNull View itemView) {
            super(itemView);

            drinkName = itemView.findViewById(R.id.txtDrinkNameTab);
            price = itemView.findViewById(R.id.txtTabDrinkPrice);
            amount = itemView.findViewById(R.id.txtDrinkAmount);
            fullPrice = itemView.findViewById(R.id.txtTabTotalPrice);
        }
    }
}

