package com.ashish.errorhandling.utils;

import android.content.Context;
import android.content.Intent;

import com.ashish.errorhandling.presentation.ui.activities.LoginActivity;
import com.ashish.errorhandling.presentation.ui.activities.PersonalDetailsActivity;

/**
 * @author ashish
 * @since 28/02/18
 */
public class NavigationUtil {

    /**
     * Starts PersonalDetailsActivity
     *
     * @param context context to start new activity and creating intent
     */

    public static void startPersonalDetailsActivity(Context context) {
        context.startActivity(getIntentForPersonalDetailsActivity(context));
    }

    private static Intent getIntentForPersonalDetailsActivity(Context context) {
        return new Intent(context, PersonalDetailsActivity.class);
    }

    //----------------------------------


    /**
     * Starts LoginActivity
     *
     * @param context context to start new activity and creating intent
     */

    public static void startLoginActivity(Context context) {
        context.startActivity(getIntentForLoginActivity(context));
    }

    private static Intent getIntentForLoginActivity(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    //----------------------------------

}
