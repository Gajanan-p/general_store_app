package com.example.generalstoreapp.ui.view;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.generalstoreapp.R;
import com.example.generalstoreapp.models.GetBillingDataModel;
import com.example.generalstoreapp.models.GetCustomerDataModel;
import com.example.generalstoreapp.ui.adapters.BillingAdapter;
import com.example.generalstoreapp.viewmodel.SalesViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class SalesFragment extends Fragment implements BillingAdapter.OnBillingActionListener {

    private SalesViewModel viewModel;
    private BillingAdapter adapter;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private MaterialButton fabAdd;

    private AutoCompleteTextView autoCustomer;
    private TextInputEditText editFromDate, editToDate;
    private List<GetCustomerDataModel> customerList = new ArrayList<>();
    private Integer selectedCustomerId = null;
    private final Calendar fromCalendar = Calendar.getInstance();
    private final Calendar toCalendar = Calendar.getInstance();
    private final SimpleDateFormat displayFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
    private final SimpleDateFormat apiFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sales, container, false);

        recyclerView = view.findViewById(R.id.recycler_sales);
        progressBar = view.findViewById(R.id.progress_bar);
        fabAdd = view.findViewById(R.id.fab_add_billing);
        
        autoCustomer = view.findViewById(R.id.auto_complete_customer);
        editFromDate = view.findViewById(R.id.edit_from_date);
        editToDate = view.findViewById(R.id.edit_to_date);

        adapter = new BillingAdapter(this);
        recyclerView.setAdapter(adapter);

        fabAdd.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.billingFragment);
        });

        setupFilters();

        return view;
    }

    private void setupFilters() {
        editFromDate.setOnClickListener(v -> showDatePicker(true));
        editToDate.setOnClickListener(v -> showDatePicker(false));

        autoCustomer.setOnItemClickListener((parent, view, position, id) -> {
            String selectedName = (String) parent.getItemAtPosition(position);
            for (GetCustomerDataModel c : customerList) {
                if (c.getName().equals(selectedName)) {
                    selectedCustomerId = c.getId();
                    break;
                }
            }
            updateFilter();
        });

        autoCustomer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()) {
                    selectedCustomerId = null;
                    updateFilter();
                }
            }
        });
        
        // Initial dates: current month
        fromCalendar.set(Calendar.DAY_OF_MONTH, 1);
        editFromDate.setText(displayFormat.format(fromCalendar.getTime()));
        editToDate.setText(displayFormat.format(toCalendar.getTime()));
    }

    private void showDatePicker(boolean isFromDate) {
        Calendar cal = isFromDate ? fromCalendar : toCalendar;
        new DatePickerDialog(requireContext(), (view, year, month, dayOfMonth) -> {
            cal.set(Calendar.YEAR, year);
            cal.set(Calendar.MONTH, month);
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            
            if (isFromDate) {
                editFromDate.setText(displayFormat.format(cal.getTime()));
            } else {
                editToDate.setText(displayFormat.format(cal.getTime()));
            }
            updateFilter();
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void updateFilter() {
        String from = apiFormat.format(fromCalendar.getTime());
        String to = apiFormat.format(toCalendar.getTime());
        viewModel.fetchBillingList(selectedCustomerId != null ? selectedCustomerId : 0, from, to);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(SalesViewModel.class);
        viewModel.init(requireContext());

        observeViewModel();
        viewModel.fetchCustomers();
        updateFilter();
    }

    private void observeViewModel() {
        viewModel.getBillingListLiveData().observe(getViewLifecycleOwner(), billingList -> {
            adapter.setData(billingList);
        });

        viewModel.getCustomersLiveData().observe(getViewLifecycleOwner(), customers -> {
            this.customerList = customers;
            List<String> names = new ArrayList<>();
            for (GetCustomerDataModel c : customers) {
                names.add(c.getName());
            }
            ArrayAdapter<String> custAdapter = new ArrayAdapter<>(requireContext(),
                    android.R.layout.simple_dropdown_item_1line, names);
            autoCustomer.setAdapter(custAdapter);
        });

        viewModel.getLoadingLiveData().observe(getViewLifecycleOwner(), isLoading -> {
            progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        });

        viewModel.getErrorLiveData().observe(getViewLifecycleOwner(), error -> {
            if (error != null) Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onClick(GetBillingDataModel billing) {
        Bundle bundle = new Bundle();
        bundle.putInt("billing_id", billing.getId());
        Navigation.findNavController(requireView()).navigate(R.id.billingDetailFragment, bundle);
    }
}
