package com.ruthuja.krushikmitr;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class FarmerDashboardActivity extends AppCompatActivity {
    private LinearLayout sectionCrop, sectionWeather, sectionFinance, sectionChatbot;
    private Button btnLogout;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_dashboard);

        // Initialize sections
        sectionCrop = findViewById(R.id.section_crop);
        sectionWeather = findViewById(R.id.section_weather);
        sectionFinance = findViewById(R.id.section_finance);
        sectionChatbot = findViewById(R.id.section_chatbot);
        btnLogout = findViewById(R.id.btn_logout);

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);

        // Set Click Listeners
        sectionCrop.setOnClickListener(view -> navigateToActivity(CropInfoActivity.class));
        sectionWeather.setOnClickListener(view -> navigateToActivity(WeatherInfoActivity.class));
        sectionFinance.setOnClickListener(view -> navigateToActivity(FinanceInfoActivity.class));
        sectionChatbot.setOnClickListener(view -> navigateToActivity(ChatbotActivity.class)); // Chatbot Feature

        btnLogout.setOnClickListener(v -> handleLogout());
    }

    private void navigateToActivity(Class<?> targetActivity) {
        Intent intent = new Intent(FarmerDashboardActivity.this, targetActivity);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    private void handleLogout() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        // Navigate back to Login
        Intent intent = new Intent(FarmerDashboardActivity.this, FarmerLoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
