package com.example.generalstoreapp.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.generalstoreapp.R;
import com.example.generalstoreapp.models.GetBillingDataModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BillingAdapter extends RecyclerView.Adapter<BillingAdapter.ViewHolder> {

    public interface OnBillingActionListener {
        void onClick(GetBillingDataModel billing);
    }

    private List<GetBillingDataModel> billingList = new ArrayList<>();
    private final OnBillingActionListener listener;

    public BillingAdapter(OnBillingActionListener listener) {
        this.listener = listener;
    }

    public void setData(List<GetBillingDataModel> newList) {
        this.billingList = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_billing, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GetBillingDataModel billing = billingList.get(position);
        holder.textInvoiceNo.setText(billing.getInvoiceNo());
        holder.textDate.setText(billing.getInvoiceDate());
        holder.textCustomerName.setText(String.format(Locale.getDefault(),(billing.getCustomer() != null) ? billing.getCustomer().getName() : "N/A"));
        holder.textTotal.setText(String.format(Locale.getDefault(), "Total: ₹%.2f", billing.getTotalAmount() != null ? billing.getTotalAmount() : 0.0));
        
        double balance = billing.getDueAmount() != null ? (double) billing.getDueAmount() : 0.0;
        if (balance <= 0) {
            holder.textStatus.setText("Paid");
            holder.textStatus.setBackgroundResource(R.color.light_green);
            holder.textStatus.setTextColor(holder.itemView.getContext().getResources().getColor(R.color.green));
        } else if (billing.getPaidAmount() != null && billing.getPaidAmount() > 0) {
            holder.textStatus.setText("Partial");
            holder.textStatus.setBackgroundResource(R.color.orange);
            holder.textStatus.setTextColor(holder.itemView.getContext().getResources().getColor(R.color.white));
        } else {
            holder.textStatus.setText("Unpaid");
            holder.textStatus.setBackgroundResource(R.color.light_red);
            holder.textStatus.setTextColor(holder.itemView.getContext().getResources().getColor(R.color.red));
        }

        holder.itemView.setOnClickListener(v -> listener.onClick(billing));
    }

    @Override
    public int getItemCount() {
        return billingList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textInvoiceNo, textDate, textCustomerName, textTotal, textStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textInvoiceNo = itemView.findViewById(R.id.text_invoice_no);
            textDate = itemView.findViewById(R.id.text_invoice_date);
            textCustomerName = itemView.findViewById(R.id.text_customer_name);
            textTotal = itemView.findViewById(R.id.text_grand_total);
            textStatus = itemView.findViewById(R.id.text_status);
        }
    }
}
