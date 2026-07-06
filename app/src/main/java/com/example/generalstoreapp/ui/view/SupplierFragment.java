package com.example.generalstoreapp.ui.view;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.example.generalstoreapp.models.AddSuppliersRequest;
import com.example.generalstoreapp.models.GetSuppliersDataModel;
import com.example.generalstoreapp.ui.adapters.SupplierAdapter;
import com.example.generalstoreapp.viewmodel.SupplierViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;

public class SupplierFragment extends Fragment implements SupplierAdapter.OnSupplierActionListener {

    private SupplierViewModel viewModel;
    private SupplierAdapter adapter;
    private RecyclerView recyclerView;
    private MaterialButton fabAdd;
    private ProgressBar progressBar;
    private TextView textEmpty;
    private TextInputEditText editSearch;

    private String currentSearch = "";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_supplier, container, false);

        recyclerView = view.findViewById(R.id.recycler_suppliers);
        fabAdd = view.findViewById(R.id.fab_add_supplier);
        progressBar = view.findViewById(R.id.progress_bar);
        textEmpty = view.findViewById(R.id.text_empty);
        editSearch = view.findViewById(R.id.edit_search);

        adapter = new SupplierAdapter(this);
        recyclerView.setAdapter(adapter);

        fabAdd.setOnClickListener(v -> showSupplierDialog(null));
        
        setupSearch();

        return view;
    }

    private void setupSearch() {
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                currentSearch = s.toString();
                refreshSuppliers();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void refreshSuppliers() {
        if (viewModel != null) {
            viewModel.fetchSuppliers(currentSearch, true, true);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(SupplierViewModel.class);
        viewModel.init(requireContext());

        observeViewModel();
        refreshSuppliers();
        
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (!recyclerView.canScrollVertically(1)) {
                    viewModel.fetchSuppliers(currentSearch, true, false);
                }
            }
        });
    }

    private void observeViewModel() {
        viewModel.getSuppliersLiveData().observe(getViewLifecycleOwner(), suppliers -> {
            adapter.setSuppliers(suppliers);
            textEmpty.setVisibility(suppliers.isEmpty() ? View.VISIBLE : View.GONE);
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
                refreshSuppliers();
            }
        });
    }

    private void showSupplierDialog(@Nullable GetSuppliersDataModel existingSupplier) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_supplier, null);
        builder.setView(dialogView);

        TextView textTitle = dialogView.findViewById(R.id.text_title);
        TextInputEditText editName = dialogView.findViewById(R.id.edit_supplier_name);
        TextInputEditText editPhone = dialogView.findViewById(R.id.edit_supplier_phone);
        TextInputEditText editEmail = dialogView.findViewById(R.id.edit_supplier_email);
        TextInputEditText editGst = dialogView.findViewById(R.id.edit_gstin);
        TextInputEditText editAddress = dialogView.findViewById(R.id.edit_address_line1);
        TextInputEditText editCity = dialogView.findViewById(R.id.edit_city);
        TextInputEditText editState = dialogView.findViewById(R.id.edit_state);
        TextInputEditText editPincode = dialogView.findViewById(R.id.edit_pincode);
        TextInputEditText editBalance = dialogView.findViewById(R.id.edit_opening_balance);
        TextInputEditText editNotes = dialogView.findViewById(R.id.edit_notes);
        SwitchMaterial switchActive = dialogView.findViewById(R.id.switch_active);

        if (existingSupplier != null) {
            textTitle.setText(R.string.edit_supplier_title);
            editName.setText(existingSupplier.getName());
            editPhone.setText(existingSupplier.getPhone());
            editEmail.setText(existingSupplier.getEmail());
            editGst.setText(existingSupplier.getGstNumber());
            editAddress.setText(existingSupplier.getAddressLine1());
            editCity.setText(existingSupplier.getCity());
            editState.setText(existingSupplier.getState());
            editPincode.setText(existingSupplier.getPincode());
            editBalance.setText(existingSupplier.getOpeningBalance());
            editNotes.setText(existingSupplier.getNotes());
            switchActive.setChecked(Boolean.TRUE.equals(existingSupplier.getIsActive()));
        }

        AlertDialog dialog = builder.create();

        dialogView.findViewById(R.id.button_submit).setOnClickListener(v -> {
            AddSuppliersRequest request = new AddSuppliersRequest();
            request.setName(editName.getText().toString());
            request.setPhone(editPhone.getText().toString());
            request.setEmail(editEmail.getText().toString());
            request.setGstNumber(editGst.getText().toString());
            request.setAddressLine1(editAddress.getText().toString());
            request.setCity(editCity.getText().toString());
            request.setState(editState.getText().toString());
            request.setPincode(editPincode.getText().toString());
            request.setOpeningBalance(parseDouble(editBalance.getText().toString()));
            request.setNotes(editNotes.getText().toString());
            request.setIsActive(switchActive.isChecked());

            if (existingSupplier == null) {
                viewModel.createSupplier(request);
            } else {
                viewModel.updateSupplier(existingSupplier.getId(), request);
            }
            dialog.dismiss();
        });

        dialog.show();
    }

    private double parseDouble(String value) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    @Override
    public void onEdit(GetSuppliersDataModel supplier) {
        showSupplierDialog(supplier);
    }

    @Override
    public void onDelete(GetSuppliersDataModel supplier) {
        new AlertDialog.Builder(requireContext())
                .setTitle(R.string.delete_supplier_title)
                .setMessage(getString(R.string.delete_confirm_msg, supplier.getName()))
                .setPositiveButton(R.string.submit, (dialog, which) -> viewModel.deleteSupplier(supplier.getId()))
                .setNegativeButton(R.string.cancel, null)
                .show();
    }
}