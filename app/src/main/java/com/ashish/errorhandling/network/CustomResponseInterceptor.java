package com.ashish.errorhandling.network;

import android.util.Log;

import com.ashish.errorhandling.network.payload.AuthenticatePayload;
import com.ashish.errorhandling.network.payload.GetLastLoginDetailsPayload;
import com.ashish.errorhandling.network.response.AuthenticateResponse;
import com.ashish.errorhandling.network.services.ErrorHandlingRestService;

import org.json.JSONException;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import static com.ashish.errorhandling.network.RestHeaderService.KEY_HEADER;

/**
 * @author ashish
 * @since 28/02/18
 */
public class CustomResponseInterceptor implements Interceptor {

    private static String newToken;
    private String bodyString;

    private final String TAG = getClass().getSimpleName();

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        if (response.code() == 401) {
            Response r = null;
            try { r = makeTokenRefreshCall(request, chain); }
            catch (JSONException e) { e.printStackTrace(); }
            return r;
        }
        Log.d(TAG, "INTERCEPTED:$ " + response.toString());
        return response;
    }

    private Response makeTokenRefreshCall(Request req, Chain chain) throws JSONException, IOException {
        Log.d(TAG, "Retrying new request");
        /* fetch refreshed token, some synchronous API call, whatever */
        String newToken = fetchToken(req.header(KEY_HEADER));
        /* make a new request which is same as the original one, except that its headers now contain a refreshed token */
        Request newRequest;
        newRequest = req.newBuilder().header(KEY_HEADER, RestHeaderService.getTokenValue(newToken)).build();
        Response another =  chain.proceed(newRequest);
        while (another.code() == 401) {
            makeTokenRefreshCall(newRequest, chain);
        }
        return another;
    }

    private String fetchToken(String oldToken) {
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
