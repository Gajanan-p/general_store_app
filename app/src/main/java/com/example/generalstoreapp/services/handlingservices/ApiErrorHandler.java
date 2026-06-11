package com.example.generalstoreapp.services.handlingservices;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class ApiErrorHandler {

    public static String getErrorMessage(int code) {

        switch (code) {
            case 400:
                return "Bad request";
            case 401:
                return "Unauthorized access";
            case 403:
                return "Access forbidden";
            case 404:
                return "Resource not found";
            case 500:
                return "Server error";
            default:
                return "Something went wrong (" + code + ")";
        }
    }

    public static String getExceptionMessage(Throwable t) {

        if (t instanceof UnknownHostException) {
            return "No internet connection";

        } else if (t instanceof SocketTimeoutException) {
            return "Connection timeout";

        } else if (t instanceof ConnectException) {
            return "Failed to connect to server";

        } else if (t instanceof IOException) {
            return "Network error occurred";

        } else {
            return "Unexpected error";
        }
    }
}
