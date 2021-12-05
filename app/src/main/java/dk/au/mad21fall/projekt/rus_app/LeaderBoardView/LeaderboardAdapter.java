package dk.au.mad21fall.projekt.rus_app.LeaderBoardView;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dk.au.mad21fall.projekt.rus_app.Models.Team;
import dk.au.mad21fall.projekt.rus_app.R;

public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.LeaderboardViewHolder> {

    private List<Team> teams = new ArrayList<>();
    private LeaderBoardActivity.ITeamBtnClickedListener listener;



    public LeaderboardAdapter(ArrayList<Team> _teams, LeaderBoardActivity.ITeamBtnClickedListener _listener) {
        Collections.sort(_teams);
        teams = _teams;
        listener = _listener;
    }

    public void setOnDeleteClickListener(LeaderBoardActivity.ITeamBtnClickedListener listener ) {this.listener = listener; }

    @NonNull
    @Override
    public LeaderboardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.team_list_item, parent, false);
        return new LeaderboardViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull LeaderboardViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Team team = teams.get(position);
        Log.d(team.getName(), "FUCK DIS BITCH");
        int placement = position+1;

        holder.title.setText(team.getName());
        holder.cases.setText(Integer.toString(team.getAmount()));
        holder.placement.setText(String.valueOf(placement));
        holder.deleteBtn.setOnClickListener(view -> {
            if(listener != null){
                listener.onDeleteClicked(teams.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return teams.size();
    }

    public void updateList(List<Team> list){
        Collections.sort(list);
        this.teams = list;
        notifyDataSetChanged();
    }

    public void remove(Team team){
        int position = this.teams.indexOf(team);
        this.teams.remove(position);
        notifyItemRemoved(position);
    }

    public class LeaderboardViewHolder extends RecyclerView.ViewHolder {
        TextView title, placement, cases;
        ImageButton deleteBtn;

        public LeaderboardViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.txtDrinkNameTab);
            placement = itemView.findViewById(R.id.leaderboardTeamPlacement);
            cases = itemView.findViewById(R.id.leaderboardTeamCases);
            deleteBtn = itemView.findViewById(R.id.teamListItemDeleteBtn);

            title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onTeamClicked(teams.get(position));
                        }
                    }
                }
            });
        }
    }



}
