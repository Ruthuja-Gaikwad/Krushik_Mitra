package com.ruthuja.krushikmitr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends Activity {

    private static final int SPLASH_DURATION = 2500; // 2.5 seconds
    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Runnable splashRunnable = () -> {
        Intent intent = new Intent(SplashActivity.this, LanguageSelectionActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Find views
        ImageView logo = findViewById(R.id.logo);
        TextView appName = findViewById(R.id.appName);
        TextView tagline = findViewById(R.id.tagline);

        // Load animations
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        Animation zoomIn = AnimationUtils.loadAnimation(this, R.anim.zoom_in);
        Animation slideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up);

        // Apply animations
        logo.startAnimation(zoomIn);
        appName.startAnimation(fadeIn);
        tagline.startAnimation(slideUp);

        // Move to Language Selection after animation
        handler.postDelayed(splashRunnable, SPLASH_DURATION);
    }

    @Override
    public void onBackPressed() {
        // Prevent back press during splash
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(splashRunnable); // Prevent memory leaks
    }
}
