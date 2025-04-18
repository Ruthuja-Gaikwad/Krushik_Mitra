package com.ruthuja.krushikmitr;

import android.app.AlertDialog;
import android.content.Context;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.ViewHolder> {

    private final Context context;
    private final ArrayList<InventoryItem> itemList;

    public InventoryAdapter(Context context, ArrayList<InventoryItem> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public InventoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_inventory, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InventoryAdapter.ViewHolder holder, int position) {
        InventoryItem item = itemList.get(position);
        holder.itemName.setText(item.getName());
        holder.itemQuantity.setText(item.getQuantity());

        holder.btnEdit.setOnClickListener(v -> showEditDialog(position));
        holder.btnDelete.setOnClickListener(v -> {
            itemList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, itemList.size());
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemName, itemQuantity;
        Button btnEdit, btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.itemName);
            itemQuantity = itemView.findViewById(R.id.itemQuantity);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }

    private void showEditDialog(int position) {
        InventoryItem currentItem = itemList.get(position);

        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_edit_item, null);
        EditText inputName = dialogView.findViewById(R.id.editName);
        EditText inputQuantity = dialogView.findViewById(R.id.editQuantity);

        inputName.setText(currentItem.getName());
        inputQuantity.setText(currentItem.getQuantity());

        new AlertDialog.Builder(context)
                .setTitle("Edit Item")
                .setView(dialogView)
                .setPositiveButton("Save", (dialog, which) -> {
                    String newName = inputName.getText().toString();
                    String newQty = inputQuantity.getText().toString();

                    itemList.set(position, new InventoryItem(newName, newQty));
                    notifyItemChanged(position);
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}
