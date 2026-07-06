package com.example.generalstoreapp.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.generalstoreapp.R;
import com.example.generalstoreapp.models.GetPurchasesDataModel;

import java.util.ArrayList;
import java.util.List;

public class PurchaseAdapter extends RecyclerView.Adapter<PurchaseAdapter.ViewHolder> {

    private List<GetPurchasesDataModel> purchases = new ArrayList<>();

    public void setData(List<GetPurchasesDataModel> purchases) {
        this.purchases = purchases != null ? purchases : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_purchase_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GetPurchasesDataModel purchase = purchases.get(position);
        holder.textNo.setText(purchase.getPurchaseNo());
        holder.textDate.setText(purchase.getPurchaseDate());
        holder.textAmount.setText(String.format("₹ %s", purchase.getTotalAmount()));
        holder.textStatus.setText(purchase.getStatus());
    }

    @Override
    public int getItemCount() {
        return purchases.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textNo, textDate, textAmount, textStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textNo = itemView.findViewById(R.id.text_purchase_no);
            textDate = itemView.findViewById(R.id.text_purchase_date);
            textAmount = itemView.findViewById(R.id.text_purchase_amount);
            textStatus = itemView.findViewById(R.id.text_purchase_status);
        }
    }
}
