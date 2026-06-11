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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.generalstoreapp.R;
import com.example.generalstoreapp.models.BillingItem;
import com.example.generalstoreapp.models.GetBillingDataModel;
import com.example.generalstoreapp.viewmodel.HomeViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BillingDetailFragment extends Fragment {

    private static final String ARG_BILLING_ID = "billing_id";
    private int billingId;
    private HomeViewModel viewModel;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private DetailItemAdapter adapter;

    private TextView textInvoiceNo, textDate, textCustomer, textStatus, textSubtotal, textDiscount, textTax, textTotal, textPaid, textDue;
    private View btnCancelInvoice;

    public static BillingDetailFragment newInstance(int billingId) {
        BillingDetailFragment fragment = new BillingDetailFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_BILLING_ID, billingId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            billingId = getArguments().getInt(ARG_BILLING_ID);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_billing_detail, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        recyclerView = view.findViewById(R.id.recycler_detail_items);
        progressBar = view.findViewById(R.id.progress_bar);
        
        textInvoiceNo = view.findViewById(R.id.text_detail_invoice_no);
        textDate = view.findViewById(R.id.text_detail_date);
        textCustomer = view.findViewById(R.id.text_detail_customer);
        textStatus = view.findViewById(R.id.text_detail_status);
        textSubtotal = view.findViewById(R.id.text_detail_subtotal);
        textDiscount = view.findViewById(R.id.text_detail_discount);
        textTax = view.findViewById(R.id.text_detail_tax);
        textTotal = view.findViewById(R.id.text_detail_total);
        textPaid = view.findViewById(R.id.text_detail_paid);
        textDue = view.findViewById(R.id.text_detail_due);
        btnCancelInvoice = view.findViewById(R.id.btn_cancel_invoice);

        adapter = new DetailItemAdapter();
        recyclerView.setAdapter(adapter);

        btnCancelInvoice.setOnClickListener(v -> showCancelDialog());
    }

    private void showCancelDialog() {
        new AlertDialog.Builder(requireContext())
                .setTitle("Cancel Invoice")
                .setMessage("Are you sure you want to cancel this invoice?")
                .setPositiveButton("Yes, Cancel", (dialog, which) -> viewModel.cancelBilling(billingId))
                .setNegativeButton("No", null)
                .show();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        viewModel.init(requireContext());

        observeViewModel();
        viewModel.fetchBillingById(billingId);
    }

    private void observeViewModel() {
        viewModel.getBillingDetailLiveData().observe(getViewLifecycleOwner(), detail -> {
            if (detail != null) {
                textInvoiceNo.setText(detail.getInvoiceNo());
                textDate.setText("Date: " + detail.getInvoiceDate());
                textCustomer.setText("Customer ID: " + detail.getCustomerId());
                textStatus.setText(detail.getStatus());
                
                textSubtotal.setText(formatCurrency(detail.getSubtotal()));
                textDiscount.setText(formatCurrency(detail.getDiscountAmount()));
                textTax.setText(formatCurrency(detail.getTaxAmount()));
                textTotal.setText(formatCurrency(detail.getTotalAmount()));
                textPaid.setText(formatCurrency(detail.getPaidAmount()));
                textDue.setText(formatCurrency(detail.getDueAmount()));

                adapter.setItems(detail.getItems());

                // Show cancel button only if status is POSTED or something active
                if ("POSTED".equals(detail.getStatus()) || "ACTIVE".equals(detail.getStatus())) {
                    btnCancelInvoice.setVisibility(View.VISIBLE);
                } else {
                    btnCancelInvoice.setVisibility(View.GONE);
                }
            }
        });

        viewModel.getCancelSuccessLiveData().observe(getViewLifecycleOwner(), success -> {
            if (success) {
                Toast.makeText(requireContext(), "Invoice cancelled successfully", Toast.LENGTH_SHORT).show();
                Navigation.findNavController(requireView()).popBackStack();
            }
        });

        viewModel.getLoadingLiveData().observe(getViewLifecycleOwner(), isLoading -> {
            progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        });

        viewModel.getErrorLiveData().observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String formatCurrency(Integer value) {
        return String.format(Locale.getDefault(), "₹%d.00", value != null ? value : 0);
    }

    private class DetailItemAdapter extends RecyclerView.Adapter<DetailItemAdapter.ViewHolder> {
        private List<BillingItem> items = new ArrayList<>();

        public void setItems(List<BillingItem> items) {
            this.items = items != null ? items : new ArrayList<>();
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_billing_product, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            BillingItem item = items.get(position);
            holder.textName.setText("Product ID: " + item.getProductId());
            holder.textDetails.setText(String.format(Locale.getDefault(), "Price: ₹%d x %d", item.getSellPrice(), item.getQty()));
            holder.textTotal.setText(formatCurrency(item.getLineTotal()));
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView textName, textDetails, textTotal;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                textName = itemView.findViewById(R.id.text_product_name);
                textDetails = itemView.findViewById(R.id.text_item_details);
                textTotal = itemView.findViewById(R.id.text_item_total);
            }
        }
    }
}
