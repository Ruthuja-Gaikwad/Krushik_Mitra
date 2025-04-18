package com.ruthuja.krushikmitr;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class OrderSuccessActivity extends AppCompatActivity {

    private TextView tvOrderId, tvTotalAmount;
    private Button btnBackToHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_success_activity);

        // Initialize views
        tvOrderId = findViewById(R.id.tvOrderId);
        tvTotalAmount = findViewById(R.id.tvTotalAmount);
        btnBackToHome = findViewById(R.id.btnBackToHome);

        // Get order ID from the intent
        String orderId = getIntent().getStringExtra("orderId");

        if (orderId != null) {
            // Fetch order details from Firebase
            fetchOrderDetails(orderId);
        } else {
            // Show error if orderId is missing
            Toast.makeText(this, "Order ID is missing", Toast.LENGTH_SHORT).show();
        }

        // Back to Home button click listener
        btnBackToHome.setOnClickListener(view -> {
            // Navigate back to the Home screen
            Intent intent = new Intent(OrderSuccessActivity.this, CustomerDashboardActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish(); // Close the current activity
        });
    }

    private void fetchOrderDetails(String orderId) {
        // Firebase Database reference to the specific order
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("orders").child(orderId);

        // Retrieve order data from Firebase
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Check if the order exists
                Order order = dataSnapshot.getValue(Order.class);
                if (order != null) {
                    // Display order details
                    tvOrderId.setText("Order ID: " + order.getOrderId());
                    tvTotalAmount.setText("Total: ₹" + String.format("%.2f", order.getTotalAmount()));
                } else {
                    // Order not found in the database
                    Toast.makeText(OrderSuccessActivity.this, "Order not found.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle database errors
                Toast.makeText(OrderSuccessActivity.this, "Failed to load order: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
