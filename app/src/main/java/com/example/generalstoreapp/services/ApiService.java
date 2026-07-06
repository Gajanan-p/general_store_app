package com.example.generalstoreapp.services;

import com.example.generalstoreapp.models.AddCustomerRequest;
import com.example.generalstoreapp.models.AddCustomerResponse;
import com.example.generalstoreapp.models.AddPaymentRequest;
import com.example.generalstoreapp.models.AddProductRequest;
import com.example.generalstoreapp.models.AddProductResponse;
import com.example.generalstoreapp.models.AddPurchasesRequest;
import com.example.generalstoreapp.models.AddPurchasesResponse;
import com.example.generalstoreapp.models.AddSuppliersRequest;
import com.example.generalstoreapp.models.AddSuppliersResponse;
import com.example.generalstoreapp.models.AddUsersByRoleRequest;
import com.example.generalstoreapp.models.BillingRequest;
import com.example.generalstoreapp.models.BillingResponse;
import com.example.generalstoreapp.models.CategoriesRequest;
import com.example.generalstoreapp.models.CategoriesListResponse;
import com.example.generalstoreapp.models.CustomerListResponse;
import com.example.generalstoreapp.models.DashboardSummaryModel;
import com.example.generalstoreapp.models.DeleteResponse;
import com.example.generalstoreapp.models.DeleteUnitsResponse;
import com.example.generalstoreapp.models.GetBillingDataModel;
import com.example.generalstoreapp.models.GetCategoriesModel;
import com.example.generalstoreapp.models.GetCustomerDataModel;
import com.example.generalstoreapp.models.GetPaymentDataModel;
import com.example.generalstoreapp.models.GetProductDataModel;
import com.example.generalstoreapp.models.GetPurchasesDataModel;
import com.example.generalstoreapp.models.GetRoleModel;
import com.example.generalstoreapp.models.GetSuppliersDataModel;
import com.example.generalstoreapp.models.GetUnitsDataModel;
import com.example.generalstoreapp.models.GetUsersByPermissionsModel;
import com.example.generalstoreapp.models.GetUsersByRoleModel;
import com.example.generalstoreapp.models.GetUsersModel;
import com.example.generalstoreapp.models.HealthResponse;
import com.example.generalstoreapp.models.LoginModel;
import com.example.generalstoreapp.models.LoginRequestModel;
import com.example.generalstoreapp.models.LowStockDashboardModel;
import com.example.generalstoreapp.models.PaymentsListResponse;
import com.example.generalstoreapp.models.PermissionsModel;
import com.example.generalstoreapp.models.PermissionsResponse;
import com.example.generalstoreapp.models.PriceHistoryResponse;
import com.example.generalstoreapp.models.ProductListResponse;
import com.example.generalstoreapp.models.RbacMeModel;
import com.example.generalstoreapp.models.RecentInvoiceModel;
import com.example.generalstoreapp.models.RefreshRequest;
import com.example.generalstoreapp.models.RefreshResponse;
import com.example.generalstoreapp.models.RegistrationRequest;
import com.example.generalstoreapp.models.RegistrationResponse;
import com.example.generalstoreapp.models.RoleRequest;
import com.example.generalstoreapp.models.RoleResponse;
import com.example.generalstoreapp.models.RolesListResponse;
import com.example.generalstoreapp.models.SalesInvoiceListResponse;
import com.example.generalstoreapp.models.Store;
import com.example.generalstoreapp.models.SuppliersListResponse;
import com.example.generalstoreapp.models.TopCustomerModel;
import com.example.generalstoreapp.models.UnitsRequest;
import com.example.generalstoreapp.models.UnitsListResponse;
import com.example.generalstoreapp.models.Users;
import com.example.generalstoreapp.models.UsersListResponse;
import com.example.generalstoreapp.models.UsersModel;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Streaming;

public interface ApiService {

//    Health
    @GET("api/v1/health")
    Call<HealthResponse> healthCheck();

//    Authentication services
    @Headers("Content-Type: application/json")
    @POST("api/v1/auth/register")
    Call<RegistrationResponse> callRegistrationDataFromServer(@Body RegistrationRequest request);

    @Headers("Content-Type: application/json")
    @POST("api/v1/auth/login")
    Call<LoginModel> fetchLoginDataFromServer(@Body LoginRequestModel requestModel);

