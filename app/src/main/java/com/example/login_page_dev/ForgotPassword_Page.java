package com.example.login_page_dev;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword_Page extends AppCompatActivity
{

    String email;
    FirebaseAuth auth;
    TextInputEditText Email_editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_page);

        auth = FirebaseAuth.getInstance();
        Button Submit_btn = findViewById(R.id.Submit_btn);
        Email_editText = findViewById(R.id.Email_editText);

        Submit_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                validate_password();
            }
        });
    }
    private void validate_password()
    {
        email = Email_editText.getText().toString();
        if(TextUtils.isEmpty(email))
        {
            Email_editText.setError("Required!!");
        }

        else
        {
            forget_password();
        }
    }

    private void forget_password()
    {
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(ForgotPassword_Page.this, "CHECK YOUR EMAIL", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(ForgotPassword_Page.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }

                else
                {
                    Toast.makeText(ForgotPassword_Page.this, "ERROR " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}