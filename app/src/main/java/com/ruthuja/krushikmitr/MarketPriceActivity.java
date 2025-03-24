package com.ruthuja.krushikmitr;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class MarketPriceActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MarketPriceAdapter adapter;
    private EditText etSearch;
    private List<CropModel> cropList, filteredList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_price);

        // Initialize Views
        recyclerView = findViewById(R.id.rv_market_prices);
        etSearch = findViewById(R.id.et_search_crop);

        // Initialize Data
        cropList = new ArrayList<>();
        populateCropList();

        filteredList = new ArrayList<>(cropList);
        adapter = new MarketPriceAdapter(filteredList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Search Filter
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterCrops(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void populateCropList() {
        cropList.add(new CropModel("Wheat", "₹2200 per quintal", R.drawable.ic_wheat));
        cropList.add(new CropModel("Rice", "₹2500 per quintal", R.drawable.ic_rice));
        cropList.add(new CropModel("Maize", "₹1800 per quintal", R.drawable.ic_maize));
        cropList.add(new CropModel("Sugarcane", "₹300 per quintal", R.drawable.ic_sugarcane));
        cropList.add(new CropModel("Soybean", "₹4000 per quintal", R.drawable.ic_soybean));
        cropList.add(new CropModel("Cotton", "₹6000 per quintal", R.drawable.ic_cotton));
    }

    private void filterCrops(String query) {
        filteredList.clear();
        for (CropModel crop : cropList) {
            if (crop.getName().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(crop);
            }
        }
        adapter.notifyDataSetChanged();
    }
}
