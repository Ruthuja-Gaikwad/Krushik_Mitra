package com.ruthuja.krushikmitr;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private Button btnLoginFarmer, btnLoginCustomer;
    private TextView welcomeText;

    private Animation fadeInAnim, slideUpAnim, bounceAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Apply previously selected language before view inflation
        applySavedLanguage();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        initViews();

        // Load animations
        initAnimations();

        // Set animated transitions
        setAnimatedViews();

        // Set login button click events
        setListeners();
    }

    private void initViews() {
        btnLoginFarmer = findViewById(R.id.btn_login_farmer);
        btnLoginCustomer = findViewById(R.id.btn_consumer_login);
        welcomeText = findViewById(R.id.welcomeText);
    }

    private void initAnimations() {
        fadeInAnim = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        slideUpAnim = AnimationUtils.loadAnimation(this, R.anim.slide_up);
        bounceAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
    }

    private void setAnimatedViews() {
        if (fadeInAnim != null) welcomeText.startAnimation(fadeInAnim);
        if (slideUpAnim != null) btnLoginFarmer.startAnimation(slideUpAnim);
        if (slideUpAnim != null) btnLoginCustomer.startAnimation(slideUpAnim);
    }

    private void setListeners() {
        btnLoginFarmer.setOnClickListener(view -> launchActivityWithBounce(view, FarmerLoginActivity.class));
        btnLoginCustomer.setOnClickListener(view -> launchActivityWithBounce(view, CustomerLoginActivity.class));
    }

    private void launchActivityWithBounce(View view, Class<?> activityClass) {
        if (bounceAnim != null) view.startAnimation(bounceAnim);
        view.setEnabled(false); // Prevent rapid clicks

        view.postDelayed(() -> {
            Intent intent = new Intent(MainActivity.this, activityClass);
            startActivity(intent);
            view.setEnabled(true);
        }, 500); // Delay matches animation time
    }

    private void applySavedLanguage() {
        SharedPreferences prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        String langCode = prefs.getString("SelectedLanguage", "en"); // default English

        Locale locale = new Locale(langCode);
        Locale.setDefault(locale);
        Resources res = getResources();
        Configuration config = new Configuration(res.getConfiguration());
        config.setLocale(locale);
        res.updateConfiguration(config, res.getDisplayMetrics());
    }
}
