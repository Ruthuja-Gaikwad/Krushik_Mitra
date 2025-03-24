package com.ruthuja.krushikmitr;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class CustomerDashboardActivity extends AppCompatActivity {
    private TextView tvWelcome;
    private Button btnViewProducts, btnOrders, btnProfile, btnLogout;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_dashboard);

        // Initialize UI elements
        tvWelcome = findViewById(R.id.tvWelcomeMessage);
        btnViewProducts = findViewById(R.id.btnViewProducts);
        btnOrders = findViewById(R.id.btnOrders);
        btnProfile = findViewById(R.id.btnProfile);
        btnLogout = findViewById(R.id.btnLogout);

        // Get SharedPreferences
        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);

        // Retrieve username from Intent or SharedPreferences
        String customerName = getIntent().getStringExtra("USERNAME");
        if (customerName == null || customerName.isEmpty()) {
            customerName = sharedPreferences.getString("CustomerName", "Customer");
        } else {
            sharedPreferences.edit().putString("CustomerName", customerName).apply();
        }

        // Set welcome message using string resources
        tvWelcome.setText(getString(R.string.welcome_message, customerName));

        // Handle button clicks
        btnViewProducts.setOnClickListener(v -> startActivity(new Intent(this, ViewProductsActivity.class)));
        btnOrders.setOnClickListener(v -> startActivity(new Intent(this, OrdersActivity.class)));
        btnProfile.setOnClickListener(v -> startActivity(new Intent(this, ProfileActivity.class)));

        btnLogout.setOnClickListener(v -> {
            sharedPreferences.edit().remove("CustomerName").apply(); // Remove only login-related data
            startActivity(new Intent(this, CustomerLoginActivity.class));
            finish(); // Close Dashboard
        });
    }
}
