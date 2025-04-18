package com.ruthuja.krushikmitr;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class CustomerLoginActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private Button btnLogin;
    private TextView tvForgotPassword, tvSignUp;
    private FirebaseAuth mAuth;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private static final String PREF_NAME = "KrushikMitrPrefs";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();

        // Check if user is already logged in
        if (sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)) {
            startActivity(new Intent(CustomerLoginActivity.this, CustomerDashboardActivity.class));
            finish(); // Close login activity
            return;
        }

        setContentView(R.layout.activity_customer_login);

        mAuth = FirebaseAuth.getInstance();

        // Initialize UI components
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);
        tvSignUp = findViewById(R.id.tvSignUp);

        // Login button click
        btnLogin.setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (validateInputs(username, password)) {
                authenticateUser(username, password);
            }
        });

        // Forgot password click
        tvForgotPassword.setOnClickListener(v -> {
            Intent intent = new Intent(CustomerLoginActivity.this, ForgotPasswordActivity.class);
            startActivity(intent);
        });

        // Sign up click
        tvSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(CustomerLoginActivity.this, CustomerSignupActivity.class);
            startActivity(intent);
        });
    }

    // Input validation
    private boolean validateInputs(String username, String password) {
        if (username.isEmpty()) {
            etUsername.setError("Email required!");
            etUsername.requestFocus();
            return false;
        }
        if (password.isEmpty()) {
            etPassword.setError("Password required!");
            etPassword.requestFocus();
            return false;
        }
        return true;
    }

    // Firebase Authentication + Save Login State
    private void authenticateUser(String username, String password) {
        mAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Save login state
                        editor.putBoolean(KEY_IS_LOGGED_IN, true);
                        editor.apply();

                        Intent intent = new Intent(CustomerLoginActivity.this, CustomerDashboardActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(CustomerLoginActivity.this,
                                "Authentication failed. " + task.getException().getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
