package com.example.generalstoreapp.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.generalstoreapp.R;
import com.example.generalstoreapp.models.Users;

import java.util.ArrayList;
import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {

    private List<Users> users = new ArrayList<>();
    private final OnUserActionListener listener;

    public interface OnUserActionListener {
        void onUserClick(Users user);
    }

    public UsersAdapter(OnUserActionListener listener) {
        this.listener = listener;
    }

    public void setUsers(List<Users> users) {
        this.users = users != null ? users : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_user_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Users user = users.get(position);
        holder.textName.setText(user.getName());
        holder.textEmail.setText(user.getEmail());
        holder.textRole.setText(user.getRoleName());
        holder.itemView.setOnClickListener(v -> listener.onUserClick(user));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textName, textEmail, textRole;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.text_user_name);
            textEmail = itemView.findViewById(R.id.text_user_email);
            textRole = itemView.findViewById(R.id.text_user_role);
        }
    }
}