    @FormUrlEncoded
    @POST("api/v1/auth/token")
    Call<LoginModel> loginWithToken(
            @Field("grant_type") String grantType,
            @Field("username") String username,
            @Field("password") String password,
            @Field("scope") String scope,
            @Field("client_id") String clientId,
            @Field("client_secret") String clientSecret
    );

    @Headers("Content-Type: application/json")
    @POST("api/v1/auth/refresh")
    Call<RefreshResponse> refreshToken(@Body RefreshRequest request);

    @Headers("Content-Type: application/json")
    @POST("api/v1/auth/logout")
    Call<DeleteResponse> logout(@Body RefreshRequest request);

    @Headers("Content-Type: application/json")
    @GET("api/v1/auth/me")
    Call<UsersModel> callUsersDataFromServer();

//    Store
    @GET("api/v1/store/me")
    Call<Store> getMyStore();

//    RBAC
    @GET("api/v1/rbac/me")
    Call<RbacMeModel> getMyPermissions();

    @GET("api/v1/rbac/permissions")
    Call<PermissionsResponse> listPermissions(@Query("limit") Integer limit, @Query("offset") Integer offset);

    @GET("api/v1/rbac/roles")
    Call<RolesListResponse> listRoles();

    @GET("api/v1/rbac/roles/{role_id}")
    Call<GetRoleModel> getRole(@Path("role_id") int roleId);

    @PUT("api/v1/rbac/roles/{role_id}/permissions")
    Call<RoleResponse> updateRolePermissions(@Path("role_id") int roleId, @Body RoleRequest request);

//    Users services
    @Headers("Content-Type: application/json")
    @POST("api/v1/users/staff")
    Call<Users> createStaffUser(@Body AddUsersByRoleRequest request);

    @Headers("Content-Type: application/json")
    @GET("api/v1/users")
    Call<UsersListResponse> listUsers(@Query("limit") Integer limit, @Query("offset") Integer offset);

    @Headers("Content-Type: application/json")
    @GET("api/v1/users/{user_id}")
    Call<Users> getUser(@Path("user_id") int userId);

    @Headers("Content-Type: application/json")
    @PUT("api/v1/users/staff/{user_id}")
    Call<Users> updateStaffUser(@Path("user_id") int userId, @Body AddUsersByRoleRequest request);

    @Headers("Content-Type: application/json")
    @DELETE("api/v1/users/staff/{user_id}")
    Call<DeleteResponse> deleteStaffUser(@Path("user_id") int userId);

//    Old/Compatibility Endpoints (keeping some but using updated models where possible)
    @Headers("Content-Type: application/json")
    @GET("api/v1/rbac/roles")
    Call<ArrayList<GetRoleModel>> callRoleDataFromServer();

    @Headers("Content-Type: application/json")
    @POST("api/v1/rbac/roles")
    Call<RoleResponse> callAddRoleDataFromServer(@Body RoleRequest request);

    @Headers("Content-Type: application/json")
    @GET("api/v1/users")
    Call<ArrayList<GetUsersModel>> getUsersDataFromServer();

    @Headers("Content-Type: application/json")
    @GET("api/v1/users/{user_id}/roles")
    Call<GetUsersByRoleModel> getUsersByRoleDataFromServer(@Path("user_id") int userId);

    @Headers("Content-Type: application/json")
    @GET("api/v1/users/{user_id}/permissions")
    Call<GetUsersByPermissionsModel> getUsersByPermissionDataFromServer(@Path("user_id") int userId);

//    Categories
    @Headers("Content-Type: application/json")
    @GET("api/v1/product-categories")
    Call<CategoriesListResponse> getCategoryDataFromServer(@Query("limit") Integer limit, @Query("offset") Integer offset);

    @Headers("Content-Type: application/json")
    @GET("api/v1/product-categories/{category_id}")
    Call<GetCategoriesModel> getCategoryByIdDataFromServer(@Path("category_id") int category_id);

    @Headers("Content-Type: application/json")
    @POST("api/v1/product-categories")
    Call<GetCategoriesModel> saveCategoryDataFromServer(@Body CategoriesRequest request);

