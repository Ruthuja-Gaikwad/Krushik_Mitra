package com.ruthuja.krushikmitr;

public class CropCalendarItem {
    private String cropName;
    private String task;
    private String date;

    public CropCalendarItem(String cropName, String task, String date) {
        this.cropName = cropName;
        this.task = task;
        this.date = date;
    }

    public String getCropName() { return cropName; }
    public String getTask() { return task; }
    public String getDate() { return date; }
}
