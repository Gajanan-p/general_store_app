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
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.generalstoreapp.R;
import com.example.generalstoreapp.models.GetBillingDataModel;
import com.example.generalstoreapp.ui.adapters.BillingAdapter;
import com.example.generalstoreapp.viewmodel.SalesViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class SalesFragment extends Fragment implements BillingAdapter.OnBillingActionListener {

    private SalesViewModel viewModel;
    private BillingAdapter adapter;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private FloatingActionButton fabAdd;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sales, container, false);

        recyclerView = view.findViewById(R.id.recycler_sales);
        progressBar = view.findViewById(R.id.progress_bar);
        fabAdd = view.findViewById(R.id.fab_add_billing);

        adapter = new BillingAdapter(this);
        recyclerView.setAdapter(adapter);

        fabAdd.setOnClickListener(v -> {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.nav_host_fragment_content_main, BillingFragment.newInstance())
                    .addToBackStack(null)
                    .commit();
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(SalesViewModel.class);
        viewModel.init(requireContext());

        observeViewModel();
        viewModel.fetchBillingList(3, "", "");
    }

    private void observeViewModel() {
        viewModel.getBillingListLiveData().observe(getViewLifecycleOwner(), billingList -> {
            adapter.setData(billingList);
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
