package com.example.generalstoreapp.services;





import com.example.generalstoreapp.models.AddCustomerRequest;
import com.example.generalstoreapp.models.AddCustomerResponse;
import com.example.generalstoreapp.models.AddProductRequest;
import com.example.generalstoreapp.models.AddProductResponse;
import com.example.generalstoreapp.models.AddPurchasesRequest;
import com.example.generalstoreapp.models.AddPurchasesResponse;
import com.example.generalstoreapp.models.AddSuppliersRequest;
import com.example.generalstoreapp.models.AddSuppliersResponse;
import com.example.generalstoreapp.models.AddUsersByRoleRequest;
import com.example.generalstoreapp.models.AddUsersByRoleResponse;
import com.example.generalstoreapp.models.BillingRequest;
import com.example.generalstoreapp.models.BillingResponse;
import com.example.generalstoreapp.models.CategoriesRequest;
import com.example.generalstoreapp.models.CategoriesResponse;
import com.example.generalstoreapp.models.DeleteResponse;
import com.example.generalstoreapp.models.DeleteUnitsResponse;
import com.example.generalstoreapp.models.GetBillingDataModel;
import com.example.generalstoreapp.models.GetCategoriesModel;
import com.example.generalstoreapp.models.GetCustomerDataModel;
import com.example.generalstoreapp.models.GetProductDataModel;
import com.example.generalstoreapp.models.GetPurchasesDataModel;
import com.example.generalstoreapp.models.GetRoleModel;
import com.example.generalstoreapp.models.GetSuppliersDataModel;
import com.example.generalstoreapp.models.GetUnitsDataModel;
import com.example.generalstoreapp.models.GetUsersByPermissionsModel;
import com.example.generalstoreapp.models.GetUsersByRoleModel;
import com.example.generalstoreapp.models.GetUsersModel;
import com.example.generalstoreapp.models.LoginModel;
import com.example.generalstoreapp.models.LoginRequestModel;
import com.example.generalstoreapp.models.PermissionsModel;
import com.example.generalstoreapp.models.RefreshRequest;
import com.example.generalstoreapp.models.RefreshResponse;
import com.example.generalstoreapp.models.RegistrationRequest;
import com.example.generalstoreapp.models.RegistrationResponse;
import com.example.generalstoreapp.models.RoleRequest;
import com.example.generalstoreapp.models.RoleResponse;
import com.example.generalstoreapp.models.UnitsRequest;
import com.example.generalstoreapp.models.UnitsResponse;
import com.example.generalstoreapp.models.UsersModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

//    Login service
    @Headers("Content-Type: application/json")
    @POST("auth/login")
    Call<LoginModel> fetchLoginDataFromServer(@Body LoginRequestModel requestModel);

//   Handled Refresh token
    @Headers("Content-Type: application/json")
    @POST("auth/refresh")
    Call<RefreshResponse> refreshToken(@Body RefreshRequest request);

//    Registration service
    @Headers("Content-Type: application/json")
    @POST("auth/register")
    Call<RegistrationResponse> callRegistrationDataFromServer(@Body RegistrationRequest request);


//    Role services
    @Headers("Content-Type: application/json")
    @GET("roles")
    Call<ArrayList<GetRoleModel>> callRoleDataFromServer();

    @Headers("Content-Type: application/json")
    @POST("roles")
    Call<RoleResponse> callAddRoleDataFromServer(@Body RoleRequest request);

    @Headers("Content-Type: application/json")
    @POST("roles")
    Call<RoleResponse> callUpdateRoleDataFromServer(@Body RoleRequest request);

    @Headers("Content-Type: application/json")
    @POST("roles")
    Call<RoleResponse> callDeleteRoleDataFromServer(@Body RoleRequest request);


//   ME Users services
    @Headers("Content-Type: application/json")
    @GET("me")
    Call<UsersModel> callUsersDataFromServer();


//   Users services
    @Headers("Content-Type: application/json")
    @GET("users")
    Call<ArrayList<GetUsersModel>> getUsersDataFromServer();

    @Headers("Content-Type: application/json")
    @GET("users/{user_id}/roles")
    Call<GetUsersByRoleModel> getUsersByRoleDataFromServer(@Path("user_id") int userId);

    @Headers("Content-Type: application/json")
    @GET("users/{user_id}/permissions")
    Call<GetUsersByPermissionsModel> getUsersByPermissionDataFromServer(@Path("user_id") int userId);

    @Headers("Content-Type: application/json")
    @POST("users/{user_id}/roles")
    Call<AddUsersByRoleResponse> addUsersByRoleDataFromServer(@Path("user_id") int userId,
                                                              @Body AddUsersByRoleRequest request);
    @Headers("Content-Type: application/json")
    @PUT("users/{user_id}")
    Call<AddUsersByRoleResponse> updateUsersDataFromServer(@Path("user_id") int userId,
                                                           @Body AddUsersByRoleRequest request);
    @Headers("Content-Type: application/json")
    @DELETE("users/{user_id}")
    Call<DeleteResponse> deleteUsersDataFromServer(@Path("user_id") int userId);

