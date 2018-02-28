package com.ashish.errorhandling.network;

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
        OkHttpClient client = new OkHttpClient.Builder()
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
