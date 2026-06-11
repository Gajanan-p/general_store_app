package com.example.generalstoreapp.ui.view;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.generalstoreapp.R;
import com.example.generalstoreapp.viewmodel.SupplierViewModel;

public class SupplierFragment extends Fragment {

    private SupplierViewModel mViewModel;

    public static SupplierFragment newInstance() {
        return new SupplierFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_supplier, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SupplierViewModel.class);
        // TODO: Use the ViewModel
    }

}