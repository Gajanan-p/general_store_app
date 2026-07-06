package com.example.generalstoreapp.repository;

import android.content.Context;

import com.example.generalstoreapp.models.AddProductRequest;
import com.example.generalstoreapp.models.DeleteResponse;
import com.example.generalstoreapp.models.GetProductDataModel;
import com.example.generalstoreapp.models.ProductListResponse;
import com.example.generalstoreapp.models.PriceHistoryResponse;
import com.example.generalstoreapp.services.ApiService;
import com.example.generalstoreapp.services.RetrofitClient;
import com.example.generalstoreapp.services.handlingservices.ApiCallback;
import com.example.generalstoreapp.services.handlingservices.ApiExecutor;

public class ProductRepository {

    private final ApiService api;

    public ProductRepository(Context context) {
        api = RetrofitClient.getApiService(context);
    }

    public void getProducts(Boolean isActive, Boolean lowStock,
                            int limit, int offset,
                            ApiCallback<ProductListResponse> callback) {
        ApiExecutor.execute(
                api.getProductListDataFromServer(isActive, lowStock, limit, offset),
                callback
        );
    }

    public void getLowStockProducts(int limit, int offset, ApiCallback<ProductListResponse> callback) {
        ApiExecutor.execute(api.getLowStockProducts(limit, offset), callback);
    }

    public void getProductByBarcode(String barcode,
                                    ApiCallback<GetProductDataModel> callback) {
        ApiExecutor.execute(api.getProductByBarcodeDataFromServer(barcode), callback);
    }

    public void addProduct(AddProductRequest request,
                           ApiCallback<GetProductDataModel> callback) {
        ApiExecutor.execute(api.saveProductDataFromServer(request), callback);
    }

    public void updateProduct(int productId, AddProductRequest request,
                              ApiCallback<GetProductDataModel> callback) {
        ApiExecutor.execute(api.updateProductDataFromServer(productId, request), callback);
    }

    public void deleteProduct(int productId,
                              ApiCallback<DeleteResponse> callback) {
        ApiExecutor.execute(api.deleteProductDataFromServer(productId), callback);
    }

    public void getPriceHistory(int productId, int limit, int offset, ApiCallback<PriceHistoryResponse> callback) {
        ApiExecutor.execute(api.getProductPriceHistory(productId, limit, offset), callback);
    }

    public void updateStock(int productId, Double newStock, ApiCallback<GetProductDataModel> callback) {
        AddProductRequest request = new AddProductRequest();
        request.setCurrentStock(newStock);
        ApiExecutor.execute(api.updateProductStock(productId, request), callback);
    }
}