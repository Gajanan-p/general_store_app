package com.example.generalstoreapp.viewmodel;

import android.content.Context;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.generalstoreapp.models.AddCustomerRequest;
import com.example.generalstoreapp.models.AddCustomerResponse;
import com.example.generalstoreapp.models.BillingItemsRequest;
import com.example.generalstoreapp.models.BillingRequest;
import com.example.generalstoreapp.models.CartItem;
import com.example.generalstoreapp.models.GetCustomerDataModel;
import com.example.generalstoreapp.models.GetProductDataModel;
import com.example.generalstoreapp.repository.BillingRepository;
import com.example.generalstoreapp.repository.CustomerRepository;
import com.example.generalstoreapp.services.handlingservices.ApiResult;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class BillingViewModel extends ViewModel {

    private BillingRepository billingRepository;
    private CustomerRepository customerRepository;

    private final MutableLiveData<List<GetCustomerDataModel>> customersLiveData = new MutableLiveData<>();
    private final MutableLiveData<GetCustomerDataModel> selectedCustomer = new MutableLiveData<>();
    private final MutableLiveData<List<CartItem>> cartItems = new MutableLiveData<>(new ArrayList<>());
    
    private final MutableLiveData<Double> subtotal = new MutableLiveData<>(0.0);
    private final MutableLiveData<Double> totalDiscount = new MutableLiveData<>(0.0);
    private final MutableLiveData<Double> totalGst = new MutableLiveData<>(0.0);
    private final MutableLiveData<Double> grandTotal = new MutableLiveData<>(0.0);
    private final MutableLiveData<Double> balance = new MutableLiveData<>(0.0);
    private final MutableLiveData<Double> totalQty = new MutableLiveData<>(0.0);
    
    private double paidAmount = 0.0;
    private double globalDiscountPercent = 0.0;
    private final MutableLiveData<String> invoiceNo = new MutableLiveData<>("");
    private final MutableLiveData<String> invoiceDate = new MutableLiveData<>("");

    private final MutableLiveData<Boolean> loading = new MutableLiveData<>(false);
    private final MutableLiveData<String> error = new MutableLiveData<>();
    private final MutableLiveData<Boolean> success = new MutableLiveData<>(false);

    public void init(Context context) {
        if (billingRepository == null) {
            billingRepository = new BillingRepository(context);
            customerRepository = new CustomerRepository(context);
            
            invoiceNo.setValue("INV-" + System.currentTimeMillis() / 1000);
            invoiceDate.setValue(new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date()));
        }
    }

    public LiveData<List<GetCustomerDataModel>> getCustomersLiveData() { return customersLiveData; }
    public LiveData<GetCustomerDataModel> getSelectedCustomer() { return selectedCustomer; }
    public LiveData<List<CartItem>> getCartItems() { return cartItems; }
    public LiveData<Double> getSubtotal() { return subtotal; }
    public LiveData<Double> getTotalDiscount() { return totalDiscount; }
    public LiveData<Double> getTotalGst() { return totalGst; }
    public LiveData<Double> getGrandTotal() { return grandTotal; }
    public LiveData<Double> getBalance() { return balance; }
    public LiveData<Double> getTotalQty() { return totalQty; }
    public LiveData<String> getInvoiceNo() { return invoiceNo; }
    public LiveData<String> getInvoiceDate() { return invoiceDate; }
    public LiveData<Boolean> getLoading() { return loading; }
    public LiveData<String> getError() { return error; }
    public LiveData<Boolean> getSuccess() { return success; }

    public void fetchCustomers(String q) {
        customerRepository.getCustomers(q, true, 50, 0, result -> {
            if (result.status == ApiResult.Status.SUCCESS && result.data != null) {
                customersLiveData.setValue(result.data.getItems());
            }
        });
    }

    public void addCustomerAndSelect(AddCustomerRequest request) {
        loading.setValue(true);
        customerRepository.addCustomer(request, result -> {
            loading.setValue(false);
            if (result.status == ApiResult.Status.SUCCESS && result.data != null) {
                AddCustomerResponse response = result.data;
                GetCustomerDataModel newCustomer = new GetCustomerDataModel();
                newCustomer.setId(response.getId());
                newCustomer.setName(response.getName());
                newCustomer.setPhone(response.getPhone());
                newCustomer.setEmail(response.getEmail());
                newCustomer.setAddressLine1(response.getAddressLine1());
                newCustomer.setAddressLine2(response.getAddressLine2());
                newCustomer.setCity(response.getCity());
                newCustomer.setState(response.getState());
                newCustomer.setPincode(response.getPincode());
                newCustomer.setOpeningBalance(response.getOpeningBalance());
                newCustomer.setIsActive(response.getIsActive());
                
                selectCustomer(newCustomer);
            } else {
                error.setValue(result.message != null ? result.message : "Error adding customer");
            }
        });
    }

    public void selectCustomer(GetCustomerDataModel customer) {
        selectedCustomer.setValue(customer);
    }

    public void addProductToCart(GetProductDataModel product) {
        addProductToCart(product, 1, product.getSellPrice().doubleValue());
    }

    public void addProductToCart(GetProductDataModel product, int quantity, double overrideRate) {
        List<CartItem> currentItems = cartItems.getValue();
        if (currentItems == null) currentItems = new ArrayList<>();
        
        boolean found = false;
        for (CartItem item : currentItems) {
            if (item.getProduct().getId().equals(product.getId())) {
                item.setQuantity(item.getQuantity() + quantity);
                // If rate is different, we might want to handle it, but for now we'll update it
                item.setOverridePrice(overrideRate);
                found = true;
                break;
            }
        }
        
        if (!found) {
            CartItem newItem = new CartItem(product, quantity);
            newItem.setOverridePrice(overrideRate);
            newItem.setDiscountPercent(globalDiscountPercent);
            currentItems.add(newItem);
        }
        
        cartItems.setValue(new ArrayList<>(currentItems));
        calculateTotals();
    }

    public void updateQuantity(CartItem item, int qty) {
        if (qty <= 0) {
            removeProductFromCart(item);
        } else {
            item.setQuantity(qty);
            cartItems.setValue(cartItems.getValue());
            calculateTotals();
        }
    }

    public void removeProductFromCart(CartItem item) {
        List<CartItem> currentItems = cartItems.getValue();
        if (currentItems != null) {
            currentItems.remove(item);
            cartItems.setValue(new ArrayList<>(currentItems));
            calculateTotals();
        }
    }

    public void setGlobalDiscount(double discountPercent) {
        this.globalDiscountPercent = discountPercent;
        List<CartItem> items = cartItems.getValue();
        if (items != null) {
            for (CartItem item : items) {
                item.setDiscountPercent(discountPercent);
            }
        }
        cartItems.setValue(items);
        calculateTotals();
    }

    public void setPaidAmount(double amount) {
        this.paidAmount = amount;
        calculateTotals();
    }

    private void calculateTotals() {
        double sub = 0;
        double disc = 0;
        double tax = 0;
        double qty = 0;
        
        List<CartItem> items = cartItems.getValue();
        if (items != null) {
            for (CartItem item : items) {
                sub += item.getLineTotal();
                disc += item.getDiscountAmount();
                tax += item.getGstAmount();
                qty += item.getQuantity();
            }
        }
        
        subtotal.setValue(sub);
        totalDiscount.setValue(disc);
        totalGst.setValue(tax);
        totalQty.setValue(qty);
        double total = sub - disc + tax;
        grandTotal.setValue(total);
        balance.setValue(total - paidAmount);
    }

    public void submitBill() {
        if (selectedCustomer.getValue() == null) {
            error.setValue("Please select a customer");
            return;
        }
        if (cartItems.getValue() == null || cartItems.getValue().isEmpty()) {
            error.setValue("Please add at least one item");
            return;
        }

        loading.setValue(true);
        
        BillingRequest request = new BillingRequest();
        request.setCustomerId(selectedCustomer.getValue().getId());
        request.setInvoiceDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date()));
        request.setPaidAmount((int) paidAmount);
        request.setDiscountAmount(totalDiscount.getValue().intValue());
        
        List<BillingItemsRequest> itemRequests = new ArrayList<>();
        for (CartItem item : cartItems.getValue()) {
            itemRequests.add(new BillingItemsRequest(
                item.getProduct().getId(),
                item.getQuantity(),
                item.getProduct().getSellPrice(),
                item.getProduct().getGstPercent()
            ));
        }
        request.setItems(itemRequests);
        request.setPaymentMethod("Cash");

        billingRepository.createInvoice(request, result -> {
            loading.setValue(false);
            if (result.status == ApiResult.Status.SUCCESS) {
                success.setValue(true);
            } else {
                error.setValue(result.message);
            }
        });
    }

    public void reset() {
        cartItems.setValue(new ArrayList<>());
        selectedCustomer.setValue(null);
        paidAmount = 0;
        globalDiscountPercent = 0;
        success.setValue(false);
        calculateTotals();
    }
}
