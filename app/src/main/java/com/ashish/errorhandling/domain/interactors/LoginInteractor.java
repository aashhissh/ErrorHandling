package com.ashish.errorhandling.domain.interactors;

import com.ashish.errorhandling.domain.interactors.base.Interactor;

/**
 * @author ashish
 * @since 28/02/18
 */
public interface LoginInteractor extends Interactor {

    interface Callback {
        void onError(String message);

        void onSuccess();
    }

}
