package com.ruthuja.krushikmitr;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class LanguageSelectionActivity extends AppCompatActivity {
    private Button btnEnglish, btnHindi, btnMarathi,btnTelugu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_selection);

        btnEnglish = findViewById(R.id.btnEnglish);
        btnHindi = findViewById(R.id.btnHindi);
        btnMarathi = findViewById(R.id.btnMarathi);
        btnTelugu = findViewById(R.id.btnTelugu);

        btnEnglish.setOnClickListener(v -> setLocale("en"));
        btnHindi.setOnClickListener(v -> setLocale("hi"));
        btnMarathi.setOnClickListener(v -> setLocale("mr"));
        btnTelugu.setOnClickListener(v -> setLocale("te"));
    }

    private void setLocale(String languageCode) {
        // Save language selection
        SharedPreferences sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("SelectedLanguage", languageCode);
        editor.apply();

        // Apply language and restart MainActivity
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Resources resources = getResources();
        Configuration config = new Configuration(resources.getConfiguration());
        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());

        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
