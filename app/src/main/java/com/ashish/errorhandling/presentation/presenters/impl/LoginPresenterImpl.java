package com.ashish.errorhandling.presentation.presenters.impl;

import com.ashish.errorhandling.domain.executor.Executor;
import com.ashish.errorhandling.domain.executor.MainThread;
import com.ashish.errorhandling.domain.interactors.LoginInteractor;
import com.ashish.errorhandling.domain.repository.UserRepository;
import com.ashish.errorhandling.presentation.presenters.LoginPresenter;
import com.ashish.errorhandling.presentation.presenters.base.AbstractPresenter;

/**
 * @author ashish
 * @since 28/02/18
 */
public class LoginPresenterImpl extends AbstractPresenter implements LoginPresenter, LoginInteractor.Callback {

    private View view;
    private UserRepository userRepository;

    public LoginPresenterImpl(Executor executor, MainThread mainThread, View view,
                              UserRepository userRepository) {
        super(executor, mainThread);

        this.view = view;
        this.userRepository = userRepository;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void onError(String message) {
        view.hideProgress();
        view.showError(message);
    }

    @Override
    public void onSuccess() {
        view.hideProgress();
        view.startHomeActivity();
    }

    @Override
    public void onLoginClicked(String username, String password) {
        view.showProgress();

    }
}
