package com.ruthuja.krushikmitr;

import java.util.Objects;

public class Product {
    private String name;
    private double price;
    private String description;
    private int imageResource; // Image resource ID (optional)

    // Constructor with imageResource as optional
    public Product(String name, double price, String description, int imageResource) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.imageResource = imageResource; // Assuming image resource is optional
    }

    // Constructor without imageResource (for cases where image is not needed)
    public Product(String name, double price, String description) {
        this(name, price, description, 0); // Default value for imageResource
    }

    // Getters
    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public int getImageResource() {
        return imageResource;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    // equals() and hashCode() to avoid duplicate products in the cart
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return Double.compare(product.price, price) == 0 &&
                Objects.equals(name, product.name) &&
                Objects.equals(description, product.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, description);
    }

    // Optional: toString() for easy logging
    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", imageResource=" + (imageResource != 0 ? imageResource : "No image") + // Handling optional imageResource
                '}';
    }
}