//   permissions

    @Headers("Content-Type: application/json")
    @GET("permissions")
    Call<PermissionsModel> getPermissionsModelDataFromServer();



//    Categories
    @Headers("Content-Type: application/json")
    @GET("categories")
    Call<ArrayList<GetCategoriesModel>> getCategoryDataFromServer();

    @Headers("Content-Type: application/json")
    @GET("categories/{category_id}")
    Call<GetCategoriesModel> getCategoryByIdDataFromServer(@Path("category_id") int category_id);

    @Headers("Content-Type: application/json")
    @POST("categories")
    Call<CategoriesResponse> saveCategoryDataFromServer(@Body CategoriesRequest request);

    @Headers("Content-Type: application/json")
    @PUT("categories/{category_id}")
    Call<CategoriesResponse> updateCategoryDataFromServer(@Path("category_id") int category_id,
                                                       @Body CategoriesRequest request);
    @Headers("Content-Type: application/json")
    @DELETE("categories/{category_id}")
    Call<DeleteResponse> deleteCategoryDataFromServer(@Path("category_id") int category_id);



//    Units
    @Headers("Content-Type: application/json")
    @GET("units")
    Call<ArrayList<GetUnitsDataModel>> getUnitsDataFromServer();

    @Headers("Content-Type: application/json")
    @GET("units/{unit_id}")
    Call<GetUnitsDataModel> getUnitsByIdDataFromServer(@Path("unit_id") int unit_id);

    @Headers("Content-Type: application/json")
    @POST("units")
    Call<UnitsResponse> saveUnitsDataFromServer(@Body UnitsRequest request);

    @Headers("Content-Type: application/json")
    @PUT("units/{unit_id}")
    Call<UnitsResponse> updateUnitsDataFromServer(@Path("unit_id") int unit_id,
                                                  @Body UnitsRequest request);

    @Headers("Content-Type: application/json")
    @DELETE("units/{unit_id}")
    Call<DeleteUnitsResponse> deleteUnitsDataFromServer(@Path("unit_id") int unit_id);



//    Products
    @Headers("Content-Type: application/json")
    @GET("products")
    Call<ArrayList<GetProductDataModel>> getProductListDataFromServer(@Query("is_active") int isActive,
                                                                      @Query("limit") int limit,
                                                                      @Query("offset") int offset);//@Query("q") String q,@Query("category_id") int categoryId,

    @Headers("Content-Type: application/json")
    @GET("products/by-barcode/{barcode}")
    Call<GetProductDataModel> getProductByBarcodeDataFromServer(@Path("barcode") String barcode);

    @Headers("Content-Type: application/json")
    @GET("products/{product_id}")
    Call<GetProductDataModel> getProductByProductIdDataFromServer(@Path("product_id") int product_id);

    @Headers("Content-Type: application/json")
    @POST("products")
    Call<AddProductResponse> saveProductDataFromServer(@Body AddProductRequest request);
//@POST("products")
//Call<AddProductResponse> saveProductDataFromServer(
//        @Header("Authorization") String token,
//        @Header("accept") String accept,
//        @Body AddProductRequest request
//);

    @Headers("Content-Type: application/json")
    @PUT("products/{product_id}")
    Call<AddProductResponse> updateProductDataFromServer(@Path("product_id") int product_id,
                                                  @Body AddProductRequest request);

    @Headers("Content-Type: application/json")
    @DELETE("products/{product_id}")
    Call<DeleteResponse> deleteProductDataFromServer(@Path("product_id") int product_id);



//    Suppliers

    @Headers("Content-Type: application/json")
    @GET("suppliers")
    Call<ArrayList<GetSuppliersDataModel>> getSupplierListDataFromServer(@Query("q") String q,
                                                                         @Query("is_active") int isActive,
                                                                         @Query("limit") int limit,
                                                                         @Query("offset") int offset);

    @Headers("Content-Type: application/json")
    @GET("suppliers/{supplier_id}")
    Call<GetSuppliersDataModel> getSuppliersByIdDataFromServer(@Path("supplier_id") int supplier_id);

    @Headers("Content-Type: application/json")
    @POST("suppliers")
    Call<AddSuppliersResponse> saveSuppliersDataFromServer(@Body AddSuppliersRequest request);

    @Headers("Content-Type: application/json")
    @PUT("suppliers/{supplier_id}")
    Call<AddSuppliersResponse> updateSupplierDataFromServer(@Path("supplier_id") int supplier_id,
                                                  @Body AddSuppliersRequest request);

    @Headers("Content-Type: application/json")
    @DELETE("suppliers/{supplier_id}")
    Call<DeleteResponse> deleteSupplierDataFromServer(@Path("supplier_id") int supplier_id);


