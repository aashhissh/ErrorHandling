package com.ashish.errorhandling.domain.interactors.impl;

import com.ashish.errorhandling.domain.converters.GetLastLoginDetailsConverter;
import com.ashish.errorhandling.domain.executor.Executor;
import com.ashish.errorhandling.domain.executor.MainThread;
import com.ashish.errorhandling.domain.interactors.LastLoginInteractor;
import com.ashish.errorhandling.domain.interactors.base.AbstractInteractor;
import com.ashish.errorhandling.domain.repository.UserRepository;
import com.ashish.errorhandling.network.CustomCallback;
import com.ashish.errorhandling.network.RestClient;
import com.ashish.errorhandling.network.payload.GetLastLoginDetailsPayload;
import com.ashish.errorhandling.network.response.GetLastLoginDetailsResponse;
import com.ashish.errorhandling.network.services.ErrorHandlingRestService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author ashish
 * @since 28/02/18
 */
public class LastLoginInteractorImpl extends AbstractInteractor implements LastLoginInteractor {

    private UserRepository userRepository;
    private Callback       callback;

    public LastLoginInteractorImpl(Executor threadExecutor, MainThread mainThread,
                                   UserRepository userRepository, Callback callback) {
        super(threadExecutor, mainThread);

        if(userRepository == null || callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        this.userRepository = userRepository;
        this.callback = callback;
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
            GetLastLoginDetailsPayload getLastLoginDetailsPayload = new GetLastLoginDetailsPayload();

            errorHandlingRestService.getLastLoginDetails(getLastLoginDetailsPayload)
                    .enqueue(new CustomCallback<GetLastLoginDetailsResponse>(new CustomCallback.RetrofitCustomCallback<GetLastLoginDetailsResponse>() {
                        @Override
                        public void onResponse(Call<GetLastLoginDetailsResponse> call, Response<GetLastLoginDetailsResponse> response) {
                            final GetLastLoginDetailsResponse getLastLoginDetailsResponse = response.body();
                            if(getLastLoginDetailsResponse != null &&
                                    getLastLoginDetailsResponse.getStatus() == 1) {
                                userRepository.saveUserId(getLastLoginDetailsResponse.getUserId());
                                mMainThread.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        callback.onLastLoginDataReceived(
                                                GetLastLoginDetailsConverter.convertGetLastLoginDetailsResponseToLastLoginDetailsModel(
                                                        getLastLoginDetailsResponse
                                                )
                                        );
                                    }
                                });
                            } else {
                                String message = getLastLoginDetailsResponse != null ?
                                        getLastLoginDetailsResponse.getMsg() :
                                        "Something went wrong !!!";
                                onResponseError(message);
                            }
                        }

                        @Override
                        public void onFailure(String errorMessage) {
                            onResponseError(errorMessage);
                        }

                        @Override
                        public void onError(String errorMessage) {
                            onResponseError(errorMessage);
                        }

                        @Override
                        public void onTokenRevoked() {
                            mMainThread.post(new Runnable() {
                                @Override
                                public void run() {
                                    callback.onTokenRevoked();
                                }
                            });
                        }
                    }));
        }
    }

    private void onResponseError(final String errorMessage) {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onError(errorMessage);
            }
        });
    }

}
