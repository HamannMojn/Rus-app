package dk.au.mad21fall.projekt.rus_app.MainView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

import dk.au.mad21fall.projekt.rus_app.LeaderBoardView.LeaderBoardActivity;
import dk.au.mad21fall.projekt.rus_app.R;
import dk.au.mad21fall.projekt.rus_app.TutorView.TutorActivity;

public class MainActivity extends AppCompatActivity {
    Button BtnSignIn;
    public static final int REQUEST_LOGIN = 69420;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BtnSignIn = findViewById(R.id.BtnSignInTutor);
        BtnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignIn();
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
                Log.d("JaGoP nYmAnN", ""+firebaseUser.getEmail());
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

                Toast.makeText(this, "DinMor logged in\n"+dinMor, Toast.LENGTH_SHORT).show();
                gotoMainApp();
            }
        }
    }

    private void gotoMainApp() {
        Intent i = new Intent(this, TutorActivity.class);
        startActivity(i);
        finish();
    }
}