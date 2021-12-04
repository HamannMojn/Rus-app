package dk.au.mad21fall.projekt.rus_app.CreateTeamView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import dk.au.mad21fall.projekt.rus_app.LeaderBoardView.LeaderBoardActivityViewModel;
import dk.au.mad21fall.projekt.rus_app.Models.Team;
import dk.au.mad21fall.projekt.rus_app.R;

public class CreateTeamActivity extends AppCompatActivity {

    private EditText inputName;
    private Button backBtn, addBtn;
    private CreateTeamActivityViewModel vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_team);

        backBtn = findViewById(R.id.createTeamBackBtn);
        addBtn = findViewById(R.id.createTeamAddBtn);
        inputName = findViewById(R.id.createTeamNameInput);

        vm = new ViewModelProvider(this).get(CreateTeamActivityViewModel.class);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Team team = new Team(inputName.getText().toString());
                vm.addTeam(team);
                finish();
            }
        });
    }
}