package com.example.generalstoreapp.utils;

import android.content.Context;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.generalstoreapp.models.CartItem;
import com.example.generalstoreapp.models.GetBillingDataModel;
import com.example.generalstoreapp.models.GetCustomerDataModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PrintUtils {

    public static void printInvoice(Context context, GetCustomerDataModel customer, List<CartItem> items, 
                                   String invoiceNo, String date, double subtotal, double discount, 
                                   double tax, double grandTotal, double paid, double balance) {
        WebView webView = new WebView(context);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                createWebPrintJob(context, view, "Invoice_" + invoiceNo);
            }
        });

        String htmlContent = generateInvoiceHtml(customer, items, invoiceNo, date, subtotal, discount, tax, grandTotal, paid, balance);
        webView.loadDataWithBaseURL(null, htmlContent, "text/html", "utf-8", null);
    }

    public static void printReport(Context context, List<GetBillingDataModel> billingList) {
        WebView webView = new WebView(context);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                createWebPrintJob(context, view, "Today_Report_" + new SimpleDateFormat("yyyyMMdd_HHmm", Locale.getDefault()).format(new Date()));
            }
        });

        String htmlContent = generateHtml(billingList);
        webView.loadDataWithBaseURL(null, htmlContent, "text/html", "utf-8", null);
    }

    private static void createWebPrintJob(Context context, WebView webView, String jobName) {
        PrintManager printManager = (PrintManager) context.getSystemService(Context.PRINT_SERVICE);
        PrintDocumentAdapter printAdapter = webView.createPrintDocumentAdapter(jobName);

        PrintAttributes attributes = new PrintAttributes.Builder()
                .setMediaSize(PrintAttributes.MediaSize.ISO_A4)
                .setResolution(new PrintAttributes.Resolution("pdf", "pdf", 600, 600))
                .setMinMargins(PrintAttributes.Margins.NO_MARGINS)
                .build();

        if (printManager != null) {
            printManager.print(jobName, printAdapter, attributes);
        }
    }

    private static String generateInvoiceHtml(GetCustomerDataModel customer, List<CartItem> items,
                                              String invoiceNo, String date, double subtotal, double discount,
                                              double tax, double grandTotal, double paid, double balance) {
        StringBuilder html = new StringBuilder();
        html.append("<html><head><style>");
        html.append("body { font-family: 'Segoe UI', Tahoma, sans-serif; margin: 0; padding: 20px; color: #333; }");
        html.append(".invoice-container { max-width: 800px; margin: auto; border: 1px solid #eee; padding: 30px; }");
        html.append(".header { display: flex; justify-content: space-between; border-bottom: 2px solid #d32f2f; padding-bottom: 20px; }");
        html.append(".store-info h1 { margin: 0; color: #d32f2f; font-size: 24px; }");
        html.append(".store-info p { margin: 5px 0; font-size: 14px; }");
        html.append(".invoice-title { text-align: right; }");
        html.append(".invoice-title h2 { margin: 0; color: #777; font-size: 20px; }");
        html.append(".details { display: flex; justify-content: space-between; margin-top: 30px; }");
        html.append(".customer-info h3 { border-bottom: 1px solid #ddd; padding-bottom: 5px; font-size: 16px; }");
        html.append(".customer-info p { margin: 5px 0; font-size: 14px; }");
        html.append(".invoice-meta p { margin: 5px 0; font-size: 14px; }");
        html.append("table { width: 100%; border-collapse: collapse; margin-top: 30px; }");
        html.append("th { background-color: #f8f9fa; color: #333; text-align: left; padding: 12px; border-bottom: 2px solid #dee2e6; }");
        html.append("td { padding: 12px; border-bottom: 1px solid #eee; font-size: 14px; }");
        html.append(".totals { margin-top: 30px; text-align: right; border-top: 1px solid #eee; padding-top: 10px; }");
        html.append(".totals p { margin: 5px 0; font-size: 14px; }");
        html.append(".grand-total { font-size: 18px; font-weight: bold; color: #d32f2f; margin-top: 10px !important; }");
        html.append(".footer { margin-top: 50px; text-align: center; font-size: 12px; color: #999; border-top: 1px solid #eee; padding-top: 20px; }");
        html.append("</style></head><body>");

        html.append("<div class='invoice-container'>");
        html.append("<div class='header'><div class='store-info'><h1>Rajput General Store</h1>");
        html.append("<p>123 Market Street, City, State</p><p>Phone: +91 9876543210</p></div>");
        html.append("<div class='invoice-title'><h2>Tax Invoice</h2></div></div>");

        String custName = customer != null ? customer.getName() : "Walk-in Customer";
        String custPhone = customer != null ? customer.getPhone() : "N/A";

        html.append("<div class='details'><div class='customer-info'><h3>Bill To:</h3>");
        html.append("<p><strong>").append(custName).append("</strong></p>");
        html.append("<p>Phone: ").append(custPhone).append("</p></div>");
        html.append("<div class='invoice-meta'><p><strong>Invoice No:</strong> ").append(invoiceNo).append("</p>");
        html.append("<p><strong>Date:</strong> ").append(date).append("</p></div></div>");

        html.append("<table><thead><tr><th>Item</th><th>Qty</th><th>Price</th><th>Tax</th><th>Total</th></tr></thead><tbody>");
        for (CartItem item : items) {
            html.append("<tr>");
            html.append("<td>").append(item.getProduct().getName()).append("</td>");
            html.append("<td>").append(item.getQuantity()).append("</td>");
            html.append("<td>₹").append(String.format(Locale.getDefault(), "%.2f", item.getPrice())).append("</td>");
            html.append("<td>₹").append(String.format(Locale.getDefault(), "%.2f", item.getGstAmount())).append("</td>");
            html.append("<td>₹").append(String.format(Locale.getDefault(), "%.2f", item.getFinalAmount())).append("</td>");
            html.append("</tr>");
        }
        html.append("</tbody></table>");

        html.append("<div class='totals'>");
        html.append("<p>Subtotal: ₹").append(String.format(Locale.getDefault(), "%.2f", subtotal)).append("</p>");
        html.append("<p>Discount: ₹").append(String.format(Locale.getDefault(), "%.2f", discount)).append("</p>");
        html.append("<p>Tax: ₹").append(String.format(Locale.getDefault(), "%.2f", tax)).append("</p>");
        html.append("<p class='grand-total'>Grand Total: ₹").append(String.format(Locale.getDefault(), "%.2f", grandTotal)).append("</p>");
        html.append("<p>Paid Amount: ₹").append(String.format(Locale.getDefault(), "%.2f", paid)).append("</p>");
        html.append("<p>Balance Due: ₹").append(String.format(Locale.getDefault(), "%.2f", balance)).append("</p></div>");

        html.append("<div class='footer'><p>Thank you for shopping with us!</p><p>Please visit again.</p></div>");
        html.append("</div></body></html>");

        return html.toString();
    }

    private static String generateHtml(List<GetBillingDataModel> list) {
        StringBuilder html = new StringBuilder();
        html.append("<html><head><style>");
        html.append("body { font-family: 'Helvetica', 'Arial', sans-serif; padding: 20px; }");
        html.append("h1 { text-align: center; color: #333; }");
        html.append("table { width: 100%; border-collapse: collapse; margin-top: 20px; }");
        html.append("th, td { border: 1px solid #ddd; padding: 12px; text-align: left; }");
        html.append("th { background-color: #f2f2f2; font-weight: bold; }");
        html.append(".summary { margin-top: 30px; border-top: 2px solid #333; padding-top: 10px; }");
        html.append(".summary-item { font-size: 1.2em; margin-bottom: 5px; }");
        html.append(".footer { margin-top: 50px; text-align: center; font-size: 0.8em; color: #777; }");
        html.append("@media print { .no-print { display: none; } }");
        html.append("</style></head><body>");

        html.append("<h1>Today's Sales Report</h1>");
        html.append("<p>Date: ").append(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date())).append("</p>");

        html.append("<table>");
        html.append("<tr><th>Sr. No.</th><th>Customer Name</th><th>Total Amount</th><th>Payment Status</th></tr>");

        double totalAmount = 0;
        int paidCount = 0;
        int pendingCount = 0;

        for (int i = 0; i < list.size(); i++) {
            GetBillingDataModel billing = list.get(i);
            String customerName = (billing.getCustomer() != null) ? billing.getCustomer().getName() : "Unknown";
            int amount = (billing.getTotalAmount() != null) ? billing.getTotalAmount() : 0;
            
            double due = (billing.getDueAmount() != null) ? (double) billing.getDueAmount() : 0.0;
            String status = (due <= 0) ? "Paid" : "Pending";

            html.append("<tr>");
            html.append("<td>").append(i + 1).append("</td>");
            html.append("<td>").append(customerName).append("</td>");
            html.append("<td>₹").append(amount).append("</td>");
            html.append("<td>").append(status).append("</td>");
            html.append("</tr>");

            totalAmount += amount;
            if ("Paid".equals(status)) {
                paidCount++;
            } else {
                pendingCount++;
            }
        }
        html.append("</table>");

        html.append("<div class='summary'>");
        html.append("<div class='summary-item'><strong>Overall Total Amount: ₹").append(String.format(Locale.getDefault(), "%.2f", totalAmount)).append("</strong></div>");
        html.append("<div class='summary-item'>Paid Records: ").append(paidCount).append("</div>");
        html.append("<div class='summary-item'>Pending Records: ").append(pendingCount).append("</div>");
        html.append("</div>");

        html.append("<div class='footer'>Generated by Rajput General Store App</div>");
        html.append("</body></html>");

        return html.toString();
    }
}
