package com.ashish.errorhandling.domain.interactors;

import com.ashish.errorhandling.domain.interactors.base.Interactor;
import com.ashish.errorhandling.domain.model.LastLoginDetailsModel;
import com.ashish.errorhandling.domain.model.UserDetailsModel;

/**
 * @author ashish
 * @since 28/02/18
 */
public interface PersonalDetailsInteractor extends Interactor{

    interface Callback {
        void onUserDetailsDataReceived(UserDetailsModel userDetailsModel);

        void onError(String message);

        void onTokenRevoked();

        void onNoUserDataFound();
    }

}
