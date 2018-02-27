package com.ashish.errorhandling.domain.interactors.impl;

import com.ashish.errorhandling.domain.executor.Executor;
import com.ashish.errorhandling.domain.executor.MainThread;
import com.ashish.errorhandling.domain.interactors.LastLoginInteractor;
import com.ashish.errorhandling.domain.interactors.base.AbstractInteractor;
import com.ashish.errorhandling.domain.repository.UserRepository;

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
            // make network call to get the user data from server
        }
    }

}
