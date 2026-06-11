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
import com.example.generalstoreapp.viewmodel.PurchaseViewModel;

public class PurchaseFragment extends Fragment {

    private PurchaseViewModel mViewModel;

    public static PurchaseFragment newInstance() {
        return new PurchaseFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_purchase, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(PurchaseViewModel.class);
        // TODO: Use the ViewModel
    }

}