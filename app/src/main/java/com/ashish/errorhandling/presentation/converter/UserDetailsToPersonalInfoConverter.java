package com.ashish.errorhandling.presentation.converter;

import com.ashish.errorhandling.domain.model.UserDetailsModel;
import com.ashish.errorhandling.presentation.models.PersonalDetailsModel;

/**
 * @author ashish
 * @since 28/02/18
 */
public class UserDetailsToPersonalInfoConverter {

    public static PersonalDetailsModel convertToPersonalDetailsModel(UserDetailsModel userDetailsModel) {
        return new PersonalDetailsModel(
                userDetailsModel.getUserName(),
                userDetailsModel.getAge(),
                userDetailsModel.getGender(),
                userDetailsModel.getAddress(),
                userDetailsModel.getBloodGroup(),
                userDetailsModel.getHeight(),
                userDetailsModel.getWeight()
        );
    }

}
