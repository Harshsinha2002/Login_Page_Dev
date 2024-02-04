package com.example.login_page_dev;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp_Page extends AppCompatActivity
{

    String email, password;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);

        TextInputEditText Email_editText = findViewById(R.id.Email_editText);
        TextInputEditText Password_editText = findViewById(R.id.Password_editText);
        Button Create_Account_btn = findViewById(R.id.Create_Account_btn);

        Create_Account_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                email = String.valueOf(Email_editText.getText());
                password = String.valueOf(Password_editText.getText());

                if(TextUtils.isEmpty(email))
                {
                    Toast.makeText(SignUp_Page.this, "ENTER YOUR EMAIL ADDRESS!!", Toast.LENGTH_SHORT).show();
                    Email_editText.setError("INVALID!!");
                    return;
                }

                else if(TextUtils.isEmpty(password))
                {
                    Toast.makeText(SignUp_Page.this, "ENTER YOUR PASSWORD!!", Toast.LENGTH_SHORT).show();
                    Password_editText.setError("INVALID!!");
                    return;
                }

                else if(password.length() < 6)
                {
                    Toast.makeText(SignUp_Page.this, "YOUR PASSWORD MUST CONTAIN 6 LETTERS!!", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(SignUp_Page.this, "Account Created", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(SignUp_Page.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                                else
                                {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(SignUp_Page.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}