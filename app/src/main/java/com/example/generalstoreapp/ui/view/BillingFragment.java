package com.example.generalstoreapp.ui.view;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.generalstoreapp.R;
import com.example.generalstoreapp.models.CartItem;
import com.example.generalstoreapp.models.GetCustomerDataModel;
import com.example.generalstoreapp.ui.adapters.BillingItemAdapter;
import com.example.generalstoreapp.utils.PrintUtils;
import com.example.generalstoreapp.viewmodel.BillingViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BillingFragment extends Fragment implements View.OnClickListener, BillingItemAdapter.OnItemActionListener {

    private BillingViewModel viewModel;
    
    public static BillingFragment newInstance() {
        return new BillingFragment();
    }

    private BillingItemAdapter adapter;
    
    private AutoCompleteTextView editCustomer;
    private TextInputEditText editPhone;
    private TextView textInvoiceNo, textDate, textTotalAmount, textBalanceDue;
    private TextView lblTotalDisc, lblTotalTax, lblTotalQty, lblSubtotal;
    private EditText editReceivedAmount;
    private CheckBox cbReceived;
    private Button btnAddItems;
    private com.google.android.material.button.MaterialButton btnCancel, btnSave;
    private RecyclerView recyclerView;

    private List<GetCustomerDataModel> customerList = new ArrayList<>();
    private ArrayAdapter<String> custAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_billing, container, false);
        initViews(view);
        setupRecyclerView();
        setupListeners();

        return view;
    }

    private void initViews(View view) {
        editCustomer = view.findViewById(R.id.edit_billing_customer);
        custAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, new ArrayList<>());
        editCustomer.setAdapter(custAdapter);
        editPhone = view.findViewById(R.id.edit_billing_phone);
        textInvoiceNo = view.findViewById(R.id.text_billing_invoice_no);
        textDate = view.findViewById(R.id.text_billing_date);

        lblTotalDisc = view.findViewById(R.id.lbl_total_disc);
        lblTotalTax = view.findViewById(R.id.lbl_total_tax);
        lblTotalQty = view.findViewById(R.id.lbl_total_qty);
        lblSubtotal = view.findViewById(R.id.lbl_subtotal);

        textTotalAmount = view.findViewById(R.id.text_total_amount);
        textBalanceDue = view.findViewById(R.id.text_balance_due);
        editReceivedAmount = view.findViewById(R.id.edit_received_amount);
        cbReceived = view.findViewById(R.id.cb_received);

        btnAddItems = view.findViewById(R.id.button_add_items);
        btnCancel = view.findViewById(R.id.button_billing_cancel);
        btnSave = view.findViewById(R.id.button_billing_save);
        recyclerView = view.findViewById(R.id.recycler_billing_items);
    }

    private void setupRecyclerView() {
        adapter = new BillingItemAdapter(this);
        recyclerView.setLayoutManager(new androidx.recyclerview.widget.LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);
    }

    private void setupListeners() {
        btnAddItems.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

        editCustomer.setOnClickListener(v -> {
            if (editCustomer.getText().length() > 0) {
                editCustomer.showDropDown();
            }
        });

        editCustomer.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus && editCustomer.getText().length() > 0) {
                editCustomer.showDropDown();
            }
        });

        editCustomer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.fetchCustomers(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });

        editCustomer.setOnItemClickListener((parent, view, position, id) -> {
            GetCustomerDataModel customer = customerList.get(position);
            viewModel.selectCustomer(customer);
        });

        editReceivedAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                double paid = parseDouble(s.toString());
                viewModel.setPaidAmount(paid);
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });

        cbReceived.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Double total = viewModel.getGrandTotal().getValue();
                if (total != null) {
                    String formatted = String.format(Locale.US, "%.2f", total);
                    if (!editReceivedAmount.getText().toString().equals(formatted)) {
                        editReceivedAmount.setText(formatted);
                    }
                }
            } else {
                String current = editReceivedAmount.getText().toString();
                if (!current.equals("0") && !current.equals("0.00") && !current.isEmpty()) {
                    editReceivedAmount.setText("0");
                }
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(BillingViewModel.class);
        viewModel.init(requireContext());

        observeViewModel();
    }

    private void observeViewModel() {
        viewModel.getCustomersLiveData().observe(getViewLifecycleOwner(), customers -> {
            this.customerList = customers;
            List<String> names = new ArrayList<>();
            for (GetCustomerDataModel c : customers) names.add(c.getName());
            custAdapter.clear();
            custAdapter.addAll(names);
            custAdapter.notifyDataSetChanged();
            editCustomer.showDropDown();
        });

        viewModel.getSelectedCustomer().observe(getViewLifecycleOwner(), customer -> {
            if (customer != null) {
                editPhone.setText(customer.getPhone());
            }
        });

        viewModel.getCartItems().observe(getViewLifecycleOwner(), items -> {
            adapter.setItems(items);
        });

        viewModel.getSubtotal().observe(getViewLifecycleOwner(), val -> lblSubtotal.setText(String.format("Subtotal: %.2f", val)));
        viewModel.getTotalGst().observe(getViewLifecycleOwner(), val -> lblTotalTax.setText(String.format("Total Tax Amt: %.2f", val)));
        viewModel.getTotalQty().observe(getViewLifecycleOwner(), val -> lblTotalQty.setText(String.format("Total Qty: %.1f", val)));
        viewModel.getTotalDiscount().observe(getViewLifecycleOwner(), val -> lblTotalDisc.setText(String.format("Total Disc: %.1f", val)));

        viewModel.getGrandTotal().observe(getViewLifecycleOwner(), val -> {
            textTotalAmount.setText(String.format("%.2f", val));
            if (cbReceived.isChecked()) {
                String formatted = String.format(Locale.US, "%.2f", val);
                if (!editReceivedAmount.getText().toString().equals(formatted)) {
                    editReceivedAmount.setText(formatted);
                }
            }
        });
        viewModel.getBalance().observe(getViewLifecycleOwner(), val -> textBalanceDue.setText(String.format("%.2f", val)));
        
        viewModel.getInvoiceNo().observe(getViewLifecycleOwner(), no -> textInvoiceNo.setText(no));
        viewModel.getInvoiceDate().observe(getViewLifecycleOwner(), date -> textDate.setText(date));

        viewModel.getSuccess().observe(getViewLifecycleOwner(), success -> {
            if (success) {
                showPrintConfirmationDialog();
            }
        });

        viewModel.getError().observe(getViewLifecycleOwner(), error -> {
            if (error != null) Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show();
        });
    }

    private void showPrintConfirmationDialog() {
        new AlertDialog.Builder(requireContext())
                .setTitle("Invoice Saved")
                .setMessage("Invoice saved successfully! Do you want to print the bill?")
                .setPositiveButton("Print", (dialog, which) -> {
                    printBill();
                    viewModel.reset();
                    requireActivity().onBackPressed();
                })
                .setNegativeButton("No", (dialog, which) -> {
                    viewModel.reset();
                    requireActivity().onBackPressed();
                })
                .setCancelable(false)
                .show();
    }

    private void printBill() {
        GetCustomerDataModel customer = viewModel.getSelectedCustomer().getValue();
        List<CartItem> items = viewModel.getCartItems().getValue();
        String invoiceNo = viewModel.getInvoiceNo().getValue();
        String date = viewModel.getInvoiceDate().getValue();
        double subtotal = viewModel.getSubtotal().getValue() != null ? viewModel.getSubtotal().getValue() : 0.0;
        double discount = viewModel.getTotalDiscount().getValue() != null ? viewModel.getTotalDiscount().getValue() : 0.0;
        double tax = viewModel.getTotalGst().getValue() != null ? viewModel.getTotalGst().getValue() : 0.0;
        double grandTotal = viewModel.getGrandTotal().getValue() != null ? viewModel.getGrandTotal().getValue() : 0.0;
        double balance = viewModel.getBalance().getValue() != null ? viewModel.getBalance().getValue() : 0.0;
        double paid = grandTotal - balance;

        PrintUtils.printInvoice(requireContext(), customer, items, invoiceNo, date, subtotal, discount, tax, grandTotal, paid, balance);
    }

    private String formatCurrency(Double value) {
        return String.format(Locale.getDefault(), "₹%.2f", value);
    }

    private double parseDouble(String val) {
        try {
            return Double.parseDouble(val);
        } catch (Exception e) {
            return 0.0;
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.button_add_items) {
            Navigation.findNavController(view).navigate(R.id.itemsFragment);
        } else if (id == R.id.button_billing_save) {
            viewModel.submitBill();
        } else if (id == R.id.button_billing_cancel) {
            viewModel.reset();
            requireActivity().onBackPressed();
        }
    }

    @Override
    public void onRemove(CartItem item) {
        viewModel.removeProductFromCart(item);
    }

    @Override
    public void onQuantityChanged(CartItem item, int newQty) {
        viewModel.updateQuantity(item, newQty);
    }
}
