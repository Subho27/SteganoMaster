package com.steganomaster.steganomaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Not registered -> signup
        TextView signupRedirectingButton = (TextView) findViewById(R.id.login_signup);
        signupRedirectingButton.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, SignupActivity.class)));

        // after login button clicked
        MaterialButton loginButton = (MaterialButton) findViewById(R.id.login_button);
        loginButton.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, HomeActivity.class)));
    }
}