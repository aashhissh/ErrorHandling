package com.ashish.errorhandling.presentation.presenters;

import com.ashish.errorhandling.presentation.models.PersonalDetailsModel;
import com.ashish.errorhandling.presentation.presenters.base.BasePresenter;
import com.ashish.errorhandling.presentation.ui.BaseView;

/**
 * @author ashish
 * @since 28/02/18
 */
public interface PersonalDetailsPresenter extends BasePresenter {

    interface View extends BaseView {
        void updatePersonalInformation(PersonalDetailsModel personalDetailsModel);

        void onTokenRevoked();
    }

}
