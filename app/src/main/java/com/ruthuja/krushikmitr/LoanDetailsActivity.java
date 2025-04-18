package com.ruthuja.krushikmitr;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class LoanDetailsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LoanProviderAdapter adapter;
    private List<LoanProvider> loanProviders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_details);

        recyclerView = findViewById(R.id.recycler_loan_providers);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loanProviders = new ArrayList<>();
        loanProviders.add(new LoanProvider("State Bank of India", "7.5%", "₹50,000 - ₹10,00,000", "+91-1234567890"));
        loanProviders.add(new LoanProvider("NABARD", "6.8%", "₹1,00,000 - ₹15,00,000", "+91-9876543210"));

        adapter = new LoanProviderAdapter(loanProviders);
        recyclerView.setAdapter(adapter);
    }
}