//    Customers

    @Headers("Content-Type: application/json")
    @GET("customers")
    Call<ArrayList<GetCustomerDataModel>> getCustomersListDataFromServer(@Query("q") String q,
                                                                         @Query("is_active") int isActive,
                                                                         @Query("limit") int limit,
                                                                         @Query("offset") int offset);

    @Headers("Content-Type: application/json")
    @GET("customers/{customer_id}")
    Call<GetCustomerDataModel> getCustomersByIdDataFromServer(@Path("customer_id") int customer_id);

    @Headers("Content-Type: application/json")
    @POST("customers")
    Call<AddCustomerResponse> saveCustomersDataFromServer(@Body AddCustomerRequest request);

    @Headers("Content-Type: application/json")
    @PUT("customers/{customer_id}")
    Call<AddCustomerResponse> updateCustomersDataFromServer(@Path("customer_id") int customer_id,
                                                          @Body AddCustomerRequest request);

    @Headers("Content-Type: application/json")
    @DELETE("customers/{customer_id}")
    Call<DeleteResponse> deleteCustomersDataFromServer(@Path("customer_id") int customer_id);

//    Purchases

    @Headers("Content-Type: application/json")
    @GET("purchases")
    Call<ArrayList<GetPurchasesDataModel>> getPurchasesListDataFromServer(@Query("supplier_id") int supplierId,
                                                                          @Query("from_date") String fromDate,
                                                                          @Query("to_date") String toDate,
                                                                          @Query("status") String status,
                                                                          @Query("limit") int limit,
                                                                          @Query("offset") int offset);

    @Headers("Content-Type: application/json")
    @GET("purchases/{purchase_id}")
    Call<GetPurchasesDataModel> getPurchasesByIdDataFromServer(@Path("purchase_id") int purchase_id);

    @Headers("Content-Type: application/json")
    @POST("purchases")
    Call<AddPurchasesResponse> savePurchasesDataFromServer(@Body AddPurchasesRequest request);

    @Headers("Content-Type: application/json")
    @PUT("purchases/{purchase_id}")
    Call<AddPurchasesResponse> updatePurchasesDataFromServer(@Path("purchase_id") int purchase_id,
                                                            @Body AddPurchasesRequest request);

    @Headers("Content-Type: application/json")
    @DELETE("purchases/{purchase_id}")
    Call<DeleteResponse> deletePurchasesDataFromServer(@Path("purchase_id") int purchase_id);

//    Billing

    @Headers("Content-Type: application/json")
    @GET("billing")
    Call<ArrayList<GetBillingDataModel>> getBillingListDataFromServer(@Query("customer_id") int customerId,
                                                                      @Query("from_date") String fromDate,
                                                                      @Query("to_date") String toDate,
                                                                      @Query("limit") int limit,
                                                                      @Query("offset") int offset);

//    @Headers("Content-Type: application/json")
//    @GET("billing/{purchase_id}")
//    Call<GetCustomerDataModel> getPurchasesByIdDataFromServer(@Path("purchase_id") int purchase_id);

    @Headers("Content-Type: application/json")
    @GET("billing/{billing_id}")
    Call<GetBillingDataModel> getBillingByIdDataFromServer(@Path("billing_id") int billingId);

    @Headers("Content-Type: application/json")
    @POST("billing/{billing_id}/cancel")
    Call<BillingResponse> cancelBillingDataFromServer(@Path("billing_id") int billingId);

    @Headers("Content-Type: application/json")
    @POST("billing")
    Call<BillingResponse> createInvoiceDataFromServer(@Body BillingRequest request);

//    @Headers("Content-Type: application/json")
//    @PUT("purchases/{purchase_id}")
//    Call<AddPurchasesResponse> updatePurchasesDataFromServer(@Path("purchase_id") int purchase_id,
//                                                             @Body AddPurchasesRequest request);
//
//    @Headers("Content-Type: application/json")
//    @DELETE("purchases/{purchase_id}")
//    Call<DeleteResponse> deletePurchasesDataFromServer(@Path("purchase_id") int purchase_id);
}
