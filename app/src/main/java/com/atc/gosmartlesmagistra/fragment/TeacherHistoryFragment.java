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
import android.widget.Toast;

import com.atc.gosmartlesmagistra.App;
import com.atc.gosmartlesmagistra.R;
import com.atc.gosmartlesmagistra.adapter.TeacherTotalHistoryListAdapter;
import com.atc.gosmartlesmagistra.api.UserApi;
import com.atc.gosmartlesmagistra.model.TeacherTotalHistory;
import com.atc.gosmartlesmagistra.model.User;
import com.atc.gosmartlesmagistra.model.response.LoginSuccess;
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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by cranium-01 on 27/07/17.
 */

public class TeacherHistoryFragment extends Fragment {

    @BindView(R.id.histories_recycler_view) RecyclerView historyRecyclerView;
    @BindView(R.id.progress_bar) ProgressBar progressBar;
    @BindView(R.id.swipeContainer) SwipeRefreshLayout swipeContainer;

    private SessionManager sessionManager;
    TeacherTotalHistoryListAdapter teacherTotalHistoryListAdapter;
    List<TeacherTotalHistory> totalHistoryList;
    Retrofit retrofit;
    DatabaseHelper databaseHelper;
    User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_teacher_history, container, false);
        ButterKnife.bind(this, view);

        sessionManager = new SessionManager(getContext());
        databaseHelper = new DatabaseHelper(getActivity());
        user = databaseHelper.getUser(sessionManager.getUserCode());
        totalHistoryList = new ArrayList<>();
        teacherTotalHistoryListAdapter = new TeacherTotalHistoryListAdapter(getActivity(), totalHistoryList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        historyRecyclerView.setLayoutManager(layoutManager);
        historyRecyclerView.setNestedScrollingEnabled(false);
        historyRecyclerView.setHorizontalScrollBarEnabled(true);
        historyRecyclerView.setItemAnimator(new DefaultItemAnimator());
        historyRecyclerView.setAdapter(teacherTotalHistoryListAdapter);

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
                refreshHistories();
            }
        });
        loadHistories();

        return view;
    }

    private void refreshHistories() {
        UserApi userApi = retrofit.create(UserApi.class);
        Call<LoginSuccess> call = userApi.getUserByUniqueNumber(sessionManager.getUserCode());
        call.enqueue(new Callback<LoginSuccess>() {
            @Override
            public void onResponse(Call<LoginSuccess> call, Response<LoginSuccess> response) {
                if (response.raw().isSuccessful()) {
                    if (user.getTeacherProfile() != null) {
                        if (user.getTeacherProfile().getTeacherCourses() != null) {
                            totalHistoryList.clear();
                            totalHistoryList.addAll(user.getTeacherProfile().getTeacherTotalHistories());
                        }
                    }
                    teacherTotalHistoryListAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getActivity(), "Invalid load data, please try again", Toast.LENGTH_LONG).show();
                }
                progressBar.setVisibility(View.INVISIBLE);
                swipeContainer.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<LoginSuccess> call, Throwable t) {
                Toast.makeText(getActivity(), "Invalid load data, please try again", Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.INVISIBLE);
                swipeContainer.setRefreshing(false);
            }
        });
    }

    private void loadHistories() {
        totalHistoryList.clear();
        totalHistoryList.addAll(user.getTeacherProfile().getTeacherTotalHistories());
        progressBar.setVisibility(View.INVISIBLE);
        swipeContainer.setRefreshing(false);
    }
}
