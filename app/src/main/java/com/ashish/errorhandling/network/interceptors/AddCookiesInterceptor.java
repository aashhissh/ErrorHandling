package com.ashish.errorhandling.network.interceptors;

import com.ashish.errorhandling.AppConstants;
import com.ashish.errorhandling.utils.PrefUtil;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author ashish
 * @since 02/03/18
 *
 * This interceptor put all the Cookies in Preferences in the Request.
 */
public class AddCookiesInterceptor implements Interceptor {

    private final String KEY_HEADER_COOKIE = "Cookie";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();

        HashSet<String> cookiesSet = PrefUtil.getStringSet(AppConstants.KEY_COOKIES);

        for(String cookie : cookiesSet) {
            builder.addHeader(KEY_HEADER_COOKIE, cookie);
        }

        return chain.proceed(builder.build());
    }

}
