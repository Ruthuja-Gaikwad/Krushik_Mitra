package com.ruthuja.krushikmitr;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class LoanEligibilityActivity extends AppCompatActivity {

    private EditText etAnnualIncome, etLandSize, etCreditScore;
    private Button btnCheckEligibility;
    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_eligibility);

        // Initialize UI Components
        etAnnualIncome = findViewById(R.id.et_annual_income);
        etLandSize = findViewById(R.id.et_land_size);
        etCreditScore = findViewById(R.id.et_credit_score);
        btnCheckEligibility = findViewById(R.id.btn_check_eligibility);
        tvResult = findViewById(R.id.tv_result);

        // Check Eligibility Button Click Listener
        btnCheckEligibility.setOnClickListener(v -> checkLoanEligibility());
    }

    private void checkLoanEligibility() {
        // Get user input
        String incomeStr = etAnnualIncome.getText().toString().trim();
        String landSizeStr = etLandSize.getText().toString().trim();
        String creditScoreStr = etCreditScore.getText().toString().trim();

        if (incomeStr.isEmpty() || landSizeStr.isEmpty() || creditScoreStr.isEmpty()) {
            tvResult.setText("Please fill all the fields!");
            return;
        }

        double annualIncome = Double.parseDouble(incomeStr);
        double landSize = Double.parseDouble(landSizeStr);
        int creditScore = Integer.parseInt(creditScoreStr);

        // Loan Eligibility Logic
        if (annualIncome > 50000 && landSize > 2 && creditScore > 650) {
            tvResult.setText("✅ Eligible for Loan!\nVisit the nearest bank for application.");
        } else {
            tvResult.setText("❌ Not Eligible.\nTry increasing income or improving credit score.");
        }
    }
}
