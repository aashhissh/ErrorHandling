package com.ashish.errorhandling.network;

import com.ashish.errorhandling.network.interceptors.AddCookiesInterceptor;
import com.ashish.errorhandling.network.interceptors.CustomResponseInterceptor;
import com.ashish.errorhandling.network.interceptors.ReceivedCookieInterceptor;
import com.ashish.errorhandling.storage.UserRepositoryImpl;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.ashish.errorhandling.AppConstants.BASE_URL;

/**
 * @author ashish
 * @since 28/02/18
 */
public class RestClient {

    private static RestClient instance;
    private static Retrofit s_retrofit;

    private RestClient() {
        CustomResponseInterceptor responseInterceptor = new CustomResponseInterceptor(new UserRepositoryImpl());
        AddCookiesInterceptor addCookiesInterceptor = new AddCookiesInterceptor();
        ReceivedCookieInterceptor receivedCookieInterceptor = new ReceivedCookieInterceptor();

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(addCookiesInterceptor)
                .addInterceptor(responseInterceptor)
                .addInterceptor(receivedCookieInterceptor)
                .build();

        s_retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
    }

    public static void init() {
        if(instance == null) {
            synchronized (RestClient.class) {
                if(instance == null) {
                    instance = new RestClient();
                }
            }
        }
    }

    public static <T> T getService(Class<T> serviceClass) {
        return s_retrofit.create(serviceClass);
    }

}
