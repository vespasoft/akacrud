package com.akacrud.view.util;

import android.app.Activity;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import java.sql.Timestamp;

/**
 * Created by luisvespa on 12/13/17.
 * This class content common utilities to use in the application
 */

public class CommonUtils {

    // This method return the current date and time
    public static Timestamp getCurrentTimeStamp() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return timestamp;
    }

    // This method hide the keyboard of mobile
    public static void hideKeyBoard(Activity activity){
        try {
            InputMethodManager input = (InputMethodManager) activity
                    .getSystemService(Activity.INPUT_METHOD_SERVICE);
            input.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    // This method shows a custom snack bar
    public static void showSnackBar(Activity activity, View view, String Message) {
        hideKeyBoard(activity);

        Snackbar snackbar = Snackbar.make(view, Message, Snackbar.LENGTH_LONG);
        // Changing action button text color
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snackbar.show();
    }

}