    @Headers("Content-Type: application/json")
    @PUT("api/v1/product-categories/{category_id}")
    Call<GetCategoriesModel> updateCategoryDataFromServer(@Path("category_id") int category_id,
                                                       @Body CategoriesRequest request);
    @Headers("Content-Type: application/json")
    @DELETE("api/v1/product-categories/{category_id}")
    Call<DeleteResponse> deleteCategoryDataFromServer(@Path("category_id") int category_id);

//    Units
    @Headers("Content-Type: application/json")
    @GET("api/v1/product-units")
    Call<UnitsListResponse> getUnitsDataFromServer(@Query("limit") Integer limit, @Query("offset") Integer offset);

    @Headers("Content-Type: application/json")
    @GET("api/v1/product-units/{unit_id}")
    Call<GetUnitsDataModel> getUnitsByIdDataFromServer(@Path("unit_id") int unit_id);

    @Headers("Content-Type: application/json")
    @POST("api/v1/product-units")
    Call<GetUnitsDataModel> saveUnitsDataFromServer(@Body UnitsRequest request);

    @Headers("Content-Type: application/json")
    @PUT("api/v1/product-units/{unit_id}")
    Call<GetUnitsDataModel> updateUnitsDataFromServer(@Path("unit_id") int unit_id,
                                                  @Body UnitsRequest request);

    @Headers("Content-Type: application/json")
    @DELETE("api/v1/product-units/{unit_id}")
    Call<DeleteUnitsResponse> deleteUnitsDataFromServer(@Path("unit_id") int unit_id);

//    Products
    @Headers("Content-Type: application/json")
    @GET("api/v1/products")
    Call<ProductListResponse> getProductListDataFromServer(@Query("is_active") Boolean isActive,
                                                                      @Query("low_stock") Boolean lowStock,
                                                                      @Query("limit") Integer limit,
                                                                      @Query("offset") Integer offset);

    @GET("api/v1/products/alerts/low-stock")
    Call<ProductListResponse> getLowStockProducts(@Query("limit") Integer limit, @Query("offset") Integer offset);

    @Headers("Content-Type: application/json")
    @GET("api/v1/products/by-barcode/{barcode}")
    Call<GetProductDataModel> getProductByBarcodeDataFromServer(@Path("barcode") String barcode);

    @Headers("Content-Type: application/json")
    @GET("api/v1/products/{product_id}")
    Call<GetProductDataModel> getProductByProductIdDataFromServer(@Path("product_id") int product_id);

    @Headers("Content-Type: application/json")
    @POST("api/v1/products")
    Call<GetProductDataModel> saveProductDataFromServer(@Body AddProductRequest request);

    @Headers("Content-Type: application/json")
    @PUT("api/v1/products/{product_id}")
    Call<GetProductDataModel> updateProductDataFromServer(@Path("product_id") int product_id,
                                                  @Body AddProductRequest request);

    @Headers("Content-Type: application/json")
    @DELETE("api/v1/products/{product_id}")
    Call<DeleteResponse> deleteProductDataFromServer(@Path("product_id") int product_id);

    @GET("api/v1/products/{product_id}/price-history")
    Call<PriceHistoryResponse> getProductPriceHistory(@Path("product_id") int productId, @Query("limit") Integer limit, @Query("offset") Integer offset);

    @PATCH("api/v1/products/{product_id}/stock")
    Call<GetProductDataModel> updateProductStock(@Path("product_id") int productId, @Body AddProductRequest request);

//    Suppliers

    @Headers("Content-Type: application/json")
    @GET("api/v1/suppliers")
    Call<SuppliersListResponse> getSupplierListDataFromServer(@Query("q") String q,
                                                                         @Query("is_active") Boolean isActive,
                                                                         @Query("limit") int limit,
                                                                         @Query("offset") int offset);

    @Headers("Content-Type: application/json")
    @GET("api/v1/suppliers/{supplier_id}")
    Call<GetSuppliersDataModel> getSuppliersByIdDataFromServer(@Path("supplier_id") int supplier_id);

    @Headers("Content-Type: application/json")
    @POST("api/v1/suppliers")
    Call<GetSuppliersDataModel> saveSuppliersDataFromServer(@Body AddSuppliersRequest request);

    @Headers("Content-Type: application/json")
    @PUT("api/v1/suppliers/{supplier_id}")
    Call<GetSuppliersDataModel> updateSupplierDataFromServer(@Path("supplier_id") int supplier_id,
                                                  @Body AddSuppliersRequest request);

