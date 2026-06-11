package com.example.generalstoreapp.repository;

import android.content.Context;

import com.example.generalstoreapp.models.AddProductRequest;
import com.example.generalstoreapp.models.AddProductResponse;
import com.example.generalstoreapp.models.DeleteResponse;
import com.example.generalstoreapp.models.GetProductDataModel;
import com.example.generalstoreapp.services.ApiService;
import com.example.generalstoreapp.services.RetrofitClient;
import com.example.generalstoreapp.services.handlingservices.ApiCallback;
import com.example.generalstoreapp.services.handlingservices.ApiExecutor;

import java.util.ArrayList;

public class ProductRepository {

    private final ApiService api;

    public ProductRepository(Context context) {
        api = RetrofitClient.getApiService(context);
    }
//String q, int categoryId,
    public void getProducts(int isActive,
                            int limit, int offset,
                            ApiCallback<ArrayList<GetProductDataModel>> callback) {
        ApiExecutor.execute(
                api.getProductListDataFromServer( isActive, limit, offset),//q, categoryId
                callback
        );
    }

    public void getProductByBarcode(String barcode,
                                    ApiCallback<GetProductDataModel> callback) {
        ApiExecutor.execute(api.getProductByBarcodeDataFromServer(barcode), callback);
    }

    public void addProduct(AddProductRequest request,
                           ApiCallback<AddProductResponse> callback) {
        ApiExecutor.execute(api.saveProductDataFromServer(request), callback);
    }

    public void updateProduct(int productId, AddProductRequest request,
                              ApiCallback<AddProductResponse> callback) {
        ApiExecutor.execute(api.updateProductDataFromServer(productId, request), callback);
    }

    public void deleteProduct(int productId,
                              ApiCallback<DeleteResponse> callback) {
        ApiExecutor.execute(api.deleteProductDataFromServer(productId), callback);
    }
}
