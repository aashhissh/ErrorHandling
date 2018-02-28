package com.ashish.errorhandling.domain.converters;

import com.ashish.errorhandling.domain.model.UserDetailsModel;
import com.ashish.errorhandling.network.response.GetUserDetailsResponse;

/**
 * @author ashish
 * @since 28/02/18
 */
public class GetUserDetailsModelConverter {

    public static UserDetailsModel convertToUserDetailsModel(GetUserDetailsResponse getUserDetailsResponse) {
        return new UserDetailsModel(
                getUserDetailsResponse.getUserName(),
                getUserDetailsResponse.getAge(),
                getUserDetailsResponse.getGender(),
                getUserDetailsResponse.getAddress(),
                getUserDetailsResponse.getBloodGroup(),
                getUserDetailsResponse.getHeight(),
                getUserDetailsResponse.getWeight()
        );
    }

}
