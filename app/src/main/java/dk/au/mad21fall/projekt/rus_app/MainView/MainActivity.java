package dk.au.mad21fall.projekt.rus_app.MainView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import dk.au.mad21fall.projekt.rus_app.BarView.BarActivity;
import dk.au.mad21fall.projekt.rus_app.LeaderBoardView.LeaderBoardActivity;
import dk.au.mad21fall.projekt.rus_app.LeaderBoardView.LeaderBoardActivityViewModel;
import dk.au.mad21fall.projekt.rus_app.Models.Tutor;
import dk.au.mad21fall.projekt.rus_app.NotificationService;
import dk.au.mad21fall.projekt.rus_app.PersonalTabView.PersonalTabActivity;
import dk.au.mad21fall.projekt.rus_app.R;
import dk.au.mad21fall.projekt.rus_app.TutorView.TutorActivity;

public class MainActivity extends AppCompatActivity {
    Button BtnSignIn;
    public static final int REQUEST_LOGIN = 69420;
    FirebaseAuth auth;
    MainActivityViewModel viewmodel;
    String TAG = "MainActivity";
    Tutor currentTutor = new Tutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewmodel = new ViewModelProvider(this).get(MainActivityViewModel.class);

        BtnSignIn = findViewById(R.id.BtnSignInTutor);
        BtnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignIn();
            }
        });

        viewmodel.getCurrentTutor().observe(this, new Observer<Tutor>() {
            @Override
            public void onChanged(Tutor tutor) {
                currentTutor = tutor;
            }
        });
    }

    private void SignIn() {
        if(auth == null) {
            auth = FirebaseAuth.getInstance();
        }
        if(auth.getCurrentUser() != null) {
            FirebaseUser firebaseUser = auth.getCurrentUser();

            if (firebaseUser != null) {
                Log.d(TAG, ""+firebaseUser.getEmail());
            }
            gotoMainApp();
        } else {
            List<AuthUI.IdpConfig> providers = Arrays.asList(
                    new AuthUI.IdpConfig.EmailBuilder().build()
            );

            startActivityForResult(
                    AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(providers).build(), REQUEST_LOGIN);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(REQUEST_LOGIN == requestCode) {
            if (resultCode == RESULT_OK) {
                String dinMor = auth.getCurrentUser().getUid();

                Toast.makeText(this, R.string.login +dinMor, Toast.LENGTH_SHORT).show();
                gotoMainApp();
            }
        }
    }

    private void gotoMainApp() {
        boolean isTutor = currentTutor != null && !currentTutor.isAdmin();
        viewmodel.StartService();
        Log.d(TAG, "is tutor: " + isTutor);
        if(isTutor) {
            Intent i = new Intent(this, PersonalTabActivity.class);
            startActivity(i);
            finish();
        } else {
            Intent i = new Intent(this, BarActivity.class);
            startActivity(i);
            finish();
        }
    }
}