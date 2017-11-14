package ch.hearc.swissbox.tools;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by sylvain.renaud on 10.11.2017.
 */

public class UsefulTools {

    /**
     * https://stackoverflow.com/questions/26911469/hide-keyboard-when-navigating-from-a-fragment-to-another
     */
    public static void hideKeyboard(Context ctx) {
        InputMethodManager inputManager = (InputMethodManager) ctx
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        // check if no view has focus:
        View view = ((Activity) ctx).getCurrentFocus();
        if (view == null)
            return;

        inputManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}