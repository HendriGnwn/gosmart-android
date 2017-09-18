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
    private static final String KEY_IS_INTRO = "appIntro";
    private static final String KEY_IS_LOGGEDIN = "isLoggedIn";
    private static final String KEY_USER_TOKEN = "getToken";
    private static final String KEY_USER_CODE = "getUserCode";
    private static final String KEY_USER_ROLE = "getUserRole";
    private static final String KEY_HAVE_AN_ORDER = "getHaveAnOrder";

    public SessionManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setLogin(boolean isLoggedIn, User user) {

        editor.putBoolean(KEY_IS_LOGGEDIN, isLoggedIn);
        editor.putString(KEY_USER_TOKEN, user.getToken());
        editor.putString(KEY_USER_CODE, user.getUniqueNumber());
        editor.putString(KEY_USER_ROLE, user.getRole().toString());
        editor.commit();
    }

    public void setLogout(){
        editor.clear();
        editor.commit();
    }

    public boolean isLoggedIn(){
        return pref.getBoolean(KEY_IS_LOGGEDIN, false);
    }

    public boolean getIntroFirstApp(){
        return pref.getBoolean(KEY_IS_INTRO, false);
    }

    public void setIntroFirstApp(boolean value) {
        editor.putBoolean(KEY_IS_INTRO, value);
        editor.commit();
    }

    public String getUserToken(){
        return pref.getString(KEY_USER_TOKEN, null);
    }

    public String getKeyUserRole(){
        return pref.getString(KEY_USER_ROLE, null);
    }

    public String getUserCode(){
        return pref.getString(KEY_USER_CODE, null);
    }

    public boolean getHaveAnOrder(){
        return pref.getBoolean(KEY_HAVE_AN_ORDER, false);
    }

    public void setKeyHaveAnOrder(boolean set){
        editor.putBoolean(KEY_HAVE_AN_ORDER, set);
        editor.commit();
    }
}
