package com.ruthuja.krushikmitr;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class InventoryActivity extends AppCompatActivity {

    private RecyclerView inventoryRecyclerView;
    private InventoryAdapter adapter;
    private ArrayList<InventoryItem> inventoryList;
    private Button btnAddItem;
    private int counter = 1; // for sample item generation

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        inventoryRecyclerView = findViewById(R.id.inventoryRecyclerView);
        btnAddItem = findViewById(R.id.btnAddItem);

        inventoryList = new ArrayList<>();
        adapter = new InventoryAdapter(this, inventoryList);
        inventoryRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        inventoryRecyclerView.setAdapter(adapter);

        // Sample data
        inventoryList.add(new InventoryItem("📦 Urea Fertilizer", "5 bags"));
        inventoryList.add(new InventoryItem("🌱 Tomato Seeds", "2 packets"));
        adapter.notifyDataSetChanged();

        // Add item on button click
        btnAddItem.setOnClickListener(v -> {
            String newName = "🧪 New Item " + counter++;
            String newQty = counter + " units";
            inventoryList.add(new InventoryItem(newName, newQty));
            adapter.notifyItemInserted(inventoryList.size() - 1);
            inventoryRecyclerView.scrollToPosition(inventoryList.size() - 1);
        });
    }
}

