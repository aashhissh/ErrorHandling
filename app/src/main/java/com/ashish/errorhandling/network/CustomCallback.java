package com.ashish.errorhandling.network;

import com.ashish.errorhandling.domain.converters.GetUserDetailsModelConverter;
import com.ashish.errorhandling.network.response.GetUserDetailsResponse;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author ashish
 * @since 02/03/18
 */
public class CustomCallback<T> implements Callback<T> {

    private RetrofitCustomCallback<T> retrofitCustomCallback;

    public CustomCallback(RetrofitCustomCallback<T> retrofitCustomCallback) {
        this.retrofitCustomCallback = retrofitCustomCallback;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if(response.isSuccessful() && response.body() != null) {
            retrofitCustomCallback.onResponse(call, response);
        } else if(response.code() == 403) {
            retrofitCustomCallback.onTokenRevoked();
        } else {
            retrofitCustomCallback.onError("Something went wrong !!!");
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable error) {
        String errorMessage;
        if (error instanceof IOException) {
            // Timeout
            errorMessage = String.valueOf(error.getCause());
        } else if (error instanceof IllegalStateException) {
            // ConversionError
            errorMessage = String.valueOf(error.getCause());
        } else {
            // Other Error
            errorMessage = String.valueOf(error.getLocalizedMessage());
        }
        retrofitCustomCallback.onFailure(errorMessage);
    }

    public interface RetrofitCustomCallback<T> {
        void onResponse(Call<T> call, Response<T> response);

        void onFailure(String errorMessage);

        void onError(String errorMessage);

        void onTokenRevoked();
    }

}
