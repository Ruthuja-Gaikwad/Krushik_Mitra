package com.ruthuja.krushikmitr;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FarmingVideosActivity extends AppCompatActivity {

    private static final String API_KEY = "AIzaSyBvYnmp2yXxhuVBXkimoIZZI8aIlrHg-yE";
    private static final String BASE_URL = "https://www.googleapis.com/youtube/v3/";

    private RecyclerView recyclerView;
    private VideoAdapter adapter;
    private EditText searchBox;

    private YouTubeAPIService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farming_videos);

        recyclerView = findViewById(R.id.recyclerView);
        searchBox = findViewById(R.id.searchBox);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(YouTubeAPIService.class);

        searchBox.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override public void afterTextChanged(Editable s) {
                searchYouTube(s.toString());
            }
        });

        searchYouTube("organic farming"); // default search
    }

    private void searchYouTube(String query) {
        Call<YouTubeResponse> call = apiService.searchVideos("snippet", query, API_KEY, "video", 10);
        call.enqueue(new Callback<YouTubeResponse>() {
            @Override
            public void onResponse(Call<YouTubeResponse> call, Response<YouTubeResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<VideoItem> videos = response.body().items;
                    adapter = new VideoAdapter(FarmingVideosActivity.this, videos);
                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(FarmingVideosActivity.this, "No videos found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<YouTubeResponse> call, Throwable t) {
                Toast.makeText(FarmingVideosActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
