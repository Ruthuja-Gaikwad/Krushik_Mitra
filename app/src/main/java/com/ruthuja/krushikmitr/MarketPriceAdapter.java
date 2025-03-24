package com.ruthuja.krushikmitr;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MarketPriceAdapter extends RecyclerView.Adapter<MarketPriceAdapter.ViewHolder> {
    private List<CropModel> cropList;

    public MarketPriceAdapter(List<CropModel> cropList) {
        this.cropList = cropList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_crop_price, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CropModel crop = cropList.get(position);
        holder.tvCropName.setText(crop.getName());
        holder.tvCropPrice.setText(crop.getPrice());
        holder.ivCropIcon.setImageResource(crop.getImageResId());
    }

    @Override
    public int getItemCount() {
        return cropList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCropName, tvCropPrice;
        ImageView ivCropIcon;

        public ViewHolder(View itemView) {
            super(itemView);
            tvCropName = itemView.findViewById(R.id.tv_crop_name);
            tvCropPrice = itemView.findViewById(R.id.tv_crop_price);
            ivCropIcon = itemView.findViewById(R.id.iv_crop_icon);
        }
    }
}
