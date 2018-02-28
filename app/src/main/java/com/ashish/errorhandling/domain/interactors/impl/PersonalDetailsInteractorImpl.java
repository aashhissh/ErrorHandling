package com.ashish.errorhandling.domain.interactors.impl;

import com.ashish.errorhandling.domain.converters.GetLastLoginDetailsConverter;
import com.ashish.errorhandling.domain.converters.GetUserDetailsModelConverter;
import com.ashish.errorhandling.domain.executor.Executor;
import com.ashish.errorhandling.domain.executor.MainThread;
import com.ashish.errorhandling.domain.interactors.PersonalDetailsInteractor;
import com.ashish.errorhandling.domain.interactors.base.AbstractInteractor;
import com.ashish.errorhandling.domain.repository.UserRepository;
import com.ashish.errorhandling.network.RestClient;
import com.ashish.errorhandling.network.RestHeaderService;
import com.ashish.errorhandling.network.payload.GetLastLoginDetailsPayload;
import com.ashish.errorhandling.network.payload.GetUserDetailsPayload;
import com.ashish.errorhandling.network.response.GetLastLoginDetailsResponse;
import com.ashish.errorhandling.network.response.GetUserDetailsResponse;
import com.ashish.errorhandling.network.services.ErrorHandlingRestService;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author ashish
 * @since 28/02/18
 */
public class PersonalDetailsInteractorImpl extends AbstractInteractor implements PersonalDetailsInteractor {

    private Callback callback;
    private UserRepository userRepository;

    public PersonalDetailsInteractorImpl(Executor threadExecutor, MainThread mainThread,
                                         Callback callback, UserRepository userRepository) {
        super(threadExecutor, mainThread);

        this.callback = callback;
        this.userRepository = userRepository;
    }

    @Override
    public void run() {
        String token = userRepository.getAuthToken();

        if(token == null || token.trim().equals("")) {
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    callback.onNoUserDataFound();
                }
            });
        } else {
            // initializing the REST service we will use
            ErrorHandlingRestService errorHandlingRestService = RestClient.getService(ErrorHandlingRestService.class);

            // initializing payload object for get user details
            GetUserDetailsPayload getUserDetailsPayload = new GetUserDetailsPayload(userRepository.getUserId());

            Map<String, String> header = RestHeaderService.getRequestHeader(token);

            errorHandlingRestService.getUserDetails(header, getUserDetailsPayload)
                    .enqueue(new retrofit2.Callback<GetUserDetailsResponse>() {
                        @Override
                        public void onResponse(Call<GetUserDetailsResponse> call, Response<GetUserDetailsResponse> response) {
                            final GetUserDetailsResponse getUserDetailsResponse = response.body();

                            if(response.isSuccessful() && getUserDetailsResponse != null &&
                                    getUserDetailsResponse.getStatus() == 1) {
                                mMainThread.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        callback.onUserDetailsDataReceived(
                                                GetUserDetailsModelConverter.convertToUserDetailsModel(
                                                        getUserDetailsResponse
                                                )
                                        );
                                    }
                                });
                            } else {
                                String message = getUserDetailsResponse != null &&
                                        getUserDetailsResponse.getMsg() != null ?
                                        getUserDetailsResponse.getMsg() :
                                        "Something went wrong !!!";
                                onError(message);
                            }
                        }

                        @Override
                        public void onFailure(Call<GetUserDetailsResponse> call, Throwable t) {
                            onError("Something went wrong !!!");
                        }
                    });
        }
    }

    private void onError(final String errorMessage) {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onError(errorMessage);
            }
        });
    }

}
