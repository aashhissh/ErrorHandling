package com.ashish.errorhandling.presentation.converter;

import com.ashish.errorhandling.domain.model.LastLoginDetailsModel;
import com.ashish.errorhandling.presentation.models.WelcomeModel;

/**
 * @author ashish
 * @since 28/02/18
 */
public class LastLoginToWelcomeConverter {

    public static WelcomeModel convertLastLoginModelToWelcomeModel(LastLoginDetailsModel lastLoginDetailsModel) {
        return new WelcomeModel(
                lastLoginDetailsModel.getUserName(),
                lastLoginDetailsModel.getLastLoginDate(),
                lastLoginDetailsModel.getDevice()
        );
    }

}
