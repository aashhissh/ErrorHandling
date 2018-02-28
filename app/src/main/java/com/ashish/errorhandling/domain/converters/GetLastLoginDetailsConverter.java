package com.ashish.errorhandling.domain.converters;

import com.ashish.errorhandling.domain.model.LastLoginDetailsModel;
import com.ashish.errorhandling.network.response.GetLastLoginDetailsResponse;

/**
 * @author ashish
 * @since 28/02/18
 */
public class GetLastLoginDetailsConverter {

    public static LastLoginDetailsModel convertGetLastLoginDetailsResponseToLastLoginDetailsModel(
            GetLastLoginDetailsResponse getLastLoginDetailsResponse) {
        return new LastLoginDetailsModel(
                getLastLoginDetailsResponse.getUserId(),
                getLastLoginDetailsResponse.getUserName(),
                getLastLoginDetailsResponse.getLastLoginDate(),
                getLastLoginDetailsResponse.getDevice()
        );
    }

}
