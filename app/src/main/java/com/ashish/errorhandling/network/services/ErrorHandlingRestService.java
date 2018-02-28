package com.ashish.errorhandling.network.services;

import com.ashish.errorhandling.network.payload.AuthenticatePayload;
import com.ashish.errorhandling.network.payload.GetLastLoginDetailsPayload;
import com.ashish.errorhandling.network.payload.GetUserDetailsPayload;
import com.ashish.errorhandling.network.response.AuthenticateResponse;
import com.ashish.errorhandling.network.response.GetLastLoginDetailsResponse;
import com.ashish.errorhandling.network.response.GetUserDetailsResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;

/**
 * @author ashish
 * @since 28/02/18
 *
 *  A REST interface describing how data will be synced with the backend.
 */
public interface ErrorHandlingRestService {

    @POST("authenticate/")
    Call<AuthenticateResponse> authenticate(
            @Body AuthenticatePayload authenticatePayload
    );

    @POST("get_last_login_ details/")
    Call<GetLastLoginDetailsResponse> getLastLoginDetails(
            @HeaderMap Map<String, String> headers,
            @Body GetLastLoginDetailsPayload getLastLoginDetailsPayload
    );

    @POST("user/get/")
    Call<GetUserDetailsResponse> getUserDetails(
            @HeaderMap Map<String, String> headers,
            @Body GetUserDetailsPayload getUserDetailsPayload
    );

}
