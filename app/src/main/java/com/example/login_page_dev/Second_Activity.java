package com.example.login_page_dev;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Second_Activity extends AppCompatActivity {

    Button Logout_btn;
    private FirebaseAuth mAuth;

    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        mAuth = FirebaseAuth.getInstance();
        Logout_btn = findViewById(R.id.Logout_btn);
        Logout_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                AccessToken accessToken = AccessToken.getCurrentAccessToken();
                if(accessToken != null && !accessToken.isExpired())
                {
                    LoginManager.getInstance().logOut();
                    Intent intent = new Intent(Second_Activity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }

                FirebaseUser currentUser = mAuth.getCurrentUser();
                if(currentUser != null)
                {
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(Second_Activity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }

                    //signOut();
                Intent intent = new Intent(Second_Activity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void signOut()
    {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>()
                {
                    @Override
                    public void onComplete(Task<Void> task)
                    {
                        Intent intent =  new Intent(Second_Activity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
    }
}