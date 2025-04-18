package com.ruthuja.krushikmitr;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private List<Product> productList;
    private Context context;

    // Constructor
    public ProductAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the view for each product item
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        // Get the current product at the position
        final Product product = productList.get(position);

        // Bind the product data to the views
        holder.productName.setText(product.getName());
        holder.productPrice.setText("$" + product.getPrice());
        holder.productDescription.setText(product.getDescription());
        holder.productImage.setImageResource(product.getImageResource());

        // Handle the "Negotiate Price" button click
        holder.btnNegotiatePrice.setOnClickListener(v -> showNegotiationDialog(product, holder.getAdapterPosition()));

        // Handle the "Add to Cart" button click
        holder.btnAddToCart.setOnClickListener(v -> addToCart(product));

        // Accessibility content descriptions
        holder.productImage.setContentDescription("Image of " + product.getName());
        holder.productName.setContentDescription("Product name: " + product.getName());
        holder.productPrice.setContentDescription("Price: " + product.getPrice());
        holder.productDescription.setContentDescription("Description: " + product.getDescription());
        holder.btnNegotiatePrice.setContentDescription("Negotiate price for " + product.getName());
        holder.btnAddToCart.setContentDescription("Add " + product.getName() + " to cart");
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    // Method to update the product list dynamically (e.g., when category changes)
    public void setProductList(List<Product> newProductList) {
        this.productList = newProductList;
        notifyDataSetChanged(); // Refresh RecyclerView
    }

    // ViewHolder to hold product views
    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productPrice, productDescription;
        ImageView productImage;
        Button btnNegotiatePrice, btnAddToCart;

        public ProductViewHolder(View view) {
            super(view);
            // Find views by ID
            productName = view.findViewById(R.id.product_name);
            productPrice = view.findViewById(R.id.product_price);
            productDescription = view.findViewById(R.id.product_description);
            productImage = view.findViewById(R.id.product_image);
            btnNegotiatePrice = view.findViewById(R.id.btnNegotiatePrice);
            btnAddToCart = view.findViewById(R.id.btnAddToCart);
        }
    }

    // Method to show the negotiation dialog for price updates
    private void showNegotiationDialog(final Product product, final int position) {
        // Create an AlertDialog for price negotiation
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Negotiate Price");

        // Create an input field to enter the new price
        final EditText input = new EditText(context);
        input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        input.setHint("Enter new price");
        builder.setView(input);

        // Positive button (OK) to accept the new price
        builder.setPositiveButton("OK", (dialog, which) -> {
            String newPrice = input.getText().toString();
            if (!newPrice.isEmpty()) {
                try {
                    // Parse and validate the entered price
                    double parsedPrice = Double.parseDouble(newPrice);
                    if (parsedPrice > 0) {
                        product.setPrice(parsedPrice);
                        // Notify the adapter that the item has changed
                        notifyItemChanged(position);
                    } else {
                        Toast.makeText(context, "Price must be greater than 0.", Toast.LENGTH_SHORT).show();
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(context, "Please enter a valid price.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(context, "Please enter a valid price.", Toast.LENGTH_SHORT).show();
            }
        });

        // Negative button (Cancel) to dismiss the dialog
        builder.setNegativeButton("Cancel", (dialog, which) -> {
            // Do nothing when Cancel is pressed
        });

        // Show the dialog
        builder.show();
    }

    // Method to add the product to the cart
    private void addToCart(Product product) {
        // You can store the products in a cart (e.g., using SharedPreferences or a Cart singleton)
        // For simplicity, we will show a Toast for now.
        // This can be enhanced by using a global Cart class.

        // Example: Add the product to a cart (you can replace it with a real cart implementation)
        Cart.getInstance().addProduct(product);

        // Show a confirmation message
        Toast.makeText(context, product.getName() + " has been added to your cart", Toast.LENGTH_SHORT).show();
    }
}
