package com.atc.gosmartlesmagistra.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.atc.gosmartlesmagistra.App;
import com.atc.gosmartlesmagistra.R;
import com.atc.gosmartlesmagistra.adapter.PagerAdapter;
import com.atc.gosmartlesmagistra.api.CourseApi;
import com.atc.gosmartlesmagistra.api.UserApi;
import com.atc.gosmartlesmagistra.model.User;
import com.atc.gosmartlesmagistra.model.response.CourseLevelSpinnerSuccess;
import com.atc.gosmartlesmagistra.model.response.LoginSuccess;
import com.atc.gosmartlesmagistra.util.DatabaseHelper;
import com.atc.gosmartlesmagistra.util.SessionManager;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TeacherProfileActivity extends AppCompatActivity {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.action_left) ImageButton actionLeft;
    @BindView(R.id.fab) FloatingActionButton fab;
    @BindView(R.id.pager) ViewPager viewPager;
    @BindView(R.id.tab_layout) TabLayout tabLayout;
    @BindView(R.id.drawer_layout) DrawerLayout drawer;

    SessionManager sessionManager;
    PagerAdapter pagerAdapter;
    DatabaseHelper databaseHelper;
    public static User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_profile);
        ButterKnife.bind(this);
        setActionLeftIcon();

        sessionManager = new SessionManager(this);
        databaseHelper = new DatabaseHelper(this);

        final Drawable iconFab = ContextCompat.getDrawable(this, R.drawable.zzz_book_plus);
        iconFab.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        fab.setImageDrawable(iconFab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UpdateTeacherCourseActivity.class);
                intent.putExtra("isNewRecord", true);
                startActivity(intent);
            }
        });

        setTabLayout();

        requestApi();

        Boolean setToCourse = getIntent().getBooleanExtra("setToCourse", false);
        if (setToCourse) {
            viewPager.setCurrentItem(1, true);
        }
    }

    protected void requestApi() {
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request newRequest  = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer " + sessionManager.getUserToken())
                        .addHeader("Content-Type", "application/json")
                        .build();
                return chain.proceed(newRequest);
            }
        }).build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(App.API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UserApi service = retrofit.create(UserApi.class);
        Call<LoginSuccess> call = service.getUserByUniqueNumber(sessionManager.getUserCode());
        call.enqueue(new Callback<LoginSuccess>() {
            @Override
            public void onResponse(Call<LoginSuccess> call, Response<LoginSuccess> response) {

                if (response.raw().isSuccessful()) {
                    user = response.body().getUser();
                }
            }

            @Override
            public void onFailure(Call<LoginSuccess> call, Throwable t) {

            }
        });
    }

    public static User getUser() {
        return user;
    }

    private void setActionLeftIcon() {
        final Drawable iconLeft = ContextCompat.getDrawable(this, R.drawable.zzz_arrow_left);
        iconLeft.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        actionLeft.setImageDrawable(iconLeft);
    }

    private void setTabLayout() {
        tabLayout.addTab(tabLayout.newTab().setText("info"));
        tabLayout.addTab(tabLayout.newTab().setText("courses"));
        tabLayout.addTab(tabLayout.newTab().setText("histories"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabTextColors(getResources().getColor(R.color.colorBlack), getResources().getColor(R.color.colorAccent));

        pagerAdapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                switch (tab.getPosition()) {
                    case 0:
                    case 1:
                        final Drawable iconFab = ContextCompat.getDrawable(getApplicationContext(), R.drawable.zzz_book_plus);
                        iconFab.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                        fab.setImageDrawable(iconFab);
                        fab.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getApplicationContext(), UpdateTeacherCourseActivity.class);
                                intent.putExtra("isNewRecord", true);
                                startActivity(intent);
                            }
                        });
                        break;
                    case 2:
                        final Drawable iconFab2 = ContextCompat.getDrawable(getApplicationContext(), R.drawable.zzz_plus_circle);
                        iconFab2.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                        fab.setImageDrawable(iconFab2);
                        fab.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), "Request Honor page is not available", Toast.LENGTH_SHORT).show();
//                                Intent intent = new Intent(getApplicationContext(), UpdateTeacherCourseActivity.class);
//                                intent.putExtra("isNewRecord", true);
//                                startActivity(intent);
                            }
                        });
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        loadCourseLevels();
    }

    private void loadCourseLevels() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(App.API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CourseApi service = retrofit.create(CourseApi.class);
        Call<CourseLevelSpinnerSuccess> call = service.courseLevelSpinners();
        call.enqueue(new Callback<CourseLevelSpinnerSuccess>() {
            @Override
            public void onResponse(Call<CourseLevelSpinnerSuccess> call, Response<CourseLevelSpinnerSuccess> response) {

                if (response.raw().isSuccessful()) {
                    databaseHelper.createCourseLevel(response.body());
                }
            }

            @Override
            public void onFailure(Call<CourseLevelSpinnerSuccess> call, Throwable t) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.action_left)
    public void doActionLeft(View view) {
        onBackPressed();
    }
}
