package com.ashish.errorhandling.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * @author ashish
 * @since 28/02/18
 */
public class CodeUtil {

    public static boolean isTextEmpty(String text) {
        return text == null || text.trim().equals("");
    }

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

}
