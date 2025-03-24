package com.ruthuja.krushikmitr;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;

public class SubsidyInfoActivity extends AppCompatActivity {
    private LinearLayout cardLoanSubsidy, cardGovtSchemes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subsidy_info);

        // Initialize the cards
        cardLoanSubsidy = findViewById(R.id.card_loan_subsidy);
        cardGovtSchemes = findViewById(R.id.card_govt_schemes);

        // Set Click Listeners
        cardLoanSubsidy.setOnClickListener(v -> {
            Intent intent = new Intent(SubsidyInfoActivity.this, LoanDetailsActivity.class);
            startActivity(intent);
        });

        cardGovtSchemes.setOnClickListener(v -> {
            Intent intent = new Intent(SubsidyInfoActivity.this, GovtSchemesActivity.class);
            startActivity(intent);
        });
    }
}
