package com.example.generalstoreapp.services.handlingservices;

public class ApiResult<T> {
    public enum Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    public final Status status;
    public final T data;
    public final String message;
    public final int code;

    public ApiResult(Status status, T data, String message, int code) {
        this.status = status;
        this.data = data;
        this.message = message;
        this.code = code;
    }

    public static <T> ApiResult<T> success(T data) {
        return new ApiResult<>(Status.SUCCESS, data, null, 200);
    }

    public static <T> ApiResult<T> error(String message, int code) {
        return new ApiResult<>(Status.ERROR, null, message, code);
    }
}
