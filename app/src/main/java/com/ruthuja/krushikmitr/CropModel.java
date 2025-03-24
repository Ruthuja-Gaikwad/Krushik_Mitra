package com.ruthuja.krushikmitr;

public class CropModel {
    private String name;
    private String price;
    private int imageResId;

    public CropModel(String name, String price, int imageResId) {
        this.name = name;
        this.price = price;
        this.imageResId = imageResId;
    }

    public String getName() { return name; }
    public String getPrice() { return price; }
    public int getImageResId() { return imageResId; }
}
