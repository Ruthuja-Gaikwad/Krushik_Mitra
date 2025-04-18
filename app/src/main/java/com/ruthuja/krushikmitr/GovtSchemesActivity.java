package com.ruthuja.krushikmitr;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class GovtSchemesActivity extends AppCompatActivity {
    private CardView cardPMKisan, cardPMFBY, cardKCC, cardENAM, cardRKVY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_govt_schemes);

        // Initialize Cards
        cardPMKisan = findViewById(R.id.card_pm_kisan);
        cardPMFBY = findViewById(R.id.card_pm_fby);
        cardKCC = findViewById(R.id.card_kcc);
        cardENAM = findViewById(R.id.card_enam);
        cardRKVY = findViewById(R.id.card_rkvy);

        // Set Click Listeners to Open Web Pages
        cardPMKisan.setOnClickListener(v -> openWebPage("https://pmkisan.gov.in/"));
        cardPMFBY.setOnClickListener(v -> openWebPage("https://pmfby.gov.in/"));
        cardKCC.setOnClickListener(v -> openWebPage("https://www.nabard.org/english/kcc.aspx"));
        cardENAM.setOnClickListener(v -> openWebPage("https://enam.gov.in/"));
        cardRKVY.setOnClickListener(v -> openWebPage("https://rkvy.nic.in/"));
    }

    // Method to Open Web Page in Browser
    private void openWebPage(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }
}
