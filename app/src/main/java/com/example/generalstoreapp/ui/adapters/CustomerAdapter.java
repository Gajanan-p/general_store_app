package com.example.generalstoreapp.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.generalstoreapp.R;
import com.example.generalstoreapp.models.GetCustomerDataModel;

import java.util.ArrayList;
import java.util.List;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.ViewHolder> {

    public interface OnCustomerActionListener {
        void onEdit(GetCustomerDataModel customer);
        void onDelete(GetCustomerDataModel customer);
    }

    private List<GetCustomerDataModel> customers = new ArrayList<>();
    private final OnCustomerActionListener listener;

    public CustomerAdapter(OnCustomerActionListener listener) {
        this.listener = listener;
    }

    public void setCustomers(List<GetCustomerDataModel> customers) {
        this.customers = customers;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_customer, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GetCustomerDataModel customer = customers.get(position);
        holder.textName.setText(customer.getName());
        holder.textPhone.setText("Phone: " + (customer.getPhone() != null ? customer.getPhone() : "N/A"));
        holder.textEmail.setText("Email: " + (customer.getEmail() != null ? customer.getEmail() : "N/A"));
        holder.textAddress.setText("Address: " + (customer.getAddressLine1() != null ? customer.getAddressLine1() : "N/A"));

        holder.itemView.setOnClickListener(v -> listener.onEdit(customer));
        holder.btnDelete.setOnClickListener(v -> listener.onDelete(customer));
    }

    @Override
    public int getItemCount() {
        return customers.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textName, textPhone, textEmail, textAddress;
        ImageButton btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.text_customer_name);
            textPhone = itemView.findViewById(R.id.text_customer_phone);
            textEmail = itemView.findViewById(R.id.text_customer_email);
            textAddress = itemView.findViewById(R.id.text_customer_address);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }
}
