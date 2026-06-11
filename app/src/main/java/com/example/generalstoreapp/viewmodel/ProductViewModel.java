package com.example.generalstoreapp.viewmodel;

import android.content.Context;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.generalstoreapp.models.AddProductRequest;
import com.example.generalstoreapp.models.GetCategoriesModel;
import com.example.generalstoreapp.models.GetProductDataModel;
import com.example.generalstoreapp.models.GetUnitsDataModel;
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
    private final MutableLiveData<ArrayList<GetCategoriesModel>> categoriesLiveData = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<GetUnitsDataModel>> unitsLiveData = new MutableLiveData<>();
    
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

    public LiveData<ArrayList<GetCategoriesModel>> getCategoriesLiveData() {
        return categoriesLiveData;
    }

    public LiveData<ArrayList<GetUnitsDataModel>> getUnitsLiveData() {
        return unitsLiveData;
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
        productRepository.getProducts(isActive, LIMIT, currentOffset, result -> {
            loadingLiveData.setValue(false);
            if (result.status == ApiResult.Status.SUCCESS) {
                List<GetProductDataModel> currentList = productsLiveData.getValue();
                if (isRefresh || currentList == null) {
                    productsLiveData.setValue(result.data);
                } else {
                    currentList.addAll(result.data);
                    productsLiveData.setValue(currentList);
                }
                
                if (result.data.size() < LIMIT) {
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
        categoryRepository.getCategories(result -> {
            if (result.status == ApiResult.Status.SUCCESS) {
                categoriesLiveData.setValue(result.data);
            }
        });
    }

    public void fetchUnits() {
        unitsRepository.getUnits(result -> {
            if (result.status == ApiResult.Status.SUCCESS) {
                unitsLiveData.setValue(result.data);
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
}
