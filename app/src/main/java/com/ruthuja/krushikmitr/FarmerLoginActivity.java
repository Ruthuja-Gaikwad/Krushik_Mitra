package com.ruthuja.krushikmitr;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FarmerLoginActivity extends AppCompatActivity {

    private EditText etFarmerUsername, etFarmerPassword;
    private Button btnFarmerSubmit;
    private TextView tvSignUp;
    private SharedPreferences sharedPreferences;

    private FirebaseAuth mAuth;
    private static final String TAG = "FarmerLoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_login);

        mAuth = FirebaseAuth.getInstance();
        sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);

        FirebaseUser currentUser = mAuth.getCurrentUser();
        boolean isLoggedIn = sharedPreferences.getBoolean("isFarmerLoggedIn", false);

        if (currentUser != null && isLoggedIn) {
            navigateToDashboard();
            return;
        }

        // Initialize Views
        etFarmerUsername = findViewById(R.id.et_farmer_username);
        etFarmerPassword = findViewById(R.id.et_farmer_password);
        btnFarmerSubmit = findViewById(R.id.btn_farmer_submit);
        tvSignUp = findViewById(R.id.tv_farmer_signup); // Add this TextView in XML

        // Login Button Click
        btnFarmerSubmit.setOnClickListener(view -> handleLogin());

        // Sign Up Click
        tvSignUp.setOnClickListener(view -> {
            Intent intent = new Intent(FarmerLoginActivity.this, FarmerSignupActivity.class);
            startActivity(intent);
        });
    }

    private void handleLogin() {
        String username = etFarmerUsername.getText().toString().trim();
        String password = etFarmerPassword.getText().toString().trim();

        if (TextUtils.isEmpty(username)) {
            etFarmerUsername.setError("Username is required");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            etFarmerPassword.setError("Password is required");
            return;
        }
        if (password.length() < 6) {
            etFarmerPassword.setError("Password must be at least 6 characters");
            return;
        }

        btnFarmerSubmit.setEnabled(false);

        mAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(this, task -> {
                    btnFarmerSubmit.setEnabled(true);
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();

                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("isFarmerLoggedIn", true);
                        editor.putString("farmerUsername", username);
                        editor.apply();

                        navigateToDashboard();
                    } else {
                        Log.e(TAG, "Login failed: ", task.getException());
                        Toast.makeText(this, "Invalid credentials. Please try again.", Toast.LENGTH_SHORT).show();
                        etFarmerPassword.setText("");
                    }
                });
    }

    private void navigateToDashboard() {
        Intent intent = new Intent(this, FarmerDashboardActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }
}
