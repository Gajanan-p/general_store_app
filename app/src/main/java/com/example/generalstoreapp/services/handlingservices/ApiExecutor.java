package com.example.generalstoreapp.services.handlingservices;



import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiExecutor {

    public static <T> void execute(
            Call<T> call,
            ApiCallback<T> callback
    ) {

        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onResult(ApiResult.success(response.body()));
                } else {
                    String msg = ApiErrorHandler.getErrorMessage(response.code());
                    callback.onResult(ApiResult.error(msg, response.code()));
                }
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                String msg = ApiErrorHandler.getExceptionMessage(t);
                callback.onResult(ApiResult.error(msg, -1));
            }
        });
    }
}
