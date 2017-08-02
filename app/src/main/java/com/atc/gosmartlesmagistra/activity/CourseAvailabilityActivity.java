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
import com.atc.gosmartlesmagistra.adapter.CourseListAdapter;
import com.atc.gosmartlesmagistra.api.CourseApi;
import com.atc.gosmartlesmagistra.model.Course;
import com.atc.gosmartlesmagistra.model.TeacherCourse;
import com.atc.gosmartlesmagistra.model.response.CourseAvailabilitiesSuccess;
import com.atc.gosmartlesmagistra.model.response.CoursesSuccess;
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

public class CourseAvailabilityActivity extends AppCompatActivity {

    @BindView(R.id.action_left) ImageButton actionLeft;
    @BindView(R.id.courses_recycler_view) RecyclerView coursesRecyclerView;
    @BindView(R.id.progress_bar) ProgressBar progressBar;
    @BindView(R.id.swipeContainer) SwipeRefreshLayout swipeContainer;
    @BindView(R.id.course_level) TextView courseLevel;
    @BindView(R.id.course_name) TextView courseName;
    @BindView(R.id.course_description) TextView courseDescription;
    @BindView(R.id.course_section) TextView courseSection;
    @BindView(R.id.sort_by_spinner) Spinner sortBySpinner;

    SessionManager sessionManager;
    CourseAvailabilityListAdapter courseListAdapter;
    List<TeacherCourse> courseList;
    Retrofit retrofit;
    Course course;
    String[] sorts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_availability);
        ButterKnife.bind(this);
        setActionLeftIcon();

        sessionManager = new SessionManager(this);
        course = (Course) getIntent().getSerializableExtra("course");
        courseName.setText(course.getName());
        courseLevel.setText(course.getCourseLevel().getName());
        courseSection.setText(course.getSection() + " " + getString(R.string.section) + ", " + course.getSectionTime() + " " + getString(R.string.time));
        courseDescription.setText(course.getDescription());
        sorts = getResources().getStringArray(R.array.sort_arrays);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this, R.layout.spinner_item, sorts
        );
        sortBySpinner.setAdapter(spinnerArrayAdapter);

        courseList = new ArrayList<>();
        courseListAdapter = new CourseAvailabilityListAdapter(this, courseList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        coursesRecyclerView.setLayoutManager(layoutManager);
        coursesRecyclerView.setNestedScrollingEnabled(false);
        coursesRecyclerView.setHorizontalScrollBarEnabled(true);
        coursesRecyclerView.setItemAnimator(new DefaultItemAnimator());
        coursesRecyclerView.setAdapter(courseListAdapter);

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

        swipeContainer.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadCourses();
            }
        });
        loadCourses();
    }

    private void loadCourses() {
        CourseApi courseApi = retrofit.create(CourseApi.class);
        String searchName = null;

        Call<CourseAvailabilitiesSuccess> call = courseApi.courseAvailabilities(1,searchName,null,null,null);
        call.enqueue(new Callback<CourseAvailabilitiesSuccess>() {
            @Override
            public void onResponse(Call<CourseAvailabilitiesSuccess> call, Response<CourseAvailabilitiesSuccess> response) {
                if(response.raw().isSuccessful()) {
                    courseList.clear();
                    courseList.addAll(response.body().getTeacherCourses());
                    courseListAdapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.INVISIBLE);
                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string.request_failed_please_try_again), Toast.LENGTH_SHORT).show();
                }
                swipeContainer.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<CourseAvailabilitiesSuccess> call, Throwable t) {
                swipeContainer.setRefreshing(false);
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
