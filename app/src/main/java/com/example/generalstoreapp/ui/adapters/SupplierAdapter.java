package com.example.generalstoreapp.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.generalstoreapp.R;
import com.example.generalstoreapp.models.GetSuppliersDataModel;

import java.util.ArrayList;
import java.util.List;

public class SupplierAdapter extends RecyclerView.Adapter<SupplierAdapter.ViewHolder> {

    public interface OnSupplierActionListener {
        void onEdit(GetSuppliersDataModel supplier);
        void onDelete(GetSuppliersDataModel supplier);
    }

    private List<GetSuppliersDataModel> suppliers = new ArrayList<>();
    private final OnSupplierActionListener listener;

    public SupplierAdapter(OnSupplierActionListener listener) {
        this.listener = listener;
    }

    public void setSuppliers(List<GetSuppliersDataModel> suppliers) {
        this.suppliers = suppliers;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_supplier, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GetSuppliersDataModel supplier = suppliers.get(position);
        holder.textName.setText(supplier.getName());
        holder.textPhone.setText("Phone: " + (supplier.getPhone() != null ? supplier.getPhone() : "N/A"));
        holder.textEmail.setText("Email: " + (supplier.getEmail() != null ? supplier.getEmail() : "N/A"));
        holder.textAddress.setText("Address: " + (supplier.getAddressLine1() != null ? supplier.getAddressLine1() : "N/A"));

        holder.itemView.setOnClickListener(v -> listener.onEdit(supplier));
        holder.btnDelete.setOnClickListener(v -> listener.onDelete(supplier));
    }

    @Override
    public int getItemCount() {
        return suppliers.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textName, textPhone, textEmail, textAddress;
        ImageButton btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.text_supplier_name);
            textPhone = itemView.findViewById(R.id.text_supplier_phone);
            textEmail = itemView.findViewById(R.id.text_supplier_email);
            textAddress = itemView.findViewById(R.id.text_supplier_address);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }
}