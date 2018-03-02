package com.ashish.errorhandling.domain.interactors.impl;

import android.text.TextUtils;

import com.ashish.errorhandling.domain.converters.GetUserDetailsModelConverter;
import com.ashish.errorhandling.domain.executor.Executor;
import com.ashish.errorhandling.domain.executor.MainThread;
import com.ashish.errorhandling.domain.interactors.PersonalDetailsInteractor;
import com.ashish.errorhandling.domain.interactors.base.AbstractInteractor;
import com.ashish.errorhandling.domain.repository.UserRepository;
import com.ashish.errorhandling.network.CustomCallback;
import com.ashish.errorhandling.network.RestClient;
import com.ashish.errorhandling.network.payload.GetUserDetailsPayload;
import com.ashish.errorhandling.network.response.AuthenticateResponse;
import com.ashish.errorhandling.network.response.GetUserDetailsResponse;
import com.ashish.errorhandling.network.services.ErrorHandlingRestService;

import java.io.IOException;

import retrofit2.Call;
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
            final GetUserDetailsPayload getUserDetailsPayload = new GetUserDetailsPayload(userRepository.getUserId());

            errorHandlingRestService.getUserDetails(getUserDetailsPayload)
                    .enqueue(new CustomCallback<GetUserDetailsResponse>(new CustomCallback.RetrofitCustomCallback<GetUserDetailsResponse>() {
                        @Override
                        public void onResponse(Call<GetUserDetailsResponse> call, Response<GetUserDetailsResponse> response) {
                            final GetUserDetailsResponse getUserDetailsResponse = response.body();
                            if(getUserDetailsResponse != null && getUserDetailsResponse.getStatus() == 1) {
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
                                String message = !TextUtils.isEmpty(getUserDetailsResponse.getMsg()) ?
                                        getUserDetailsResponse.getMsg() :
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
                                    userRepository.saveAuthToken(null);
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
