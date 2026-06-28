package com.example.generalstoreapp.ui.view;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.generalstoreapp.R;
import com.example.generalstoreapp.models.CartItem;
import com.example.generalstoreapp.models.GetCategoriesModel;
import com.example.generalstoreapp.models.GetProductDataModel;
import com.example.generalstoreapp.ui.adapters.ProductAdapter;
import com.example.generalstoreapp.viewmodel.BillingViewModel;
import com.example.generalstoreapp.viewmodel.ProductViewModel;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class ItemsFragment extends Fragment implements ProductAdapter.OnProductActionListener {

    private BillingViewModel billingViewModel;
    private ProductViewModel productViewModel;
    private ProductAdapter adapter;
    private AddedItemAdapter addedItemAdapter;
    private RecyclerView recyclerView, recyclerAddedItems;
    private ProgressBar progressBar;
    private TextInputEditText editSearch;
    private ChipGroup chipGroupCategories;
    private LinearLayout layoutResults, layoutItemDetails, layoutAddedItems;
    private TextInputEditText editQuantity, editRate;
    private AutoCompleteTextView spinnerUnit;
    private MaterialButton btnAddMore, btnSubmit;
    private List<GetProductDataModel> selectedProducts = new ArrayList<>();
    private GetProductDataModel currentSelectedProduct;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_items, container, false);

        recyclerView = view.findViewById(R.id.recycler_products);
        recyclerAddedItems = view.findViewById(R.id.recycler_added_items);
        progressBar = view.findViewById(R.id.progress_bar);
        editSearch = view.findViewById(R.id.edit_search);
        btnSubmit = view.findViewById(R.id.btn_submit_items);
        
        layoutResults = view.findViewById(R.id.layout_results);
        layoutResults.setVisibility(View.VISIBLE);
        layoutItemDetails = view.findViewById(R.id.layout_item_details);
        layoutAddedItems = view.findViewById(R.id.layout_added_items);
        editQuantity = view.findViewById(R.id.edit_quantity);
        editRate = view.findViewById(R.id.edit_rate);
        spinnerUnit = view.findViewById(R.id.spinner_unit);
        btnAddMore = view.findViewById(R.id.btn_add_more);

        adapter = new ProductAdapter(this);
        recyclerView.setLayoutManager(new androidx.recyclerview.widget.LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);

        addedItemAdapter = new AddedItemAdapter();
        recyclerAddedItems.setLayoutManager(new androidx.recyclerview.widget.LinearLayoutManager(requireContext()));
        recyclerAddedItems.setAdapter(addedItemAdapter);

        setupSearchAndFilter();
        setupFormListeners();

        btnSubmit.setOnClickListener(v -> {
            saveCurrentItem();
            Navigation.findNavController(v).popBackStack();
        });

        btnAddMore.setOnClickListener(v -> {
            saveCurrentItem();
            resetForm();
        });

        return view;
    }

    private void setupFormListeners() {
        // Setup Unit spinner if needed
        String[] units = {"Unit", "Kg", "Gm", "Ltr", "Pcs"};
        ArrayAdapter<String> unitAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, units);
        spinnerUnit.setAdapter(unitAdapter);
    }

    private void saveCurrentItem() {
        if (currentSelectedProduct != null) {
            int qty = 1;
            try {
                qty = Integer.parseInt(editQuantity.getText().toString());
            } catch (Exception ignored) {}
            
            double rate = currentSelectedProduct.getSellPrice().doubleValue();
            try {
                rate = Double.parseDouble(editRate.getText().toString());
            } catch (Exception ignored) {}

            // Temporarily update product price if changed by user for this bill
            // Note: In a real app, you'd pass the override price to the cart item
            billingViewModel.addProductToCart(currentSelectedProduct, qty, rate);
            currentSelectedProduct = null;
        }
    }

    private void resetForm() {
        currentSelectedProduct = null;
        editSearch.setText("");
        editQuantity.setText("1");
        editRate.setText("");
        layoutItemDetails.setVisibility(View.GONE);
        layoutResults.setVisibility(View.VISIBLE);
        editSearch.requestFocus();
    }

    private void setupSearchAndFilter() {
        editSearch.setOnClickListener(v -> {
            layoutResults.setVisibility(View.VISIBLE);
            adapter.filter(editSearch.getText().toString());
        });

        editSearch.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                layoutResults.setVisibility(View.VISIBLE);
                adapter.filter(editSearch.getText().toString());
            }
        });

        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String query = s.toString();
                if (currentSelectedProduct != null && !query.equals(currentSelectedProduct.getName())) {
                    currentSelectedProduct = null;
                    layoutItemDetails.setVisibility(View.GONE);
                }

                if (currentSelectedProduct == null) {
                    layoutResults.setVisibility(View.VISIBLE);
                    adapter.filter(query);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void refreshProducts() {
        productViewModel.fetchProducts("", 0, 1, true);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        billingViewModel = new ViewModelProvider(requireActivity()).get(BillingViewModel.class);
        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        productViewModel.init(requireContext());

        observeViewModel();
        refreshProducts();
        productViewModel.fetchCategories();
    }

    private void observeViewModel() {
        productViewModel.getProductsLiveData().observe(getViewLifecycleOwner(), products -> {
            adapter.setProducts(products);
        });

        productViewModel.getLoadingLiveData().observe(getViewLifecycleOwner(), isLoading -> {
            progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        });

        billingViewModel.getCartItems().observe(getViewLifecycleOwner(), items -> {
            addedItemAdapter.setItems(items);
            updateSubmitButton(items != null ? items.size() : 0);
            layoutAddedItems.setVisibility(items != null && !items.isEmpty() ? View.VISIBLE : View.GONE);

        });
    }

    @Override
    public void onEdit(GetProductDataModel product) {
        currentSelectedProduct = product;
        editSearch.setText(product.getName());
        editRate.setText(String.valueOf(product.getSellPrice()));
        layoutResults.setVisibility(View.GONE);
        layoutItemDetails.setVisibility(View.VISIBLE);
        editQuantity.requestFocus();
    }

    private void updateSubmitButton(int count) {
        if (btnSubmit != null) {
            btnSubmit.setText("Submit (" + count + " items)");
        }
    }

    @Override
    public void onDelete(GetProductDataModel product) {
        // Not used in selection mode
    }

    private class AddedItemAdapter extends RecyclerView.Adapter<AddedItemAdapter.ViewHolder> {
        private List<CartItem> items = new ArrayList<>();

        public void setItems(List<CartItem> items) {
            this.items = items != null ? items : new ArrayList<>();
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_added_product, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            CartItem cartItem = items.get(position);
            GetProductDataModel p = cartItem.getProduct();
//            String info = String.format(java.util.Locale.getDefault(), "%s, Price: ₹%.2f, Stock: %d",
//                    p.getName(), cartItem.getPrice(), p.getStockQty());
            holder.textInfo.setText(p.getName());
            holder.textPrice.setText(String.valueOf("Price "+":" + cartItem.getPrice()));
            holder.textStock.setText(String.valueOf("In Stock Qty "+":" + p.getStockQty()));
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView textInfo;
            TextView textPrice;
            TextView textStock;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                textInfo = itemView.findViewById(R.id.text_added_item_info_name);
                textPrice = itemView.findViewById(R.id.text_added_item_info_price);
                textStock = itemView.findViewById(R.id.text_added_item_info_stock);
            }
        }
    }
}
