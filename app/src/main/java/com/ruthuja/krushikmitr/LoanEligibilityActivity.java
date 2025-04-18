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

        // Determine eligible banks based on credit score
        StringBuilder eligibleBanks = new StringBuilder();

        // Apply logic for credit score
        if (creditScore >= 750) {
            eligibleBanks.append("HDFC, Axis Bank, SBI, ICICI, PNB, Kotak Mahindra, Bank of Baroda, IDFC First, YES Bank, Union Bank");
        } else if (creditScore >= 700) {
            eligibleBanks.append("HDFC, Axis Bank, SBI, ICICI, PNB, Kotak Mahindra");
        } else if (creditScore >= 650) {
            eligibleBanks.append("Axis Bank, SBI, ICICI, PNB");
        } else if (creditScore >= 600) {
            eligibleBanks.append("SBI, ICICI, PNB");
        } else if (creditScore >= 500) {
            eligibleBanks.append("SBI, PNB");
        }

        // Apply logic for annual income
        StringBuilder incomeBanks = new StringBuilder();

        if (annualIncome >= 80000) {
            incomeBanks.append("HDFC, Axis Bank, SBI, ICICI, PNB, Kotak Mahindra, Bank of Baroda, IDFC First, YES Bank, Union Bank");
        } else if (annualIncome >= 70000) {
            incomeBanks.append("HDFC, Axis Bank, SBI, ICICI, PNB, Kotak Mahindra");
        } else if (annualIncome >= 60000) {
            incomeBanks.append("Axis Bank, SBI, ICICI, PNB");
        } else if (annualIncome >= 50000) {
            incomeBanks.append("SBI, ICICI, PNB");
        } else if (annualIncome >= 40000) {
            incomeBanks.append("SBI, PNB");
        }

        // Combine both eligibility checks
        if (annualIncome > 50000 && landSize > 2 && eligibleBanks.length() > 0 && incomeBanks.length() > 0) {
            String combinedEligibleBanks = eligibleBanks.toString() + " | " + incomeBanks.toString();
            tvResult.setText("✅ Eligible for Loan at " + combinedEligibleBanks + "!\nVisit the nearest branch to apply.");
        } else {
            tvResult.setText("❌ Not Eligible for Loan.\nTry increasing income or improving credit score.");
        }
    }
}
