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
import com.example.generalstoreapp.models.AddCustomerRequest;
import com.example.generalstoreapp.models.GetCustomerDataModel;
import com.example.generalstoreapp.ui.adapters.CustomerAdapter;
import com.example.generalstoreapp.viewmodel.CustomersViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;

public class CustomersFragment extends Fragment implements CustomerAdapter.OnCustomerActionListener {

    private CustomersViewModel viewModel;
    private CustomerAdapter adapter;
    private RecyclerView recyclerView;
    private FloatingActionButton fabAdd;
    private ProgressBar progressBar;
    private TextView textEmpty;
    private TextInputEditText editSearch;

    private String currentSearch = "";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customers, container, false);

        recyclerView = view.findViewById(R.id.recycler_customers);
        fabAdd = view.findViewById(R.id.fab_add_customer);
        progressBar = view.findViewById(R.id.progress_bar);
        textEmpty = view.findViewById(R.id.text_empty);
        editSearch = view.findViewById(R.id.edit_search);

        adapter = new CustomerAdapter(this);
        recyclerView.setAdapter(adapter);

        fabAdd.setOnClickListener(v -> showCustomerDialog(null));
        
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
                refreshCustomers();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void refreshCustomers() {
        if (viewModel != null) {
            viewModel.fetchCustomers(currentSearch, 1, true);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(CustomersViewModel.class);
        viewModel.init(requireContext());

        observeViewModel();
        refreshCustomers();
        
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (!recyclerView.canScrollVertically(1)) {
                    viewModel.fetchCustomers(currentSearch, 1, false);
                }
            }
        });
    }

    private void observeViewModel() {
        viewModel.getCustomersLiveData().observe(getViewLifecycleOwner(), customers -> {
            adapter.setCustomers(customers);
            textEmpty.setVisibility(customers.isEmpty() ? View.VISIBLE : View.GONE);
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
                refreshCustomers();
            }
        });
    }

    private void showCustomerDialog(@Nullable GetCustomerDataModel existingCustomer) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_customer, null);
        builder.setView(dialogView);

        TextView textTitle = dialogView.findViewById(R.id.text_title);
        TextInputEditText editName = dialogView.findViewById(R.id.edit_customer_name);
        TextInputEditText editPhone = dialogView.findViewById(R.id.edit_customer_phone);
        TextInputEditText editEmail = dialogView.findViewById(R.id.edit_customer_email);
        TextInputEditText editAddress = dialogView.findViewById(R.id.edit_address_line1);
        TextInputEditText editCity = dialogView.findViewById(R.id.edit_city);
        TextInputEditText editState = dialogView.findViewById(R.id.edit_state);
        TextInputEditText editPincode = dialogView.findViewById(R.id.edit_pincode);
        TextInputEditText editBalance = dialogView.findViewById(R.id.edit_opening_balance);
        SwitchMaterial switchActive = dialogView.findViewById(R.id.switch_active);

        if (existingCustomer != null) {
            textTitle.setText(R.string.edit_customer_title);
            editName.setText(existingCustomer.getName());
            editPhone.setText(existingCustomer.getPhone());
            editEmail.setText(existingCustomer.getEmail());
            editAddress.setText(existingCustomer.getAddressLine1());
            editCity.setText(existingCustomer.getCity());
            editState.setText(existingCustomer.getState());
            editPincode.setText(existingCustomer.getPincode());
            editBalance.setText(String.valueOf(existingCustomer.getOpeningBalance()));
            switchActive.setChecked(existingCustomer.getIsActive() == 1);
        }

        AlertDialog dialog = builder.create();

        dialogView.findViewById(R.id.button_submit).setOnClickListener(v -> {
            AddCustomerRequest request = new AddCustomerRequest();
            request.setName(editName.getText().toString());
            request.setPhone(editPhone.getText().toString());
            request.setEmail(editEmail.getText().toString());
            request.setAddressLine1(editAddress.getText().toString());
            request.setCity(editCity.getText().toString());
            request.setState(editState.getText().toString());
            request.setPincode(editPincode.getText().toString());
            request.setOpeningBalance(parseInt(editBalance.getText().toString()));
            request.setIsActive(switchActive.isChecked() ? 1 : 0);

            if (existingCustomer == null) {
                viewModel.createCustomer(request);
            } else {
                viewModel.updateCustomer(existingCustomer.getId(), request);
            }
            dialog.dismiss();
        });

        dialog.show();
    }

    private int parseInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    @Override
    public void onEdit(GetCustomerDataModel customer) {
        showCustomerDialog(customer);
    }

    @Override
    public void onDelete(GetCustomerDataModel customer) {
        new AlertDialog.Builder(requireContext())
                .setTitle(R.string.delete_customer_title)
                .setMessage(getString(R.string.delete_confirm_msg, customer.getName()))
                .setPositiveButton(R.string.submit, (dialog, which) -> viewModel.deleteCustomer(customer.getId()))
                .setNegativeButton(R.string.cancel, null)
                .show();
    }
}
