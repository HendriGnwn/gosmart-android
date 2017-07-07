
package com.atc.gosmartlesmagistra.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.atc.gosmartlesmagistra.model.User;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by hendrigunawan on 7/4/17.
 */

public class SessionManager {

    private static final String KEY_ID_ADMIN = "admin";

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;

    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "ATCIndonesia";
    private static final String KEY_IS_LOGGEDIN = "isLoggedIn";
    private static final String KEY_USER_TOKEN = "getToken";
    private static final String KEY_USER_CODE = "getUserCode";

    public SessionManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setLogin(boolean isLoggedIn, User user) {

        editor.putBoolean(KEY_IS_LOGGEDIN, isLoggedIn);
        editor.putString(KEY_USER_TOKEN, user.getToken());
        editor.putString(KEY_USER_CODE, user.getUniqueNumber());
        editor.commit();
    }

    public void setLogout(){
        editor.clear();
        editor.commit();
    }

    public boolean isLoggedIn(){
        return pref.getBoolean(KEY_IS_LOGGEDIN, false);
    }

    public String getUserToken(){
        return pref.getString(KEY_USER_TOKEN, null);
    }

    public String getUserCode(){
        return pref.getString(KEY_USER_CODE, null);
    }
}
