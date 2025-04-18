package com.ruthuja.krushikmitr;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FarmerDashboardActivity extends AppCompatActivity {

    private LinearLayout sectionCrop, sectionWeather, sectionFinance, sectionChatbot;
    private LinearLayout sectionVideos, sectionInventory, sectionCalendar, sectionCommunity;
    private Button btnLogout;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_dashboard);

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);

        // Initialize views
        sectionCrop = findViewById(R.id.section_crop);
        sectionWeather = findViewById(R.id.section_weather);
        sectionFinance = findViewById(R.id.section_finance);
        sectionChatbot = findViewById(R.id.section_chatbot);
        sectionVideos = findViewById(R.id.section_videos);
        sectionInventory = findViewById(R.id.section_inventory);
        sectionCalendar = findViewById(R.id.section_calendar);
        sectionCommunity = findViewById(R.id.section_community);
        btnLogout = findViewById(R.id.btn_logout);

        // Set Click Listeners
        sectionCrop.setOnClickListener(view -> navigateToActivity(CropInfoActivity.class));
        sectionWeather.setOnClickListener(view -> navigateToActivity(WeatherInfoActivity.class));
        sectionFinance.setOnClickListener(view -> navigateToActivity(FinanceInfoActivity.class));
        sectionChatbot.setOnClickListener(view -> navigateToActivity(ChatbotActivity.class));
        sectionVideos.setOnClickListener(view -> navigateToActivity(FarmingVideosActivity.class));
        sectionInventory.setOnClickListener(view -> navigateToActivity(InventoryActivity.class));
        sectionCalendar.setOnClickListener(view -> navigateToActivity(CropCalendarActivity.class));
        sectionCommunity.setOnClickListener(view -> navigateToActivity(CommunityActivity.class));
        btnLogout.setOnClickListener(view -> handleLogout());
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        boolean isLoggedIn = sharedPreferences.getBoolean("isFarmerLoggedIn", false);

        if (currentUser == null || !isLoggedIn) {
            Intent intent = new Intent(this, FarmerLoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
    }

    private void navigateToActivity(Class<?> targetActivity) {
        Intent intent = new Intent(this, targetActivity);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    private void handleLogout() {
        // Sign out from Firebase
        FirebaseAuth.getInstance().signOut();

        // Clear SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        // Go to login screen
        Intent intent = new Intent(this, FarmerLoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
