package dk.au.mad21fall.projekt.rus_app.TutorView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import dk.au.mad21fall.projekt.rus_app.Models.Tutor;
import dk.au.mad21fall.projekt.rus_app.R;

public class TutorAdapter extends RecyclerView.Adapter<TutorAdapter.TutorViewHolder> {
    public interface TutorItemClickedListener
    {
        void onTutorClicked(Tutor tutor);
    }

    private ArrayList<Tutor> TutorList = new ArrayList<>();
    private TutorItemClickedListener listener;

    public TutorAdapter(ArrayList<Tutor> tutorViewHolder) {
        TutorList = tutorViewHolder;
    }

    @NonNull
    @Override
    public TutorAdapter.TutorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.tutor_list_item, parent, false);
        TutorAdapter.TutorViewHolder vh = new TutorAdapter.TutorViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull TutorViewHolder holder, int position) {
        Tutor currentTutor = TutorList.get(position);

        Glide.with(holder.imgIcon.getContext()).load(currentTutor.getTutorImage()).into(holder.imgIcon);
        holder.txtTutorName.setText(currentTutor.getTutorName());
    }

    @Override
    public int getItemCount() {
        return TutorList.size();
    }

    public class TutorViewHolder extends RecyclerView.ViewHolder
    {
        public ImageView imgIcon;
        public TextView txtTutorName;

        public TutorViewHolder (@NonNull View itemView)
        {
            super(itemView);

            imgIcon = itemView.findViewById(R.id.imgTutor);
            txtTutorName = itemView.findViewById(R.id.txtTutorName);


            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view)
                {
                    if(listener != null)
                    {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION)
                        {
                            listener.onTutorClicked(TutorList.get(position));
                        }
                    }
                }
            });
        }
    }

    public void setOnItemClickListener(TutorAdapter.TutorItemClickedListener listener) { this.listener = listener; }

    public void Tutor(ArrayList<Tutor> tutors)
    {
        this.TutorList = tutors;
        Log.d("MY_PP", "" + this.TutorList.size());
        notifyDataSetChanged();
    }
}
