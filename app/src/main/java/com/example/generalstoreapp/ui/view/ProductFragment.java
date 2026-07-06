package com.example.generalstoreapp.ui.view;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.generalstoreapp.R;
import com.example.generalstoreapp.models.AddProductRequest;
import com.example.generalstoreapp.models.GetCategoriesModel;
import com.example.generalstoreapp.models.GetProductDataModel;
import com.example.generalstoreapp.models.GetUnitsDataModel;
import com.example.generalstoreapp.ui.adapters.ProductAdapter;
import com.example.generalstoreapp.viewmodel.ProductViewModel;
import com.example.generalstoreapp.utils.PermissionUtils;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import com.google.android.material.switchmaterial.SwitchMaterial;

public class ProductFragment extends Fragment implements ProductAdapter.OnProductActionListener {

    private ProductViewModel viewModel;
    private ProductAdapter adapter;
    private RecyclerView recyclerView;
    private MaterialButton fabAdd;
    private ProgressBar progressBar;
    private TextView textEmpty;
    private TextInputEditText editSearch;
    private ChipGroup chipGroupCategories;

    private List<GetCategoriesModel> categoriesList = new ArrayList<>();
    private List<GetUnitsDataModel> unitsList = new ArrayList<>();
    
    private String currentSearch = "";
    private int currentCategoryId = 0;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, container, false);

        recyclerView = view.findViewById(R.id.recycler_products);
        fabAdd = view.findViewById(R.id.fab_add_product);
        progressBar = view.findViewById(R.id.progress_bar);
        textEmpty = view.findViewById(R.id.text_empty);
        editSearch = view.findViewById(R.id.edit_search);
        chipGroupCategories = view.findViewById(R.id.chip_group_categories);

        adapter = new ProductAdapter(this);
        recyclerView.setAdapter(adapter);

        fabAdd.setVisibility(PermissionUtils.hasPermission(requireContext(), "PRODUCT_CREATE") ? View.VISIBLE : View.GONE);
        fabAdd.setOnClickListener(v -> showProductDialog(null));
        
        view.findViewById(R.id.btn_barcode_scan).setOnClickListener(v -> showBarcodeSearchDialog());
        
        setupSearchAndFilter();

        return view;
    }

    private void showBarcodeSearchDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle(R.string.search_by_barcode_title);
        
        View view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_barcode_search, null);
        builder.setView(view);
        
        TextInputEditText editBarcode = view.findViewById(R.id.edit_barcode_search);
        
        builder.setPositiveButton(R.string.submit, (dialog, which) -> {
            String barcode = editBarcode.getText().toString();
            if (!barcode.isEmpty()) {
                currentSearch = barcode;
                editSearch.setText(barcode);
                refreshProducts();
            }
        });
        builder.setNegativeButton(R.string.cancel, null);
        builder.show();
    }

    private void setupSearchAndFilter() {
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                currentSearch = s.toString();
                refreshProducts();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void refreshProducts() {
        viewModel.fetchProducts(currentSearch, currentCategoryId, 1, true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        viewModel.init(requireContext());

        observeViewModel();
        
        refreshProducts();
        viewModel.fetchCategories();
        viewModel.fetchUnits();
        
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (!recyclerView.canScrollVertically(1)) { // Check if reached bottom
                    viewModel.fetchProducts(currentSearch, currentCategoryId, 1, false);
                }
            }
        });
    }

    private void observeViewModel() {
        viewModel.getProductsLiveData().observe(getViewLifecycleOwner(), products -> {
            adapter.setProducts(products);
            textEmpty.setVisibility(products.isEmpty() ? View.VISIBLE : View.GONE);
        });

        viewModel.getLoadingLiveData().observe(getViewLifecycleOwner(), isLoading -> {
            progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        });

        viewModel.getErrorLiveData().observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show();
            }
        });

        viewModel.getCategoriesLiveData().observe(getViewLifecycleOwner(), categories -> {
            this.categoriesList = categories;
            setupCategoryFilter(categories);
        });

        viewModel.getUnitsLiveData().observe(getViewLifecycleOwner(), units -> {
            this.unitsList = units;
        });

        viewModel.getSuccessLiveData().observe(getViewLifecycleOwner(), success -> {
            if (success) {
                Toast.makeText(requireContext(), getString(R.string.success), Toast.LENGTH_SHORT).show();
                refreshProducts();
            }
        });
    }

    private void setupCategoryFilter(List<GetCategoriesModel> categories) {
        chipGroupCategories.removeAllViews();
        
        // Add "All" chip
        Chip allChip = new Chip(requireContext());
        allChip.setText(R.string.all_categories);
        allChip.setCheckable(true);
        allChip.setChecked(currentCategoryId == 0);
        allChip.setId(View.generateViewId());
        allChip.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                currentCategoryId = 0;
                refreshProducts();
            }
        });
        chipGroupCategories.addView(allChip);

        for (GetCategoriesModel cat : categories) {
            Chip chip = new Chip(requireContext());
            chip.setText(cat.getName());
            chip.setCheckable(true);
            chip.setChecked(currentCategoryId == cat.getId());
            chip.setId(View.generateViewId());
            chip.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    currentCategoryId = cat.getId();
                    refreshProducts();
                }
            });
            chipGroupCategories.addView(chip);
        }
    }

    private void showProductDialog(@Nullable GetProductDataModel existingProduct) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_product, null);
        builder.setView(dialogView);

        TextView textTitle = dialogView.findViewById(R.id.text_title);
        TextInputEditText editName = dialogView.findViewById(R.id.edit_product_name);
        TextInputEditText editSku = dialogView.findViewById(R.id.edit_product_sku);
        TextInputEditText editBarcode = dialogView.findViewById(R.id.edit_product_barcode);
        AutoCompleteTextView spinnerCategory = dialogView.findViewById(R.id.spinner_category);
        AutoCompleteTextView spinnerUnit = dialogView.findViewById(R.id.spinner_unit);
        TextInputEditText editCost = dialogView.findViewById(R.id.edit_cost_price);
        TextInputEditText editSell = dialogView.findViewById(R.id.edit_sell_price);
        TextInputEditText editMrp = dialogView.findViewById(R.id.edit_mrp);
        TextInputEditText editGst = dialogView.findViewById(R.id.edit_gst);
        TextInputEditText editOpeningStock = dialogView.findViewById(R.id.edit_opening_stock);
        TextInputEditText editLowStock = dialogView.findViewById(R.id.edit_low_stock);
        SwitchMaterial switchActive = dialogView.findViewById(R.id.switch_active);

        if (existingProduct != null) {
            textTitle.setText(R.string.edit_product_title);
            editName.setText(existingProduct.getName());
            editSku.setText(existingProduct.getSku());
            editBarcode.setText(existingProduct.getSku());
            editCost.setText(String.valueOf(existingProduct.getCostPrice()));
            editSell.setText(String.valueOf(existingProduct.getSellPrice()));
            if (editMrp != null) editMrp.setText(String.valueOf(existingProduct.getSellPrice())); // Example mapping
            editOpeningStock.setText(existingProduct.getCurrentStock());
            editLowStock.setText(existingProduct.getLowStockAlert());
            switchActive.setChecked(Boolean.TRUE.equals(existingProduct.getIsActive()));
            
            // Set spinners if possible
            for (GetCategoriesModel cat : categoriesList) {
                if (cat.getId().equals(existingProduct.getCategoryId())) {
                    spinnerCategory.setText(cat.getName(), false);
                    break;
                }
            }
            for (GetUnitsDataModel unit : unitsList) {
                if (unit.getId().equals(existingProduct.getUnitId())) {
                    spinnerUnit.setText(unit.getName(), false);
                    break;
                }
            }
        }

        // Setup Categories Spinner for Dialog
        List<String> categoryNames = new ArrayList<>();
        for (GetCategoriesModel cat : categoriesList) categoryNames.add(cat.getName());
        ArrayAdapter<String> catAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, categoryNames);
        spinnerCategory.setAdapter(catAdapter);

        // Setup Units Spinner for Dialog
        List<String> unitNames = new ArrayList<>();
        for (GetUnitsDataModel unit : unitsList) unitNames.add(unit.getName());
        ArrayAdapter<String> unitAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, unitNames);
        spinnerUnit.setAdapter(unitAdapter);

        AlertDialog dialog = builder.create();

        dialogView.findViewById(R.id.button_submit).setOnClickListener(v -> {
            AddProductRequest request = new AddProductRequest();
            request.setName(editName.getText().toString());
            request.setSku(editSku.getText().toString());
            request.setSku(editBarcode.getText().toString());
            
            int catIndex = categoryNames.indexOf(spinnerCategory.getText().toString());
            if (catIndex != -1) request.setCategoryId(categoriesList.get(catIndex).getId());
            
            int unitIndex = unitNames.indexOf(spinnerUnit.getText().toString());
            if (unitIndex != -1) request.setUnitId(unitsList.get(unitIndex).getId());

            request.setPurchasePrice(parseDouble(editCost.getText().toString()));
            request.setSellingPrice(parseDouble(editSell.getText().toString()));
            request.setCurrentStock(parseDouble(editOpeningStock.getText().toString()));
            request.setLowStockAlert(parseDouble(editLowStock.getText().toString()));
            request.setIsActive(switchActive.isChecked());
            request.setPriceChangeReason("Updated from App");

            if (existingProduct == null) {
                viewModel.addProduct(request);
            } else {
                viewModel.updateProduct(existingProduct.getId(), request);
            }
            dialog.dismiss();
        });

        dialog.show();
    }

    private double parseDouble(String value) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    private int parseInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    @Override
    public void onEdit(GetProductDataModel product) {
        if (PermissionUtils.hasPermission(requireContext(), "PRODUCT_UPDATE")) {
            showProductDialog(product);
        } else {
            Toast.makeText(requireContext(), "You don't have permission to update products", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDelete(GetProductDataModel product) {
        if (PermissionUtils.hasPermission(requireContext(), "PRODUCT_DELETE")) {
            new AlertDialog.Builder(requireContext())
                    .setTitle(R.string.delete_product_title)
                    .setMessage(getString(R.string.delete_confirm_msg, product.getName()))
                    .setPositiveButton(R.string.submit, (dialog, which) -> viewModel.deleteProduct(product.getId()))
                    .setNegativeButton(R.string.cancel, null)
                    .show();
        } else {
            Toast.makeText(requireContext(), "You don't have permission to delete products", Toast.LENGTH_SHORT).show();
        }
    }
}
