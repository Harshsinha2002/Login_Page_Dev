package com.example.login_page_dev;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    CallbackManager callbackManager; //FACEBOOK LOGIN OBJECT
    ImageView Facebook_btn; //FACEBOOK LOGIN OBJECT
    private FirebaseAuth mAuth; //FIREBASE LOGIN OBJECT
    TextInputEditText Email_editText; //FIREBASE LOGIN OBJECT
    TextInputEditText Password_editText; //FIREBASE LOGIN OBJECT
    String email,password; //FIREBASE LOGIN OBJECT


    GoogleSignInOptions gso;
    GoogleSignInClient mGoogleSignInClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //CODE FOR FACEBOOK LOGIN STARTS FROM HERE
        //*************************************************************************************************************************
        callbackManager = CallbackManager.Factory.create();
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
         if(accessToken != null && !accessToken.isExpired())
         {
             Intent intent = new Intent(MainActivity.this, Second_Activity.class);
             startActivity(intent);
             finish();
         }

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Toast.makeText(MainActivity.this, "Authentication Sucess!!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, Second_Activity.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(MainActivity.this, "Authentication Cancelled!!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Toast.makeText(MainActivity.this, "Authentication Failed!!", Toast.LENGTH_SHORT).show();
                    }
                });


        Facebook_btn = findViewById(R.id.Facebook_btn);
        Facebook_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                LoginManager.getInstance().logInWithReadPermissions(MainActivity.this, Arrays.asList("public_profile"));
            }
        });
        //CODE FOR FACEBOOK LOGIN ENDS FROM HERE
        //*************************************************************************************************************************

        //CODE FOR FIREBASE LOGIN STARTS FROM HERE
        //*************************************************************************************************************************
        mAuth = FirebaseAuth.getInstance();
        Email_editText = findViewById(R.id.Email_editText);
        Password_editText = findViewById(R.id.Password_editText);
        Button Login_btn = findViewById(R.id.Login_btn);


        Login_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                email = String.valueOf(Email_editText.getText());
                password = String.valueOf(Password_editText.getText());

                if(TextUtils.isEmpty(email))
                {
                    Email_editText.setError("INVALID!!");
                }

                else if(TextUtils.isEmpty(password))
                {
                    Password_editText.setError("INVALID!!");
                }

                else {
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(MainActivity.this, "Authentication Sucess!!", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(MainActivity.this, Second_Activity.class);
                                        startActivity(intent);
                                        finish();

                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                                        Toast.makeText(MainActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
        //CODE FOR FIREBASE LOGIN ENDS FROM HERE
        //******************************************************************************************************************************

        //CODE FOR GOOGLE LOGIN STARTS FROM HERE
        //****************************************************************************************************************************

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        ImageView Google_btn = findViewById(R.id.Google_btn);
        Google_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                signIn();
            }
        });




        //CODE FOR SIGN UP PAGE INTENT STARTS FROM HERE
        //*********************************************************************************************************************************

        TextView SignUp_txt = findViewById(R.id.SignUp_txt);
        SignUp_txt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this, SignUp_Page.class);
                startActivity(intent);
            }
        });

        //CODE FOR SIGN UP PAGE INTENT ENDS FROM HERE
        //***********************************************************************************************************************

        //CODE FOR FORGOT PASSWORD PAGE INTENT STARTS FROM HERE
        //*********************************************************************************************************************

        TextView ForgetPassword_txt = findViewById(R.id.ForgetPassword_txt);
        ForgetPassword_txt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this, ForgotPassword_Page.class);
                startActivity(intent);
            }
        });

        //CODE FOR FORGOT PASSWORD PAGE INTENT ENDS FROM HERE
        //*********************************************************************************************************************

    }


    //CODE FOR GOOGLE LOGIN STARTS FROM HERE
    //*********************************************************************************************************************

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1000)
        {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try
            {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Toast.makeText(MainActivity.this, "Authentication Sucess!!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, Second_Activity.class);
                startActivity(intent);
                finish();
            }
            catch (ApiException e)
            {
                Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            }
        }
    }
    //CODE FOR GOOGLE LOGIN ENDS FROM HERE
    //*********************************************************************************************************************



    //CODE FOR FIREBASE LOGIN ON START METHOD FROM HERE and CODE FOR GOOGLE LOGIN ON START METHOD FROM HERE
    //*********************************************************************************************************************

    public void onStart()
    {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null)
        {
            Intent intent = new Intent(MainActivity.this, Second_Activity.class);
            startActivity(intent);
            finish();
            currentUser.reload();
        }
        //*********************************************************************************************************************
        //*********************************************************************************************************************


        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account != null)
        {
           /* Intent intent = new Intent(MainActivity.this, Second_Activity.class);
            startActivity(intent);
            finish();*/
        }

    }
    //CODE FOR FIREBASE LOGIN ENDS FROM HERE and CODE FOR GOOGLE LOGIN ON START METHOD ENDS FROM HERE
    //*********************************************************************************************************************


    //CODE FOR GOOGLE LOGIN START FROM HERE
    //*********************************************************************************************************************
    private void signIn()
    {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 1000);
    }

    //CODE FOR GOOGLE LOGIN ENDS FROM HERE
    //*********************************************************************************************************************

}