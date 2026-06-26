package com.example.generalstoreapp.ui.view;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.generalstoreapp.R;
import com.example.generalstoreapp.models.GetUnitsDataModel;
import com.example.generalstoreapp.ui.adapters.UnitAdapter;
import com.example.generalstoreapp.viewmodel.UnitsViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class UnitsFragment extends Fragment implements UnitAdapter.OnUnitActionListener {

    private UnitsViewModel viewModel;
    private UnitAdapter adapter;
    private RecyclerView recyclerView;
    private MaterialButton fabAdd;
    private ProgressBar progressBar;
    private TextView textEmpty;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_units, container, false);

        recyclerView = view.findViewById(R.id.recycler_units);
        fabAdd = view.findViewById(R.id.fab_add_unit);
        progressBar = view.findViewById(R.id.progress_bar);
        textEmpty = view.findViewById(R.id.text_empty);

        adapter = new UnitAdapter(this);
        recyclerView.setAdapter(adapter);

        fabAdd.setOnClickListener(v -> showUnitDialog(null));

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(UnitsViewModel.class);
        viewModel.init(requireContext());

        observeViewModel();
        viewModel.fetchUnits();
    }

    private void observeViewModel() {
        viewModel.getUnitsLiveData().observe(getViewLifecycleOwner(), units -> {
            adapter.setUnits(units);
            textEmpty.setVisibility(units.isEmpty() ? View.VISIBLE : View.GONE);
        });

        viewModel.getLoadingLiveData().observe(getViewLifecycleOwner(), isLoading -> {
            progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        });

        viewModel.getErrorLiveData().observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show();
            }
        });

        viewModel.getSuccessLiveData().observe(getViewLifecycleOwner(), success -> {
            if (success) {
                Toast.makeText(requireContext(), getString(R.string.success), Toast.LENGTH_SHORT).show();
                viewModel.fetchUnits();
            }
        });
    }

    private void showUnitDialog(@Nullable GetUnitsDataModel existingUnit) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_unit, null);
        builder.setView(dialogView);

        TextView textTitle = dialogView.findViewById(R.id.text_title);
        TextInputEditText editName = dialogView.findViewById(R.id.edit_unit_name);
        TextInputEditText editSymbol = dialogView.findViewById(R.id.edit_unit_symbol);

        if (existingUnit != null) {
            textTitle.setText(R.string.edit_unit_title);
            editName.setText(existingUnit.getName());
            editSymbol.setText(existingUnit.getSymbol());
        }

        AlertDialog dialog = builder.create();

        dialogView.findViewById(R.id.button_submit).setOnClickListener(v -> {
            String name = editName.getText().toString();
            String symbol = editSymbol.getText().toString();
            if (existingUnit == null) {
                viewModel.addUnit(name, symbol);
            } else {
                viewModel.updateUnit(existingUnit.getId(), name, symbol);
            }
            dialog.dismiss();
        });

        dialog.show();
    }

    @Override
    public void onEdit(GetUnitsDataModel unit) {
        showUnitDialog(unit);
    }

    @Override
    public void onDelete(GetUnitsDataModel unit) {
        new AlertDialog.Builder(requireContext())
                .setTitle(R.string.delete_unit_title)
                .setMessage(getString(R.string.delete_confirm_msg, unit.getName()))
                .setPositiveButton(R.string.submit, (dialog, which) -> viewModel.deleteUnit(unit.getId()))
                .setNegativeButton(R.string.cancel, null)
                .show();
    }
}
