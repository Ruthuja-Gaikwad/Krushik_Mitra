package com.ruthuja.krushikmitr;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView; // ✅ Import CardView

public class FinanceInfoActivity extends AppCompatActivity {
    private CardView cardLoan, cardSubsidy, cardMarketPrice; // ✅ Use CardView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finance_info);

        // Initialize cards correctly
        cardLoan = findViewById(R.id.card_loan);
        cardSubsidy = findViewById(R.id.card_subsidy);
        cardMarketPrice = findViewById(R.id.card_market_price);

        // Set Click Listeners
        cardLoan.setOnClickListener(v -> startActivity(new Intent(FinanceInfoActivity.this, LoanEligibilityActivity.class)));
        cardSubsidy.setOnClickListener(v -> startActivity(new Intent(FinanceInfoActivity.this, SubsidyInfoActivity.class)));
        cardMarketPrice.setOnClickListener(v -> startActivity(new Intent(FinanceInfoActivity.this, MarketPriceActivity.class)));
    }
}
