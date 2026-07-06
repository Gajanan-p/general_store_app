package com.example.generalstoreapp.ui.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.generalstoreapp.R;
import com.example.generalstoreapp.ui.adapters.PurchaseAdapter;
import com.example.generalstoreapp.viewmodel.PurchaseViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class PurchaseFragment extends Fragment {

    private PurchaseViewModel viewModel;
    private PurchaseAdapter adapter;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_purchase, container, false);

        recyclerView = view.findViewById(R.id.recycler_purchases);
        progressBar = view.findViewById(R.id.progress_bar);

        adapter = new PurchaseAdapter();
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(PurchaseViewModel.class);
        viewModel.init(requireContext());

        observeViewModel();
        
        // Initial fetch: last 30 days
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String toDate = sdf.format(cal.getTime()) + "T23:59:59Z";
        cal.add(Calendar.DAY_OF_YEAR, -30);
        String fromDate = sdf.format(cal.getTime()) + "T00:00:00Z";
        
        viewModel.fetchPurchases(0, fromDate, toDate, null);
    }

    private void observeViewModel() {
        viewModel.getPurchasesLiveData().observe(getViewLifecycleOwner(), purchases -> {
            adapter.setData(purchases);
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
}
