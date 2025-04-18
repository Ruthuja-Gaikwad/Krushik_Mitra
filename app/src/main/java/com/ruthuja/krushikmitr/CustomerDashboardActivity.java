package com.ruthuja.krushikmitr;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class CustomerDashboardActivity extends AppCompatActivity {

    private TabLayout categoryTabs;
    private RecyclerView rvProducts;
    private Button btnViewCart, btnCheckout;
    private TextView tvCustomerWelcome, tvCustomerMessage;
    private ProductAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_dashboard);

        // Initialize views
        categoryTabs = findViewById(R.id.category_tabs);
        rvProducts = findViewById(R.id.rvProducts);
        btnViewCart = findViewById(R.id.btnViewCart);
        btnCheckout = findViewById(R.id.btnCheckout);
        tvCustomerWelcome = findViewById(R.id.tvCustomerWelcome);
        tvCustomerMessage = findViewById(R.id.tvCustomerMessage);

        // Set welcome message
        tvCustomerWelcome.setText("Welcome to Krushik Mitr!");
        tvCustomerMessage.setText("Explore fresh products directly from farmers.");

        // Setup RecyclerView with an empty list initially
        rvProducts.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ProductAdapter(this, new ArrayList<>());
        rvProducts.setAdapter(adapter);

        // Load products by default category (Fruits)
        loadProductsByCategory("Fruits");

        // Add tabs for category selection
        categoryTabs.addTab(categoryTabs.newTab().setText("Fruits"));
        categoryTabs.addTab(categoryTabs.newTab().setText("Vegetables"));
        categoryTabs.addTab(categoryTabs.newTab().setText("Dairy"));

        // Handle tab selection and load respective category products
        categoryTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                loadProductsByCategory(tab.getText().toString());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // Optional: Handle tab unselected if needed
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // Optional: Handle tab reselection if needed
            }
        });

        // Cart & Checkout button click listeners
        btnViewCart.setOnClickListener(v -> startActivity(new Intent(this, CartActivity.class)));
        btnCheckout.setOnClickListener(v -> startActivity(new Intent(this, CheckoutActivity.class)));
    }

    /**
     * This method loads products based on the selected category
     * and updates the RecyclerView with the correct products.
     *
     * @param category The selected category (e.g., Fruits, Vegetables, Dairy)
     */
    private void loadProductsByCategory(String category) {
        List<Product> products = new ArrayList<>();

        // Load products based on the selected category
        switch (category) {
            case "Fruits":
                products.add(new Product("Apple", 50.0, "Fresh Red Apples", R.drawable.ic_apple));
                products.add(new Product("Banana", 30.0, "Organic Bananas", R.drawable.ic_banana));
                products.add(new Product("Mango", 60.0, "Fresh Mangoes", R.drawable.mango));  // Fixed duplicate item
                break;

            case "Vegetables":
                products.add(new Product("Tomato", 25.0, "Fresh Tomatoes", R.drawable.tomatoes));
                products.add(new Product("Carrot", 20.0, "Organic Carrots", R.drawable.carrot));
                break;

            case "Dairy":
                products.add(new Product("Milk", 40.0, "Fresh Cow Milk", R.drawable.milk));
                products.add(new Product("Paneer", 200.0, "Homemade Paneer", R.drawable.paneer));
                break;
        }

        // Update the RecyclerView adapter with the new product list
        adapter.setProductList(products);
    }
}
