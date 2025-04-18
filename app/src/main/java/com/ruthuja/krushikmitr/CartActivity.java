package com.ruthuja.krushikmitr;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    private RecyclerView rvCartItems;
    private Button btnCheckout;
    private TextView tvTotalAmount;
    private ArrayList<Product> cartItems;
    private CartAdapter cartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_activity);

        // Initialize views
        rvCartItems = findViewById(R.id.rvCartItems);
        btnCheckout = findViewById(R.id.btnCheckout);
        tvTotalAmount = findViewById(R.id.tvTotalAmount);

        // Get actual cart items from the singleton Cart class
        cartItems = new ArrayList<>(Cart.getInstance().getProducts());

        // Setup RecyclerView for cart items
        cartAdapter = new CartAdapter(this, cartItems);
        rvCartItems.setLayoutManager(new LinearLayoutManager(this));
        rvCartItems.setAdapter(cartAdapter);

        // Show total amount
        updateTotalAmount();

        // Handle Checkout Button Click
        btnCheckout.setOnClickListener(view -> {
            // Navigate to checkout process (could be another activity or dialog)
            Toast.makeText(this, "Proceeding to Checkout", Toast.LENGTH_SHORT).show();
        });

        // Update the RecyclerView whenever the cart changes (use listener)
        Cart.getInstance().setCartUpdateListener(() -> {
            // Clear and update the cart items in RecyclerView
            cartItems.clear();
            cartItems.addAll(Cart.getInstance().getProducts());
            cartAdapter.notifyDataSetChanged(); // Notify adapter that data has changed
            updateTotalAmount(); // Update total amount
        });
    }

    // Method to update total amount in the cart
    private void updateTotalAmount() {
        double totalAmount = 0;
        for (Product product : cartItems) {
            totalAmount += product.getPrice();
        }
        tvTotalAmount.setText("Total: ₹" + totalAmount); // Update currency symbol to ₹
    }
}
