package com.ruthuja.krushikmitr;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CropCalendarAdapter extends RecyclerView.Adapter<CropCalendarAdapter.ViewHolder> {

    private final List<CropCalendarItem> cropList;
    private final Context context;

    public CropCalendarAdapter(Context context, List<CropCalendarItem> cropList) {
        this.context = context;
        this.cropList = cropList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCropName, tvTask, tvDate;

        public ViewHolder(View itemView) {
            super(itemView);
            tvCropName = itemView.findViewById(R.id.tvCropName);
            tvTask = itemView.findViewById(R.id.tvTask);
            tvDate = itemView.findViewById(R.id.tvDate);
        }

        public void bind(CropCalendarItem item) {
            tvCropName.setText("🌾 Crop: " + item.getCropName());
            tvTask.setText("🛠️ Task: " + item.getTask());
            tvDate.setText("📅 Date: " + item.getDate());

            itemView.setContentDescription("Crop: " + item.getCropName() +
                    ", Task: " + item.getTask() +
                    ", Date: " + item.getDate());
        }
    }

    @Override
    public CropCalendarAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.crop_calendar_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CropCalendarAdapter.ViewHolder holder, int position) {
        holder.bind(cropList.get(position));
    }

    @Override
    public int getItemCount() {
        return cropList.size();
    }
}
