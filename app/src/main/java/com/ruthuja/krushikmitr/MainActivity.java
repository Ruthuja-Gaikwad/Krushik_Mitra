package com.ruthuja.krushikmitr;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private Button btnLoginFarmer, btnLoginCustomer;
    private TextView welcomeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        applySavedLanguage(); // Apply saved language setting
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI elements
        btnLoginFarmer = findViewById(R.id.btn_login_farmer);
        btnLoginCustomer = findViewById(R.id.btn_consumer_login);
        welcomeText = findViewById(R.id.welcomeText);

        // Load Animations
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        Animation slideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up);
        Animation bounce = AnimationUtils.loadAnimation(this, R.anim.bounce);

        // Apply Animations
        welcomeText.startAnimation(fadeIn);
        btnLoginFarmer.startAnimation(slideUp);
        btnLoginCustomer.startAnimation(slideUp);

        // Button Click Animations
        btnLoginFarmer.setOnClickListener(v -> {
            v.startAnimation(bounce);
            Intent intent = new Intent(MainActivity.this, FarmerLoginActivity.class);
            startActivity(intent);
        });

        btnLoginCustomer.setOnClickListener(v -> {
            v.startAnimation(bounce);
            Intent intent = new Intent(MainActivity.this, CustomerLoginActivity.class);
            startActivity(intent);
        });
    }

    private void applySavedLanguage() {
        SharedPreferences sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        String languageCode = sharedPreferences.getString("SelectedLanguage", "en");

        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Resources resources = getResources();
        Configuration config = new Configuration(resources.getConfiguration());
        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());
    }
}
