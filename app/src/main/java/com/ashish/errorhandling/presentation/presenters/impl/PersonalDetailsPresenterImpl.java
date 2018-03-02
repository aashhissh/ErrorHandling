package com.ashish.errorhandling.presentation.presenters.impl;

import com.ashish.errorhandling.domain.executor.Executor;
import com.ashish.errorhandling.domain.executor.MainThread;
import com.ashish.errorhandling.domain.interactors.PersonalDetailsInteractor;
import com.ashish.errorhandling.domain.interactors.impl.PersonalDetailsInteractorImpl;
import com.ashish.errorhandling.domain.model.UserDetailsModel;
import com.ashish.errorhandling.domain.repository.UserRepository;
import com.ashish.errorhandling.presentation.converter.UserDetailsToPersonalInfoConverter;
import com.ashish.errorhandling.presentation.presenters.PersonalDetailsPresenter;
import com.ashish.errorhandling.presentation.presenters.base.AbstractPresenter;

/**
 * @author ashish
 * @since 28/02/18
 */
public class PersonalDetailsPresenterImpl extends AbstractPresenter implements PersonalDetailsPresenter, PersonalDetailsInteractor.Callback {

    private View view;
    private UserRepository userRepository;

    public PersonalDetailsPresenterImpl(Executor executor, MainThread mainThread, View view,
                                        UserRepository userRepository) {
        super(executor, mainThread);

        this.view = view;
        this.userRepository = userRepository;
    }

    @Override
    public void resume() {
        getUsersPersonalDetails();
    }

    private void getUsersPersonalDetails() {
        view.showProgress();
        PersonalDetailsInteractor personalDetailsInteractor = new PersonalDetailsInteractorImpl(
                mExecutor,
                mMainThread,
                this,
                userRepository
        );
        personalDetailsInteractor.execute();
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
    public void onUserDetailsDataReceived(UserDetailsModel userDetailsModel) {
        view.hideProgress();
        view.updatePersonalInformation(UserDetailsToPersonalInfoConverter.convertToPersonalDetailsModel(userDetailsModel));
    }

    @Override
    public void onError(String message) {
        view.hideProgress();
    }

    @Override
    public void onTokenRevoked() {
        view.hideProgress();
        view.onTokenRevoked();
    }

    @Override
    public void onNoUserDataFound() {
        view.hideProgress();
        view.onTokenRevoked();
    }
}
