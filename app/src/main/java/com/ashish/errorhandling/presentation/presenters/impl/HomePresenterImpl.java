package com.ashish.errorhandling.presentation.presenters.impl;

import com.ashish.errorhandling.domain.executor.Executor;
import com.ashish.errorhandling.domain.executor.MainThread;
import com.ashish.errorhandling.domain.interactors.LastLoginInteractor;
import com.ashish.errorhandling.domain.interactors.impl.LastLoginInteractorImpl;
import com.ashish.errorhandling.domain.model.LastLoginDetailsModel;
import com.ashish.errorhandling.domain.repository.UserRepository;
import com.ashish.errorhandling.presentation.converter.LastLoginToWelcomeConverter;
import com.ashish.errorhandling.presentation.models.WelcomeModel;
import com.ashish.errorhandling.presentation.presenters.HomePresenter;
import com.ashish.errorhandling.presentation.presenters.base.AbstractPresenter;

/**
 * @author ashish
 * @since 28/02/18
 */
public class HomePresenterImpl extends AbstractPresenter implements HomePresenter, LastLoginInteractor.Callback {

    private HomePresenter.View view;
    private UserRepository userRepository;

    public HomePresenterImpl(Executor executor, MainThread mainThread, HomePresenter.View view,
                             UserRepository userRepository) {
        super(executor, mainThread);

        this.view = view;
        this.userRepository = userRepository;
    }

    @Override
    public void resume() {
        getWelComeData();
    }

    private void getWelComeData() {
        LastLoginInteractor lastLoginInteractor = new LastLoginInteractorImpl(
                mExecutor,
                mMainThread,
                userRepository,
                this
        );
        lastLoginInteractor.execute();
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
    public void onLastLoginDataReceived(LastLoginDetailsModel lastLoginDetailsModel) {
        WelcomeModel welcomeModel = LastLoginToWelcomeConverter.convertLastLoginModelToWelcomeModel(
                lastLoginDetailsModel
        );

        view.updateWelcomeMessage(
                welcomeModel
        );
    }

    @Override
    public void onError(String message) {
        view.showError(message);
    }

    @Override
    public void onTokenRevoked() {
        view.onTokenRevoked();
    }

    @Override
    public void onNoUserDataFound() {
        view.onTokenRevoked();
    }
}