    @Headers("Content-Type: application/json")
    @DELETE("api/v1/suppliers/{supplier_id}")
    Call<DeleteResponse> deleteSupplierDataFromServer(@Path("supplier_id") int supplier_id);

//    Customers
    @Headers("Content-Type: application/json")
    @GET("api/v1/customers")
    Call<CustomerListResponse> getCustomersListDataFromServer(@Query("q") String q,
                                                                         @Query("is_active") Boolean isActive,
                                                                         @Query("limit") int limit,
                                                                         @Query("offset") int offset);

    @Headers("Content-Type: application/json")
    @GET("api/v1/customers/{customer_id}")
    Call<GetCustomerDataModel> getCustomersByIdDataFromServer(@Path("customer_id") int customer_id);

    @Headers("Content-Type: application/json")
    @POST("api/v1/customers")
    Call<AddCustomerResponse> saveCustomersDataFromServer(@Body AddCustomerRequest request);

    @Headers("Content-Type: application/json")
    @PUT("api/v1/customers/{customer_id}")
    Call<AddCustomerResponse> updateCustomersDataFromServer(@Path("customer_id") int customer_id,
                                                          @Body AddCustomerRequest request);

    @Headers("Content-Type: application/json")
    @DELETE("api/v1/customers/{customer_id}")
    Call<DeleteResponse> deleteCustomersDataFromServer(@Path("customer_id") int customer_id);

//    Purchase Invoices
    @Headers("Content-Type: application/json")
    @GET("api/v1/purchase-invoices")
    Call<ArrayList<GetPurchasesDataModel>> getPurchasesListDataFromServer(@Query("supplier_id") int supplierId,
                                                                          @Query("from_date") String fromDate,
                                                                          @Query("to_date") String toDate,
                                                                          @Query("status") String status,
                                                                          @Query("limit") int limit,
                                                                          @Query("offset") int offset);

    @Headers("Content-Type: application/json")
    @GET("api/v1/purchase-invoices/{purchase_invoice_id}")
    Call<GetPurchasesDataModel> getPurchasesByIdDataFromServer(@Path("purchase_invoice_id") int purchase_id);

    @Headers("Content-Type: application/json")
    @POST("api/v1/purchase-invoices")
    Call<AddPurchasesResponse> savePurchasesDataFromServer(@Body AddPurchasesRequest request);

    @Headers("Content-Type: application/json")
    @PUT("api/v1/purchase-invoices/{purchase_invoice_id}")
    Call<AddPurchasesResponse> updatePurchasesDataFromServer(@Path("purchase_invoice_id") int purchase_invoice_id,
                                                       @Body AddPurchasesRequest request);

    @Headers("Content-Type: application/json")
    @DELETE("api/v1/purchase-invoices/{purchase_invoice_id}")
    Call<DeleteResponse> deletePurchasesDataFromServer(@Path("purchase_invoice_id") int purchase_invoice_id);

    @Headers("Content-Type: application/json")
    @POST("api/v1/purchase-invoices/{purchase_invoice_id}/cancel")
    Call<DeleteResponse> cancelPurchaseInvoice(@Path("purchase_invoice_id") int purchaseId);

//    Sales Invoices
    @GET("api/v1/sales-invoices")
    Call<SalesInvoiceListResponse> getBillingListDataFromServer(@Query("customer_id") Integer customerId,
                                                                      @Query("from_date") String fromDate,
                                                                      @Query("to_date") String toDate,
                                                                      @Query("include_cancelled") Boolean includeCancelled,
                                                                      @Query("limit") Integer limit,
                                                                      @Query("offset") Integer offset);

    @Headers("Content-Type: application/json")
    @GET("api/v1/sales-invoices/{invoice_id}")
    Call<GetBillingDataModel> getBillingByIdDataFromServer(@Path("invoice_id") int billingId);

    @Headers("Content-Type: application/json")
    @POST("api/v1/sales-invoices/{invoice_id}/cancel")
    Call<GetBillingDataModel> cancelBillingDataFromServer(@Path("invoice_id") int billingId);

    @Headers("Content-Type: application/json")
    @POST("api/v1/sales-invoices")
    Call<GetBillingDataModel> createInvoiceDataFromServer(@Body BillingRequest request);

//    Payments
    @POST("api/v1/payments")
    Call<GetPaymentDataModel> createPayment(@Body AddPaymentRequest request);

