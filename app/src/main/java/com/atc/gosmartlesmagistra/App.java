package com.atc.gosmartlesmagistra;


import android.app.Activity;
import android.app.Application;
import android.graphics.Bitmap;
import android.util.Base64;
import android.view.inputmethod.InputMethodManager;

import java.io.ByteArrayOutputStream;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by hendrigunawan on 7/4/17.
 */

public class App extends Application {

    public static String API = "http://sushitei.cranium.id/api/v1/";
    public static String URL = "http://sushitei.cranium.id/";

    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/latoRegular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

    }

    public static String getGoogleApiKey() {
        return "";
    }

    public static String encodeTobase64(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }
}