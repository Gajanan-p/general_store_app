package com.example.generalstoreapp.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.generalstoreapp.R;
import com.example.generalstoreapp.models.CartItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BillingItemAdapter extends RecyclerView.Adapter<BillingItemAdapter.ViewHolder> {

    public interface OnItemActionListener {
        void onRemove(CartItem item);
        void onQuantityChanged(CartItem item, int newQty);
    }

    private List<CartItem> items = new ArrayList<>();
    private final OnItemActionListener listener;

    public BillingItemAdapter(OnItemActionListener listener) {
        this.listener = listener;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_billing_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CartItem item = items.get(position);
        holder.textIndex.setText(String.format(Locale.getDefault(), "#%d", position + 1));
        holder.textName.setText(item.getProduct().getName());
        holder.textTotal.setText(String.format(Locale.getDefault(), "₹ %.0f", item.getFinalAmount()));
        
        holder.textDetails.setText(String.format(Locale.getDefault(), "%d x %.2f = ₹ %.2f", 
                item.getQuantity(), item.getPrice(), item.getLineTotal()));
        
        holder.lblDisc.setText(String.format(Locale.getDefault(), "Discount (%%): %.0f", item.getDiscountPercent()));
        holder.textDisc.setText(String.format(Locale.getDefault(), "₹ %.0f", item.getDiscountAmount()));
        
        holder.textTax.setText(String.format(Locale.getDefault(), "₹ %.0f", item.getGstAmount()));
        holder.lblTax.setText(String.format(Locale.getDefault(), "Tax: %d%%", item.getProduct().getGstPercent()));

        holder.itemView.setOnLongClickListener(v -> {
            listener.onRemove(item);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textIndex, textName, textTotal, textDetails, textDisc, textTax, lblDisc, lblTax;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textIndex = itemView.findViewById(R.id.text_item_index);
            textName = itemView.findViewById(R.id.text_product_name);
            textTotal = itemView.findViewById(R.id.text_item_total);
            textDetails = itemView.findViewById(R.id.text_item_details);
            textDisc = itemView.findViewById(R.id.text_discount_amount);
            textTax = itemView.findViewById(R.id.text_tax_amount);
            lblDisc = itemView.findViewById(R.id.label_discount);
            lblTax = itemView.findViewById(R.id.label_tax);
        }
    }
}
