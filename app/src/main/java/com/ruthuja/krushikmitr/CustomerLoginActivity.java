package com.ruthuja.krushikmitr;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class CustomerLoginActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private Button btnLogin;
    private TextView tvForgotPassword, tvSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_login);

        // Initialize UI components
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);
        tvSignUp = findViewById(R.id.tvSignUp);

        // Login Button Click Event
        btnLogin.setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (validateInputs(username, password)) {
                authenticateUser(username, password);
            }
        });

        // Forgot Password Click Event
        tvForgotPassword.setOnClickListener(v -> {
            Intent intent = new Intent(CustomerLoginActivity.this, ForgotPasswordActivity.class);
            startActivity(intent);
        });

        // Sign-Up Click Event
        tvSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(CustomerLoginActivity.this, CustomerSignupActivity.class);
            startActivity(intent);
        });
    }

    // Function to validate user inputs
    private boolean validateInputs(String username, String password) {
        if (username.isEmpty()) {
            etUsername.setError("Username required!");
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

    // Function to authenticate user and navigate to the dashboard
    private void authenticateUser(String username, String password) {
        // Replace this with actual authentication logic
        if (username.equals("customer") && password.equals("1234")) {
            // Pass the username to the Customer Dashboard
            Intent intent = new Intent(CustomerLoginActivity.this, CustomerDashboardActivity.class);
            intent.putExtra("USERNAME", username);
            startActivity(intent);
            finish(); // Close Login Activity
        } else {
            Toast.makeText(CustomerLoginActivity.this, "Invalid credentials! Try again.", Toast.LENGTH_SHORT).show();
        }
    }
}
