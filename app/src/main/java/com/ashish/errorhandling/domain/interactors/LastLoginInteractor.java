package com.ashish.errorhandling.domain.interactors;

import com.ashish.errorhandling.domain.interactors.base.Interactor;
import com.ashish.errorhandling.domain.model.LastLoginDetailsModel;

/**
 * @author ashish
 * @since 28/02/18
 */
public interface LastLoginInteractor extends Interactor {

    interface Callback {
        void onLastLoginDataReceived(LastLoginDetailsModel lastLoginDetailsModel);

        void onError(String message);

        void onTokenRevoked();

        void onNoUserDataFound();
    }

}
