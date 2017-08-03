package com.atc.gosmartlesmagistra.activity;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.atc.gosmartlesmagistra.App;
import com.atc.gosmartlesmagistra.R;
import com.atc.gosmartlesmagistra.adapter.CourseAvailabilityListAdapter;
import com.atc.gosmartlesmagistra.adapter.SimiliarTeacherCourseListAdapter;
import com.atc.gosmartlesmagistra.api.CourseApi;
import com.atc.gosmartlesmagistra.model.Course;
import com.atc.gosmartlesmagistra.model.TeacherCourse;
import com.atc.gosmartlesmagistra.model.User;
import com.atc.gosmartlesmagistra.model.response.CourseAvailabilitiesSuccess;
import com.atc.gosmartlesmagistra.util.SessionManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

/**
 * Created by hendrigunawan on 7/4/17.
 */

public class TeacherCourseDetailActivity extends AppCompatActivity {

    @BindView(R.id.action_left) ImageButton actionLeft;
    @BindView(R.id.title_bar) TextView titleBar;
    @BindView(R.id.course_level) TextView courseLevel;
    @BindView(R.id.course_name) TextView courseName;
    @BindView(R.id.course_section) TextView courseSection;
    @BindView(R.id.course_description) TextView courseDescription;
    @BindView(R.id.username) TextView username;
    @BindView(R.id.phone) TextView phone;
    @BindView(R.id.address) TextView address;
    @BindView(R.id.price) TextView price;
    @BindView(R.id.similiar_course_recycler_view) RecyclerView similiarCourseRecyclerView;
    @BindView(R.id.progress_bar) ProgressBar progressBar;

    SessionManager sessionManager;
    Retrofit retrofit;
    Course course;
    List<TeacherCourse> teacherCourseList;
    TeacherCourse teacherCourse;
    User user;
    SimiliarTeacherCourseListAdapter similiarTeacherCourseListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_course_detail);
        ButterKnife.bind(this);
        setActionLeftIcon();

        sessionManager = new SessionManager(this);
        teacherCourse = (TeacherCourse) getIntent().getSerializableExtra("teacherCourse");
        course = teacherCourse.getCourse();
        user = teacherCourse.getUser();

        titleBar.setText(user.getFullName());
        courseName.setText(course.getName());
        courseLevel.setText(course.getCourseLevel().getName());
        courseDescription.setText(teacherCourse.getDescription());
        courseSection.setText(course.getSection() + " " + getString(R.string.section) + ", " + course.getSectionTime() + " " + getString(R.string.time));
        address.setText(user.getAddress());
        phone.setText(user.getPhoneNumber());
        username.setText(user.getFullName());
        price.setText(teacherCourse.getFormattedFinalCost());

        teacherCourseList = new ArrayList<>();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        similiarCourseRecyclerView.setLayoutManager(layoutManager);
        similiarCourseRecyclerView.setNestedScrollingEnabled(false);
        similiarCourseRecyclerView.setVerticalScrollBarEnabled(true);
        similiarTeacherCourseListAdapter = new SimiliarTeacherCourseListAdapter(this, teacherCourseList);
        similiarCourseRecyclerView.setAdapter(similiarTeacherCourseListAdapter);

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
        retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(App.API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        loadSimiliarCourse();
    }

    private void loadSimiliarCourse() {
        progressBar.setVisibility(View.VISIBLE);
        CourseApi courseApi = retrofit.create(CourseApi.class);

        Call<CourseAvailabilitiesSuccess> call = courseApi.similiarTeacherCourses(teacherCourse.getId());
        call.enqueue(new Callback<CourseAvailabilitiesSuccess>() {
            @Override
            public void onResponse(Call<CourseAvailabilitiesSuccess> call, Response<CourseAvailabilitiesSuccess> response) {
                if(response.raw().isSuccessful()) {
                    teacherCourseList.clear();
                    teacherCourseList.addAll(response.body().getTeacherCourses());
                    similiarTeacherCourseListAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string.request_failed_please_try_again), Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<CourseAvailabilitiesSuccess> call, Throwable t) {
                Toast.makeText(getApplicationContext(), getString(R.string.request_failed_please_try_again), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void setActionLeftIcon() {
        final Drawable iconLeft = ContextCompat.getDrawable(this, R.drawable.zzz_arrow_left);
        iconLeft.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        actionLeft.setImageDrawable(iconLeft);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @OnClick(R.id.action_left)
    public void doActionLeft(View view) {
        onBackPressed();
    }
}
