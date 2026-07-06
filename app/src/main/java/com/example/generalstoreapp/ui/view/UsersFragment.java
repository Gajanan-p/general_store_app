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
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.generalstoreapp.R;
import com.example.generalstoreapp.models.Users;
import com.example.generalstoreapp.ui.adapters.UsersAdapter;
import com.example.generalstoreapp.viewmodel.UsersViewModel;

public class UsersFragment extends Fragment implements UsersAdapter.OnUserActionListener {

    private UsersViewModel viewModel;
    private UsersAdapter adapter;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TextView textEmpty;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_users, container, false);

        recyclerView = view.findViewById(R.id.recycler_users);
        progressBar = view.findViewById(R.id.progress_bar);
        textEmpty = view.findViewById(R.id.text_empty);

        adapter = new UsersAdapter(this);
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(UsersViewModel.class);
        viewModel.init(requireContext());

        observeViewModel();
        viewModel.fetchUsers(true);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (!recyclerView.canScrollVertically(1)) {
                    viewModel.fetchUsers(false);
                }
            }
        });
    }

    private void observeViewModel() {
        viewModel.getUsersLiveData().observe(getViewLifecycleOwner(), users -> {
            adapter.setUsers(users);
            textEmpty.setVisibility(users == null || users.isEmpty() ? View.VISIBLE : View.GONE);
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

    @Override
    public void onUserClick(Users user) {
        Toast.makeText(requireContext(), "User: " + user.getName(), Toast.LENGTH_SHORT).show();
    }
}
