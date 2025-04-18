package com.ruthuja.krushikmitr;
public class InventoryItem {
    private String name;
    private String quantity;

    public InventoryItem(String name, String quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public String getQuantity() {
        return quantity;
    }
}