    @GET("api/v1/payments")
    Call<PaymentsListResponse> listPayments(@Query("limit") Integer limit, @Query("offset") Integer offset);

    @GET("api/v1/payments/{payment_id}")
    Call<GetPaymentDataModel> getPayment(@Path("payment_id") int paymentId);

    @DELETE("api/v1/payments/{payment_id}")
    Call<DeleteResponse> cancelPayment(@Path("payment_id") int paymentId);

//    Supplier Payments
    @POST("api/v1/supplier-payments")
    Call<ResponseBody> createSupplierPayment(@Body Object request);

    @GET("api/v1/supplier-payments")
    Call<ResponseBody> listSupplierPayments();

    @GET("api/v1/supplier-payments/{supplier_payment_id}")
    Call<ResponseBody> getSupplierPayment(@Path("supplier_payment_id") int supplierPaymentId);

    @DELETE("api/v1/supplier-payments/{supplier_payment_id}")
    Call<DeleteResponse> cancelSupplierPayment(@Path("supplier_payment_id") int supplierPaymentId);

//    Expenses
    @POST("api/v1/expenses")
    Call<ResponseBody> createExpense(@Body Object request);

    @GET("api/v1/expenses")
    Call<ResponseBody> listExpenses();

    @GET("api/v1/expenses/{expense_id}")
    Call<ResponseBody> getExpense(@Path("expense_id") int expenseId);

    @PUT("api/v1/expenses/{expense_id}")
    Call<ResponseBody> updateExpense(@Path("expense_id") int expenseId, @Body Object request);

    @DELETE("api/v1/expenses/{expense_id}")
    Call<DeleteResponse> deleteExpense(@Path("expense_id") int expenseId);

//    Exports
    @Streaming
    @GET("api/v1/exports/reports/sales.xlsx")
    Call<ResponseBody> exportSalesReport();

    @Streaming
    @GET("api/v1/exports/reports/customer-outstanding.xlsx")
    Call<ResponseBody> exportCustomerOutstandingReport();

    @Streaming
    @GET("api/v1/exports/reports/inventory.xlsx")
    Call<ResponseBody> exportInventoryReport();

    @Streaming
    @GET("api/v1/exports/reports/payments.xlsx")
    Call<ResponseBody> exportPaymentReport();

    @Streaming
    @GET("api/v1/exports/reports/expenses.xlsx")
    Call<ResponseBody> exportExpenseReport();

    @Streaming
    @GET("api/v1/exports/reports/supplier-payments.xlsx")
    Call<ResponseBody> exportSupplierPaymentReport();

//    Print
    @GET("api/v1/print/sales-invoices/{invoice_id}")
    Call<ResponseBody> printSalesInvoice(@Path("invoice_id") int invoiceId);

    @GET("api/v1/print/purchase-invoices/{purchase_invoice_id}")
    Call<ResponseBody> printPurchaseInvoice(@Path("purchase_invoice_id") int purchaseInvoiceId);

//    Dashboard
    @GET("api/v1/dashboard/summary")
    Call<DashboardSummaryModel> getDashboardSummary();

    @GET("api/v1/dashboard/recent-invoices")
    Call<ArrayList<RecentInvoiceModel>> getRecentInvoices(@Query("limit") Integer limit);

    @GET("api/v1/dashboard/low-stock")
    Call<ArrayList<LowStockDashboardModel>> getDashboardLowStock(@Query("limit") Integer limit);

    @GET("api/v1/dashboard/top-customers")
    Call<ArrayList<TopCustomerModel>> getTopCustomers(@Query("limit") Integer limit);

//    Reports
    @GET("api/v1/reports/sales")
    Call<ResponseBody> getSalesReport();

    @GET("api/v1/reports/customer-outstanding")
    Call<ResponseBody> getCustomerOutstandingReport();

    @GET("api/v1/reports/inventory")
    Call<ResponseBody> getInventoryReport();

    @GET("api/v1/reports/payments")
    Call<ResponseBody> getPaymentReport();

    @GET("api/v1/reports/expenses")
    Call<ResponseBody> getExpenseReport();

    @GET("api/v1/reports/supplier-payments")
    Call<ResponseBody> getSupplierPaymentReport();
}
