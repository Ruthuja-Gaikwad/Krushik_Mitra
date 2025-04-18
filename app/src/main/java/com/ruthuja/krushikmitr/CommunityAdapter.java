package com.ruthuja.krushikmitr;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CommunityAdapter extends RecyclerView.Adapter<CommunityAdapter.CommunityViewHolder> {

    private List<Post> postList;

    // Constructor to initialize the postList
    public CommunityAdapter(List<Post> postList) {
        this.postList = postList;
    }

    @NonNull
    @Override
    public CommunityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for each item in the RecyclerView
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
        return new CommunityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommunityViewHolder holder, int position) {
        // Bind the data from the postList to the views in each item
        Post post = postList.get(position);
        holder.tvTitle.setText(post.getTitle());
        holder.tvDescription.setText(post.getDescription());
        holder.tvDate.setText(post.getDate());
    }

    @Override
    public int getItemCount() {
        // Return the total number of items in the postList
        return postList.size();
    }

    // ViewHolder class to represent each item in the RecyclerView
    public static class CommunityViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle, tvDescription, tvDate;

        public CommunityViewHolder(View itemView) {
            super(itemView);

            // Initialize the TextViews
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvDate = itemView.findViewById(R.id.tvDate);
        }
    }
}
