package com.ruthuja.krushikmitr;
import android.os.Bundle;
import java.util.List;
import java.util.ArrayList;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
public class CropCalendarActivity extends AppCompatActivity {

    private RecyclerView recyclerCropCalendar;
    private CropCalendarAdapter adapter;
    private List<CropCalendarItem> cropCalendarList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_calendar);

        recyclerCropCalendar = findViewById(R.id.recyclerCropCalendar);
        cropCalendarList = new ArrayList<>();

        // Sample data for prototype
        cropCalendarList.add(new CropCalendarItem("Wheat", "Sowing", "10-Apr-2025"));
        cropCalendarList.add(new CropCalendarItem("Rice", "Watering", "15-Apr-2025"));
        cropCalendarList.add(new CropCalendarItem("Tomato", "Harvest", "20-Apr-2025"));

        adapter = new CropCalendarAdapter(this, cropCalendarList);
        recyclerCropCalendar.setLayoutManager(new LinearLayoutManager(this));
        recyclerCropCalendar.setAdapter(adapter);
    }
}
