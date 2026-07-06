package com.example.generalstoreapp.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.generalstoreapp.R;
import com.example.generalstoreapp.models.GetRoleModel;

import java.util.ArrayList;
import java.util.List;

public class RoleAdapter extends RecyclerView.Adapter<RoleAdapter.ViewHolder> {

    private List<GetRoleModel> roles = new ArrayList<>();
    private final OnRoleActionListener listener;

    public interface OnRoleActionListener {
        void onRoleClick(GetRoleModel role);
    }

    public RoleAdapter(OnRoleActionListener listener) {
        this.listener = listener;
    }

    public void setRoles(List<GetRoleModel> roles) {
        this.roles = roles != null ? roles : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_role_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GetRoleModel role = roles.get(position);
        holder.textName.setText(role.getName());
        holder.textDesc.setText(role.getDescription());
        holder.itemView.setOnClickListener(v -> listener.onRoleClick(role));
    }

    @Override
    public int getItemCount() {
        return roles.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textName, textDesc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.text_role_name);
            textDesc = itemView.findViewById(R.id.text_role_description);
        }
    }
}
