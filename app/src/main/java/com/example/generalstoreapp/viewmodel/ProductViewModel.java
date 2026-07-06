package com.example.generalstoreapp.viewmodel;

import android.content.Context;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.generalstoreapp.models.AddProductRequest;
import com.example.generalstoreapp.models.GetCategoriesModel;
import com.example.generalstoreapp.models.GetProductDataModel;
import com.example.generalstoreapp.models.GetUnitsDataModel;
import com.example.generalstoreapp.models.ProductPriceHistoryModel;
import com.example.generalstoreapp.repository.CategoryRepository;
import com.example.generalstoreapp.repository.ProductRepository;
import com.example.generalstoreapp.repository.UnitsRepository;
import com.example.generalstoreapp.services.handlingservices.ApiResult;

import java.util.ArrayList;
import java.util.List;

public class ProductViewModel extends ViewModel {

    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;
    private UnitsRepository unitsRepository;

    private final MutableLiveData<List<GetProductDataModel>> productsLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<GetCategoriesModel>> categoriesLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<GetUnitsDataModel>> unitsLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<ProductPriceHistoryModel>> priceHistoryLiveData = new MutableLiveData<>();
    
    private final MutableLiveData<String> errorLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loadingLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> successLiveData = new MutableLiveData<>();

    private int currentOffset = 0;
    private final int LIMIT = 50;
    private boolean isLastPage = false;

    public void init(Context context) {
        if (productRepository == null) {
            productRepository = new ProductRepository(context);
            categoryRepository = new CategoryRepository(context);
            unitsRepository = new UnitsRepository(context);
        }
    }

    public LiveData<List<GetProductDataModel>> getProductsLiveData() {
        return productsLiveData;
    }

    public LiveData<List<GetCategoriesModel>> getCategoriesLiveData() {
        return categoriesLiveData;
    }

    public LiveData<List<GetUnitsDataModel>> getUnitsLiveData() {
        return unitsLiveData;
    }

    public LiveData<List<ProductPriceHistoryModel>> getPriceHistoryLiveData() {
        return priceHistoryLiveData;
    }

    public LiveData<String> getErrorLiveData() {
        return errorLiveData;
    }

    public LiveData<Boolean> getLoadingLiveData() {
        return loadingLiveData;
    }

    public LiveData<Boolean> getSuccessLiveData() {
        return successLiveData;
    }

    public void fetchProducts(String q, int categoryId, int isActive, boolean isRefresh) {
        if (isRefresh) {
            currentOffset = 0;
            isLastPage = false;
        } else if (isLastPage) {
            return;
        }

        loadingLiveData.setValue(true);
        // Mapping isActive int to Boolean (1 = true)
        Boolean isActiveBool = (isActive == 1);
        
        productRepository.getProducts(isActiveBool, false, LIMIT, currentOffset, result -> {
            loadingLiveData.setValue(false);
            if (result.status == ApiResult.Status.SUCCESS && result.data != null) {
                List<GetProductDataModel> items = result.data.getItems();
                List<GetProductDataModel> currentList = productsLiveData.getValue();
                if (isRefresh || currentList == null) {
                    productsLiveData.setValue(items);
                } else {
                    List<GetProductDataModel> newList = new ArrayList<>(currentList);
                    newList.addAll(items);
                    productsLiveData.setValue(newList);
                }
                
                if (items.size() < LIMIT) {
                    isLastPage = true;
                } else {
                    currentOffset += LIMIT;
                }
            } else {
                errorLiveData.setValue(result.message);
            }
        });
    }

    public void fetchCategories() {
        categoryRepository.getCategories(100, 0, result -> {
            if (result.status == ApiResult.Status.SUCCESS && result.data != null) {
                categoriesLiveData.setValue(result.data.getItems());
            }
        });
    }

    public void fetchUnits() {
        unitsRepository.getUnits(100, 0, result -> {
            if (result.status == ApiResult.Status.SUCCESS && result.data != null) {
                unitsLiveData.setValue(result.data.getItems());
            }
        });
    }

    public void addProduct(AddProductRequest request) {
        loadingLiveData.setValue(true);
        productRepository.addProduct(request, result -> {
            loadingLiveData.setValue(false);
            if (result.status == ApiResult.Status.SUCCESS) {
                successLiveData.setValue(true);
            } else {
                errorLiveData.setValue(result.message);
            }
        });
    }

    public void updateProduct(int productId, AddProductRequest request) {
        loadingLiveData.setValue(true);
        productRepository.updateProduct(productId, request, result -> {
            loadingLiveData.setValue(false);
            if (result.status == ApiResult.Status.SUCCESS) {
                successLiveData.setValue(true);
            } else {
                errorLiveData.setValue(result.message);
            }
        });
    }

    public void deleteProduct(int productId) {
        loadingLiveData.setValue(true);
        productRepository.deleteProduct(productId, result -> {
            loadingLiveData.setValue(false);
            if (result.status == ApiResult.Status.SUCCESS) {
                successLiveData.setValue(true);
            } else {
                errorLiveData.setValue(result.message);
            }
        });
    }

    public void fetchPriceHistory(int productId) {
        loadingLiveData.setValue(true);
        productRepository.getPriceHistory(productId, 50, 0, result -> {
            loadingLiveData.setValue(false);
            if (result.status == ApiResult.Status.SUCCESS && result.data != null) {
                priceHistoryLiveData.setValue(result.data.getItems());
            } else {
                errorLiveData.setValue(result.message);
            }
        });
    }
}