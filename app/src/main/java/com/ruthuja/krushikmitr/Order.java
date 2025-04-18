package com.ruthuja.krushikmitr;

import java.util.List;

public class Order {
    private String orderId;
    private List<Product> productList;
    private double totalAmount;
    private long timestamp;
    private String customerId;
    private String paymentMethod;  // NEW FIELD

    // Required default constructor for Firebase
    public Order() {}

    public Order(String orderId, List<Product> productList, double totalAmount, long timestamp, String customerId, String paymentMethod) {
        this.orderId = orderId;
        this.productList = productList;
        this.totalAmount = totalAmount;
        this.timestamp = timestamp;
        this.customerId = customerId;
        this.paymentMethod = paymentMethod;
    }

    // Getters
    public String getOrderId() {
        return orderId;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    // Setters
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
