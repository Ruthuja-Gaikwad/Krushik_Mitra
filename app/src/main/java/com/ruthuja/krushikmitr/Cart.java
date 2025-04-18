package com.ruthuja.krushikmitr;

import android.util.Log;
import java.util.ArrayList;
import java.util.List;

// Singleton class for managing the cart
public class Cart {

    private static Cart instance;
    private final List<Product> productList;
    private CartUpdateListener cartUpdateListener;

    private Cart() {
        productList = new ArrayList<>();
    }

    // Thread-safe singleton pattern
    public static synchronized Cart getInstance() {
        if (instance == null) {
            instance = new Cart();
        }
        return instance;
    }

    // Add product to the cart (avoid duplicates)
    public void addProduct(Product product) {
        if (!productList.contains(product)) {
            productList.add(product);
            Log.d("Cart", "Product added: " + product.getName());
            notifyCartUpdated(); // Notify listeners when the cart is updated
        } else {
            Log.d("Cart", "Product already in cart: " + product.getName());
        }
    }

    // Remove product from the cart
    public void removeProduct(Product product) {
        if (productList.contains(product)) {
            productList.remove(product);
            Log.d("Cart", "Product removed: " + product.getName());
            notifyCartUpdated(); // Notify listeners when the cart is updated
        }
    }

    // Get the list of products in the cart
    public List<Product> getProducts() {
        return new ArrayList<>(productList); // Return a copy to prevent outside modification
    }

    // Clear the cart
    public void clearCart() {
        productList.clear();
        Log.d("Cart", "Cart cleared.");
        notifyCartUpdated(); // Notify listeners when the cart is updated
    }

    // Optional: Get cart size
    public int getCartSize() {
        return productList.size();
    }

    // Set a listener to notify when the cart is updated
    public void setCartUpdateListener(CartUpdateListener listener) {
        this.cartUpdateListener = listener;
    }

    // Notify listeners that the cart has been updated
    private void notifyCartUpdated() {
        if (cartUpdateListener != null) {
            cartUpdateListener.onCartUpdated();
        }
    }

    // CartUpdateListener interface
    public interface CartUpdateListener {
        void onCartUpdated();
    }
}
