package com.ashish.errorhandling.network.interceptors;

import android.util.Log;

import com.ashish.errorhandling.domain.repository.UserRepository;
import com.ashish.errorhandling.network.RestClient;
import com.ashish.errorhandling.network.payload.AuthenticatePayload;
import com.ashish.errorhandling.network.payload.GetLastLoginDetailsPayload;
import com.ashish.errorhandling.network.response.AuthenticateResponse;
import com.ashish.errorhandling.network.services.ErrorHandlingRestService;
import com.ashish.errorhandling.utils.PrefUtil;

import org.json.JSONException;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author ashish
 * @since 28/02/18
 */
public class CustomResponseInterceptor implements Interceptor {

    private UserRepository userRepository;

    public CustomResponseInterceptor(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        if (response.code() == 401) {
            Response r = null;
            try {
                r = makeTokenRefreshCall(request, chain);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return r;
        }
        return response;
    }

    private Response makeTokenRefreshCall(Request req, Chain chain) throws JSONException, IOException {
        /* fetch refreshed token, some synchronous API call, whatever */
        String newToken = fetchNewToken(userRepository.getAuthToken());

        // storing new token to repository
        userRepository.saveAuthToken(newToken);

        /* make a new request which is same as the original one, except that its headers now contain a refreshed token */
        Request newRequest = req.newBuilder().build();
        Response another =  chain.proceed(newRequest);
        while (another.code() == 401) {
            makeTokenRefreshCall(newRequest, chain);
        }
        return another;
    }

    private String fetchNewToken(String oldToken) {
        // initializing the REST service we will use
        ErrorHandlingRestService errorHandlingRestService = RestClient.getService(ErrorHandlingRestService.class);

        // initializing payload object for get user details
        AuthenticatePayload authenticatePayload = new AuthenticatePayload(oldToken);

        try {
            AuthenticateResponse authenticateResponse = errorHandlingRestService.authenticate(authenticatePayload).execute().body();
            if(authenticateResponse != null) {
                return authenticateResponse.getAccessToken();
            } else {
                return oldToken;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return oldToken;
        }
    }
}
