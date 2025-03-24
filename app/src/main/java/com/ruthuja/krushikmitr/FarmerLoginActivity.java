package com.ruthuja.krushikmitr;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class FarmerLoginActivity extends AppCompatActivity {
    private EditText etFarmerUsername, etFarmerPassword;
    private Button btnFarmerSubmit;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);

        // Check if already logged in
        if (sharedPreferences.getBoolean("isFarmerLoggedIn", false)) {
            startActivity(new Intent(this, FarmerDashboardActivity.class));
            finish(); // Close Login Activity
        }

        setContentView(R.layout.activity_farmer_login);

        // Initialize views
        etFarmerUsername = findViewById(R.id.et_farmer_username);
        etFarmerPassword = findViewById(R.id.et_farmer_password);
        btnFarmerSubmit = findViewById(R.id.btn_farmer_submit);

        btnFarmerSubmit.setOnClickListener(v -> handleLogin());
    }

    private void handleLogin() {
        String username = etFarmerUsername.getText().toString().trim();
        String password = etFarmerPassword.getText().toString().trim();

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter username and password", Toast.LENGTH_SHORT).show();
            return;
        }

        // Assume login is successful
        Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();

        // Store login session
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isFarmerLoggedIn", true);
        editor.putString("farmerUsername", username);
        editor.apply();

        // Navigate to Farmer Dashboard
        startActivity(new Intent(FarmerLoginActivity.this, FarmerDashboardActivity.class));
        finish();
    }
}
