package com.example.generalstoreapp.ui.view;

import android.app.AlertDialog;
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
import com.example.generalstoreapp.models.GetCategoriesModel;
import com.example.generalstoreapp.ui.adapters.CategoryAdapter;
import com.example.generalstoreapp.viewmodel.CategoriesViewModel;
import com.example.generalstoreapp.utils.PermissionUtils;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

public class CategoriesFragment extends Fragment implements CategoryAdapter.OnCategoryActionListener {

    private CategoriesViewModel viewModel;
    private CategoryAdapter adapter;
    private RecyclerView recyclerView;
    private MaterialButton fabAdd;
    private ProgressBar progressBar;
    private TextView textEmpty;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categories, container, false);

        recyclerView = view.findViewById(R.id.recycler_categories);
        fabAdd = view.findViewById(R.id.fab_add_category);
        progressBar = view.findViewById(R.id.progress_bar);
        textEmpty = view.findViewById(R.id.text_empty);

        adapter = new CategoryAdapter(this);
        recyclerView.setAdapter(adapter);

        fabAdd.setVisibility(PermissionUtils.hasPermission(requireContext(), "CATEGORY_CREATE") ? View.VISIBLE : View.GONE);
        fabAdd.setOnClickListener(v -> showCategoryDialog(null));

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(CategoriesViewModel.class);
        viewModel.init(requireContext());

        observeViewModel();
        viewModel.fetchCategories(true);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (!recyclerView.canScrollVertically(1)) {
                    viewModel.fetchCategories(false);
                }
            }
        });
    }

    private void observeViewModel() {
        viewModel.getCategoriesLiveData().observe(getViewLifecycleOwner(), categories -> {
            adapter.setCategories(categories);
            textEmpty.setVisibility(categories.isEmpty() ? View.VISIBLE : View.GONE);
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
                Toast.makeText(requireContext(), getString(R.string.success), Toast.LENGTH_SHORT).show();
                viewModel.fetchCategories();
            }
        });
    }

    private void showCategoryDialog(@Nullable GetCategoriesModel existingCategory) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_category, null);
        builder.setView(dialogView);

        TextView textTitle = dialogView.findViewById(R.id.text_title);
        TextInputEditText editName = dialogView.findViewById(R.id.edit_category_name);
        TextInputEditText editDesc = dialogView.findViewById(R.id.edit_category_description);

        if (existingCategory != null) {
            textTitle.setText(R.string.edit_category_title);
            editName.setText(existingCategory.getName());
            editDesc.setText(existingCategory.getDescription() != null ? existingCategory.getDescription() : "");
        }

        AlertDialog dialog = builder.create();

        dialogView.findViewById(R.id.button_submit).setOnClickListener(v -> {
            String name = editName.getText().toString();
            String desc = editDesc.getText().toString();
            if (existingCategory == null) {
                viewModel.createNewCategory(name, desc);
            } else {
                viewModel.updateCategory(existingCategory.getId(), name, desc);
            }
            dialog.dismiss();
        });

        dialog.show();
    }

    @Override
    public void onEdit(GetCategoriesModel category) {
        if (PermissionUtils.hasPermission(requireContext(), "CATEGORY_UPDATE")) {
            showCategoryDialog(category);
        } else {
            Toast.makeText(requireContext(), "You don't have permission to update categories", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDelete(GetCategoriesModel category) {
        if (PermissionUtils.hasPermission(requireContext(), "CATEGORY_DELETE")) {
            new AlertDialog.Builder(requireContext())
                    .setTitle(R.string.delete_category_title)
                    .setMessage(getString(R.string.delete_confirm_msg, category.getName()))
                    .setPositiveButton(R.string.submit, (dialog, which) -> viewModel.deleteCategory(category.getId()))
                    .setNegativeButton(R.string.cancel, null)
                    .show();
        } else {
            Toast.makeText(requireContext(), "You don't have permission to delete categories", Toast.LENGTH_SHORT).show();
        }
    }
}
