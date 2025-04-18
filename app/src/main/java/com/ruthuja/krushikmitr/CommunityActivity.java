package com.ruthuja.krushikmitr;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.annotation.NonNull;
import java.util.ArrayList;
import java.util.List;

public class CommunityActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CommunityAdapter adapter;
    private List<Post> postList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);

        recyclerView = findViewById(R.id.rvCommunityPosts);

        // Initialize the post list
        postList = new ArrayList<>();
        postList.add(new Post("Post 1", "This is the description of post 1", "12-Apr-2025"));
        postList.add(new Post("Post 2", "This is the description of post 2", "13-Apr-2025"));
        postList.add(new Post("Post 3", "This is the description of post 3", "14-Apr-2025"));

        // Set up the RecyclerView with the adapter
        adapter = new CommunityAdapter(postList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
