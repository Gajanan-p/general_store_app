package com.example.generalstoreapp.ui.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.generalstoreapp.R;
import com.example.generalstoreapp.utils.PermissionUtils;
import com.example.generalstoreapp.databinding.FragmentHomeBinding;
import com.example.generalstoreapp.ui.adapters.BillingAdapter;
import com.example.generalstoreapp.viewmodel.HomeViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private FragmentHomeBinding binding;
    private HomeViewModel homeViewModel;
    private RecyclerView recyclerViewSalesList;
    private AppCompatButton buttonAddNewBilling;
    private BillingAdapter adapter;
    NavController navController;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        homeViewModel.init(requireContext());

        buttonAddNewBilling = binding.buttonAddSales;
        recyclerViewSalesList = binding.recyclerViewCustomerList;
        recyclerViewSalesList.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerViewSalesList.setHasFixedSize(true);

        buttonAddNewBilling.setVisibility(PermissionUtils.hasPermission(requireContext(), "BILLING_CREATE") ? View.VISIBLE : View.GONE);
        buttonAddNewBilling.setOnClickListener(this);
        getBillingData();
        observeViewModel();

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = NavHostFragment.findNavController(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.button_add_sales) {
            navController.navigate(R.id.billingFragment);
        }
    }

    public void getBillingData(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String datePart = sdf.format(new Date());
        String fromDate = datePart + "T00:00:00Z";
        String toDate = datePart + "T23:59:59Z";

        homeViewModel.fetchBillingList( null,
                fromDate,
                toDate,
                50,
                0);
    }
    private void observeViewModel() {
        homeViewModel.getBillingListLiveData().observe(getViewLifecycleOwner(), list -> {
            adapter = new BillingAdapter(billing -> {
                Bundle bundle = new Bundle();
                bundle.putInt("billing_id", billing.getId());
                Navigation.findNavController(requireView()).navigate(R.id.billingDetailFragment, bundle);
            });
            recyclerViewSalesList.setAdapter(adapter);
            adapter.setData(list);
        });

        homeViewModel.getTodaySaleAmount().observe(getViewLifecycleOwner(), amount -> 
                binding.txtTodaySaleAmount.setText(String.format(Locale.getDefault(), "₹ %.2f", amount)));
        
        homeViewModel.getTodayReceivedAmount().observe(getViewLifecycleOwner(), amount -> 
                binding.txtAmount.setText(String.format(Locale.getDefault(), "₹ %.2f", amount)));
        
        homeViewModel.getTodayPendingAmount().observe(getViewLifecycleOwner(), amount -> 
                binding.txtPendingDueAmount.setText(String.format(Locale.getDefault(), "₹ %.2f", amount)));

        homeViewModel.getLoadingLiveData().observe(getViewLifecycleOwner(), isLoading -> {
            //progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        });

        homeViewModel.getErrorLiveData().observe(getViewLifecycleOwner(), error -> {
            if (!TextUtils.isEmpty(error)) {
                Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
