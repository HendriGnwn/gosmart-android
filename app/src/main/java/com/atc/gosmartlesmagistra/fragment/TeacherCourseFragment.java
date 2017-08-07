package com.atc.gosmartlesmagistra.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.atc.gosmartlesmagistra.App;
import com.atc.gosmartlesmagistra.R;
import com.atc.gosmartlesmagistra.adapter.TeacherProfileCourseListAdapter;
import com.atc.gosmartlesmagistra.model.TeacherCourse;
import com.atc.gosmartlesmagistra.model.User;
import com.atc.gosmartlesmagistra.util.DatabaseHelper;
import com.atc.gosmartlesmagistra.util.SessionManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by cranium-01 on 27/07/17.
 */

public class TeacherCourseFragment extends Fragment {

    @BindView(R.id.courses_recycler_view) RecyclerView coursesRecyclerView;
    @BindView(R.id.progress_bar) ProgressBar progressBar;
    @BindView(R.id.swipeContainer) SwipeRefreshLayout swipeContainer;

    private SessionManager sessionManager;
    TeacherProfileCourseListAdapter teacherProfileCourseListAdapter;
    List<TeacherCourse> courseList;
    Retrofit retrofit;
    DatabaseHelper databaseHelper;
    User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_teacher_course, container, false);
        ButterKnife.bind(this, view);

        sessionManager = new SessionManager(getContext());
        databaseHelper = new DatabaseHelper(getActivity());
        user = databaseHelper.getUser(sessionManager.getUserCode());
        courseList = new ArrayList<>();
        teacherProfileCourseListAdapter = new TeacherProfileCourseListAdapter(getActivity(), courseList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        coursesRecyclerView.setLayoutManager(layoutManager);
        coursesRecyclerView.setNestedScrollingEnabled(false);
        coursesRecyclerView.setHorizontalScrollBarEnabled(true);
        coursesRecyclerView.setItemAnimator(new DefaultItemAnimator());
        coursesRecyclerView.setAdapter(teacherProfileCourseListAdapter);

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

        return view;
    }

    private void loadCourses() {

        courseList.clear();
        if (user.getTeacherProfile() != null) {
            if (user.getTeacherProfile().getTeacherCourses() != null) {
                courseList.addAll(user.getTeacherProfile().getTeacherCourses());
            }
        }
        teacherProfileCourseListAdapter.notifyDataSetChanged();
        progressBar.setVisibility(View.INVISIBLE);
        swipeContainer.setRefreshing(false);

    }
}
