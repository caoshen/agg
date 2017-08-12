package xyz.dcme.library.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class DeviceUtils {

    private DeviceUtils() {

    }

    public static void showSoftKeyboard(View view) {
        if (view == null) {
            return;
        }
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        if (!view.isFocused()) {
            view.requestFocus();
        }

        Context context = view.getContext();
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, 0);
    }

    public static void hideSoftKeyboard(View view) {
        if (view == null) {
            return;
        }
        Context context = view.getContext();
        if (context == null || !(context instanceof Activity)) {
            return;
        }

        View currentFocus = ((Activity) context).getCurrentFocus();
        if (currentFocus == null) {
            return;
        }
        currentFocus.clearFocus();

        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(currentFocus.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static void closeKeyboard(EditText view) {
        view.clearFocus();
        Context context = view.getContext();
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
    }
}
