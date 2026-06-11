package com.example.generalstoreapp.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.generalstoreapp.R;
import com.example.generalstoreapp.models.GetProductDataModel;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    public interface OnProductActionListener {
        void onEdit(GetProductDataModel product);
        void onDelete(GetProductDataModel product);
    }

    private List<GetProductDataModel> products = new ArrayList<>();
    private List<GetProductDataModel> productsFull = new ArrayList<>();
    private final OnProductActionListener listener;

    public ProductAdapter(OnProductActionListener listener) {
        this.listener = listener;
    }

    public void setProducts(List<GetProductDataModel> products) {
        this.products = new ArrayList<>(products);
        this.productsFull = new ArrayList<>(products);
        notifyDataSetChanged();
    }

    public void filter(String query) {
        products.clear();
        if (query.isEmpty()) {
            products.addAll(productsFull);
        } else {
            String filterPattern = query.toLowerCase().trim();
            for (GetProductDataModel item : productsFull) {
                if (item.getName().toLowerCase().contains(filterPattern)) {
                    products.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GetProductDataModel product = products.get(position);
        holder.textName.setText(product.getName());
        holder.textSku.setText("SKU: " + (product.getSku() != null ? product.getSku() : "N/A"));
        holder.textPrice.setText("Price: ₹" + product.getSellPrice());
        holder.textStock.setText("Stock: " + product.getStockQty());

        holder.itemView.setOnClickListener(v -> listener.onEdit(product));
        
        if (holder.btnDelete != null) {
            holder.btnDelete.setOnClickListener(v -> listener.onDelete(product));
        }
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textName, textSku, textPrice, textStock;
        ImageButton btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.text_product_name);
            textSku = itemView.findViewById(R.id.text_sku);
            textPrice = itemView.findViewById(R.id.text_price);
            textStock = itemView.findViewById(R.id.text_stock);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }
}
