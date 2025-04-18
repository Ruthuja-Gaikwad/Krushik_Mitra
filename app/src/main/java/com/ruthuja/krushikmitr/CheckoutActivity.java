package com.ruthuja.krushikmitr;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CheckoutActivity extends AppCompatActivity {

    private RecyclerView rvCheckoutItems;
    private TextView tvCheckoutTotal;
    private RadioGroup rgPaymentMethods;
    private Button btnPayNow;

    private List<Product> checkoutItems;
    private CartAdapter checkoutAdapter;

    private double finalTotal = 0.0;
    private String selectedPaymentMethod = "Unknown";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        // Initialize UI elements
        rvCheckoutItems = findViewById(R.id.rvCheckoutItems);
        tvCheckoutTotal = findViewById(R.id.tvCheckoutTotal);
        rgPaymentMethods = findViewById(R.id.rgPaymentMethods);
        btnPayNow = findViewById(R.id.btnPayNow);

        // Dummy cart items - replace with actual cart data
        checkoutItems = new ArrayList<>();
        checkoutItems.add(new Product("Tomato", 5.0, "Fresh organic tomatoes", R.drawable.tomatoes));

        // Setup RecyclerView
        rvCheckoutItems.setLayoutManager(new LinearLayoutManager(this));
        checkoutAdapter = new CartAdapter(this, checkoutItems);
        rvCheckoutItems.setAdapter(checkoutAdapter);

        // Calculate total
        for (Product product : checkoutItems) {
            finalTotal += product.getPrice();
        }
        tvCheckoutTotal.setText(String.format("Total: ₹%.2f", finalTotal));

        // Handle payment method selection
        rgPaymentMethods.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rbCOD) {
                selectedPaymentMethod = "Cash on Delivery";
                btnPayNow.setText("Place Order");
            } else if (checkedId == R.id.rbCreditCard) {
                selectedPaymentMethod = "Credit Card";
                btnPayNow.setText("Pay Now");
            } else if (checkedId == R.id.rbPayPal) {
                selectedPaymentMethod = "PayPal";
                btnPayNow.setText("Pay Now");
            }
        });

        // Handle Pay Now / Place Order
        btnPayNow.setOnClickListener(v -> {
            if (selectedPaymentMethod.equals("Unknown")) {
                Toast.makeText(this, "Please select a payment method", Toast.LENGTH_SHORT).show();
                return;
            }

            // Simulate order placement and navigate to success screen
            Toast.makeText(this, "Order placed successfully!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(CheckoutActivity.this, OrderSuccessActivity.class);
            intent.putExtra("orderId", "test_order_001"); // Optional dummy order ID
            startActivity(intent);
            finish(); // Close the checkout screen
        });
    }
}
