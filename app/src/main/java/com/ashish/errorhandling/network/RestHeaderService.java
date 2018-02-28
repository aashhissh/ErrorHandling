package com.ashish.errorhandling.network;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ashish
 * @since 28/02/18
 */
public class RestHeaderService {

    private static final String KEY_HEADER = "Bearer: ";

    public static Map<String, String> getRequestHeader(String token) {
        Map<String, String> header = new HashMap<>();
        header.put(KEY_HEADER, token);
        return header;
    }

}
