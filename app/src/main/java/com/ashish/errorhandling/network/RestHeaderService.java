package com.ashish.errorhandling.network;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ashish
 * @since 28/02/18
 */
public class RestHeaderService {

    public static final String KEY_HEADER = "Authorization";

    public static Map<String, String> getRequestHeader(String token) {
        Map<String, String> header = new HashMap<>();
        header.put(KEY_HEADER, " Token " + token);
        return header;
    }

    public static String getTokenValue(String token) {
        return (" Token " + token);
    }

}
