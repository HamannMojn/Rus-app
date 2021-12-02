package dk.au.mad21fall.projekt.rus_app.LeaderBoardView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import dk.au.mad21fall.projekt.rus_app.Models.Team;
import dk.au.mad21fall.projekt.rus_app.R;

public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.LeaderboardViewHolder> {

    private List<Team> teams = new ArrayList<>();

    public LeaderboardAdapter(ArrayList<Team> _teams) {teams = _teams; }

    @NonNull
    @Override
    public LeaderboardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.team_list_item, parent, false);
        return new LeaderboardViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull LeaderboardViewHolder holder, int position) {
        Team team = teams.get(position);
        Log.d(team.getName(), "FUCK DIS BITCH");

        holder.title.setText(team.getName());
        holder.cases.setText(Integer.toString(team.getAmount()));
        holder.placement.setText(""+position+1);
    }

    @Override
    public int getItemCount() {
        return teams.size();
    }

    public void updateList(List<Team> list){
        this.teams = list;
        notifyDataSetChanged();
    }

    public class LeaderboardViewHolder extends RecyclerView.ViewHolder {
        TextView title, placement, cases;

        public LeaderboardViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.leaderboardTeamTitle);
            placement = itemView.findViewById(R.id.leaderboardTeamPlacement);
            cases = itemView.findViewById(R.id.leaderboardTeamCases);
        }
    }



}
