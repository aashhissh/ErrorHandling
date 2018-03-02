package com.ashish.errorhandling.network.interceptors;

import com.ashish.errorhandling.AppConstants;
import com.ashish.errorhandling.utils.CodeUtil;
import com.ashish.errorhandling.utils.PrefUtil;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * @author ashish
 * @since 02/03/18
 */
public class ReceivedCookieInterceptor implements Interceptor {

    private final String KEY_SET_COOKIE = "Set-Cookie";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());

        if(!response.headers(KEY_SET_COOKIE).isEmpty()) {
            HashSet<String> oldCookiesSet = PrefUtil.getStringSet(AppConstants.KEY_COOKIES);
            List<String> responseCookiesList = response.headers(KEY_SET_COOKIE);

            oldCookiesSet.addAll(responseCookiesList);

            PrefUtil.setStringSet(AppConstants.KEY_COOKIES, oldCookiesSet);
        }

        return response;
    }

}
