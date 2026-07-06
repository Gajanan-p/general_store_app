package com.example.generalstoreapp.ui.view;

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
import com.example.generalstoreapp.models.GetRoleModel;
import com.example.generalstoreapp.models.RoleRequest;
import com.example.generalstoreapp.ui.adapters.RoleAdapter;
import com.example.generalstoreapp.viewmodel.RoleViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import android.app.AlertDialog;

public class RoleFragment extends Fragment implements RoleAdapter.OnRoleActionListener {

    private RoleViewModel viewModel;
    private RoleAdapter adapter;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TextView textEmpty;
    private MaterialButton fabAdd;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_role, container, false);

        recyclerView = view.findViewById(R.id.recycler_roles);
        progressBar = view.findViewById(R.id.progress_bar);
        textEmpty = view.findViewById(R.id.text_empty);
        fabAdd = view.findViewById(R.id.fab_add_role);

        adapter = new RoleAdapter(this);
        recyclerView.setAdapter(adapter);

        fabAdd.setOnClickListener(v -> showRoleDialog());

        return view;
    }

    private void showRoleDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_role, null);
        builder.setView(dialogView);

        TextInputEditText editName = dialogView.findViewById(R.id.edit_role_name);
        TextInputEditText editDesc = dialogView.findViewById(R.id.edit_role_description);

        AlertDialog dialog = builder.create();

        dialogView.findViewById(R.id.button_submit).setOnClickListener(v -> {
            String name = editName.getText() != null ? editName.getText().toString() : "";
            String desc = editDesc.getText() != null ? editDesc.getText().toString() : "";
            if (!name.isEmpty()) {
                viewModel.createRole(name, desc);
                dialog.dismiss();
            } else {
                editName.setError(getString(R.string.all_fields_required));
            }
        });

        dialog.show();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(RoleViewModel.class);
        viewModel.init(requireContext());

        observeViewModel();
        viewModel.fetchRoles();
    }

    private void observeViewModel() {
        viewModel.getRolesLiveData().observe(getViewLifecycleOwner(), roles -> {
            adapter.setRoles(roles);
            textEmpty.setVisibility(roles == null || roles.isEmpty() ? View.VISIBLE : View.GONE);
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
                Toast.makeText(requireContext(), getString(R.string.operation_successful), Toast.LENGTH_SHORT).show();
                viewModel.fetchRoles();
            }
        });
    }

    @Override
    public void onRoleClick(GetRoleModel role) {
        // Handle role click, maybe show permissions
        Toast.makeText(requireContext(), "Role: " + role.getName(), Toast.LENGTH_SHORT).show();
    }
}
