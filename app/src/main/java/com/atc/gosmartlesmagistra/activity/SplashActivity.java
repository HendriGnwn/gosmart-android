package com.atc.gosmartlesmagistra.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;

import com.atc.gosmartlesmagistra.R;
import com.atc.gosmartlesmagistra.util.SessionManager;

import java.util.Locale;

/**
 * Created by hendrigunawan on 7/4/17.
 */

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 1000;
    private static final String TAG = SplashActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Resources resources = getResources();
        Configuration configuration = resources.getConfiguration();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        Locale locale = new Locale("id", "ID");
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration,displayMetrics);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent;

                SessionManager sessionManager = new SessionManager(SplashActivity.this);
                if (sessionManager.getIntroFirstApp()) {
                    intent = new Intent(SplashActivity.this, LoginActivity.class);
                } else {
                    intent = new Intent(SplashActivity.this, IntroActivity.class);
                    sessionManager.setIntroFirstApp(true);
                }

                startActivity(intent);
                overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
                finish();
            }
        }, SPLASH_TIME_OUT);

    }

}
