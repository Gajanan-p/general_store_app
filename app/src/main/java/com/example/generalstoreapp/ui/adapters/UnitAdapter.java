package com.example.generalstoreapp.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.generalstoreapp.R;
import com.example.generalstoreapp.models.GetUnitsDataModel;

import java.util.ArrayList;
import java.util.List;

public class UnitAdapter extends RecyclerView.Adapter<UnitAdapter.ViewHolder> {

    public interface OnUnitActionListener {
        void onEdit(GetUnitsDataModel unit);
        void onDelete(GetUnitsDataModel unit);
    }

    private List<GetUnitsDataModel> units = new ArrayList<>();
    private final OnUnitActionListener listener;

    public UnitAdapter(OnUnitActionListener listener) {
        this.listener = listener;
    }

    public void setUnits(List<GetUnitsDataModel> units) {
        this.units = units;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_unit, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GetUnitsDataModel unit = units.get(position);
        holder.textName.setText(unit.getName());
        holder.textSymbol.setText(unit.getSymbol());

        holder.itemView.setOnClickListener(v -> listener.onEdit(unit));
        holder.btnDelete.setOnClickListener(v -> listener.onDelete(unit));
    }

    @Override
    public int getItemCount() {
        return units.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textName, textSymbol;
        ImageButton btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.text_unit_name);
            textSymbol = itemView.findViewById(R.id.text_symbol);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }
}
