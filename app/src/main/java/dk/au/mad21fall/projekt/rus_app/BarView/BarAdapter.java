package dk.au.mad21fall.projekt.rus_app.BarView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import dk.au.mad21fall.projekt.rus_app.Models.Tutor;
import dk.au.mad21fall.projekt.rus_app.R;
import dk.au.mad21fall.projekt.rus_app.TutorView.TutorAdapter;

public class BarAdapter extends RecyclerView.Adapter<BarAdapter.BarViewHolder> {
    public interface BarItemClickedListener{
        void onBarClicked(Tutor tutor);
    }

    private ArrayList<Tutor> BarList = new ArrayList<>();
    private BarItemClickedListener listener;

    public BarAdapter(ArrayList<Tutor> barViewHolder){
        BarList = barViewHolder;
    }

    @NonNull
    @Override
    public BarAdapter.BarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.tutor_list_item, parent, false);
        BarAdapter.BarViewHolder vh = new BarAdapter.BarViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull BarViewHolder holder, int position) {
        Tutor currentTutor = BarList.get(position);

        Glide.with(holder.imgIcon.getContext()).load(currentTutor.getTutorImage()).into(holder.imgIcon);
        holder.txtTutorName.setText(currentTutor.getTutorName());

    }

    @Override
    public int getItemCount() {
        return BarList.size();
    }

    public class BarViewHolder extends RecyclerView.ViewHolder{
        public ImageView imgIcon;
        public TextView txtTutorName;

        public BarViewHolder(@NonNull View itemView) {
            super(itemView);

            imgIcon = itemView.findViewById(R.id.imgTutor);
            txtTutorName = itemView.findViewById(R.id.txtTutorName);

            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onBarClicked(BarList.get(position));
                        }
                    }
                }
            });
        }
    }

    public void setOnItemClickListener(BarAdapter.BarItemClickedListener listener) { this.listener = listener; }

    public void Tutor(ArrayList<Tutor> tutors)
    {
        this.BarList = tutors;
        notifyDataSetChanged();
    }

}
