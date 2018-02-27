package com.ashish.errorhandling.commons;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

/**
 * @author ashish
 * @since 28/02/18
 */
public class ProgressDialog extends DialogFragment {
    private static final String DIALOG_TAG = "uc_progress_dialog";
    private static final String KEY_DIALOG_MESSAGE = "uc_dialog_message";
    private static final String KEY_DIALOG_TITLE = "uc_dialog_title";
    private static final String KEY_DIALOG_CANCELABLE = "uc_dialog_cancelable";

    /**
     * Create basic non-cancelable progress dialog
     *
     * @param activity target activity
     * @param message  message to be displayed for progress
     */
    public static void createDialog(FragmentActivity activity, String message) {
        createDialog(activity, message, false);
    }

    /**
     * Create basic progress dialog
     *
     * @param activity   target activity
     * @param message    message to be displayed
     * @param cancelable set dialog to be cancellable ot not
     */
    public static void createDialog(FragmentActivity activity, String message,
                                    boolean cancelable) {
        createDialog(activity, message, null, cancelable);
    }

    /**
     * Create basic progress dialog
     *
     * @param activity   target activity
     * @param message    message to be displayed
     * @param title      title to be displayed on dialog
     * @param cancelable set dialog to be cancellable ot not
     */
    public static void createDialog(FragmentActivity activity, String message, String title,
                                    boolean cancelable) {
        ProgressDialog frag = new ProgressDialog();
        Bundle args = new Bundle();
        args.putString(KEY_DIALOG_MESSAGE, message);
        args.putString(KEY_DIALOG_TITLE, title);
        args.putBoolean(KEY_DIALOG_CANCELABLE, cancelable);
        frag.setArguments(args);
        FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
        ft.add(frag, DIALOG_TAG);
        ft.commitAllowingStateLoss();
    }

    /**
     * Remove previously created dialog
     *
     * @param activity target activity using which previous dialog was created
     */
    public static void removeDialog(FragmentActivity activity) {
        if (null == activity) {
            return;
        }
        Fragment frag = activity.getSupportFragmentManager().findFragmentByTag(DIALOG_TAG);
        if (frag != null && frag instanceof ProgressDialog) {
            ((ProgressDialog) frag).dismissAllowingStateLoss();
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        String message = arguments.getString(KEY_DIALOG_MESSAGE);
        String title = arguments.getString(KEY_DIALOG_TITLE);
        boolean cancelable = arguments.getBoolean(KEY_DIALOG_CANCELABLE);
        android.app.ProgressDialog dialog = new android.app.ProgressDialog(getContext());
        dialog.setTitle(title);
        dialog.setMessage(message);
        setCancelable(cancelable);
        return dialog;
    }
}
