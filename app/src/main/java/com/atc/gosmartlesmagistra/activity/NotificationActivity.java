package com.atc.gosmartlesmagistra.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.atc.gosmartlesmagistra.App;
import com.atc.gosmartlesmagistra.R;
import com.atc.gosmartlesmagistra.adapter.NotificationExpandableListAdapter;
import com.atc.gosmartlesmagistra.adapter.OrderHistoryExpandableListAdapter;
import com.atc.gosmartlesmagistra.api.OrderApi;
import com.atc.gosmartlesmagistra.api.UserApi;
import com.atc.gosmartlesmagistra.model.Notification;
import com.atc.gosmartlesmagistra.model.Order;
import com.atc.gosmartlesmagistra.model.User;
import com.atc.gosmartlesmagistra.model.response.NotificationSuccess;
import com.atc.gosmartlesmagistra.model.response.OrderHistorySuccess;
import com.atc.gosmartlesmagistra.util.DatabaseHelper;
import com.atc.gosmartlesmagistra.util.SessionManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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

public class NotificationActivity extends AppCompatActivity {

    @BindView(R.id.action_left) ImageButton actionLeft;
    @BindView(R.id.notification_list_view) ExpandableListView orderListView;
    @BindView(R.id.swipeContainer) SwipeRefreshLayout swipeContainer;
    @BindView(R.id.progress_bar) ProgressBar progressBar;

    SessionManager sessionManager;
    DatabaseHelper databaseHelper;
    User user;
    NotificationExpandableListAdapter notificationExpandableListAdapter;
    List<Notification> notificationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        ButterKnife.bind(this);
        setActionLeftIcon();

        sessionManager = new SessionManager(this);
        databaseHelper = new DatabaseHelper(this);

        if (!sessionManager.isLoggedIn()) {
            Toast.makeText(this, "You must login", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

        user = databaseHelper.getUser(sessionManager.getUserCode());

        getOrderHistories();

        orderListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                setListViewHeight(parent, groupPosition);

                return false;
            }
        });
        orderListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                NotificationExpandableListAdapter listAdapter = (NotificationExpandableListAdapter) orderListView.getExpandableListAdapter();
                View groupItem = listAdapter.getGroupView(groupPosition, true, null, orderListView);
                RelativeLayout layout = (RelativeLayout) groupItem.findViewById(R.id.group_view);
                layout.setVisibility(View.GONE);
            }
        });
        orderListView.setNestedScrollingEnabled(true);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getOrderHistories();
            }
        });
    }

    private void getOrderHistories() {
        progressBar.setVisibility(View.VISIBLE);
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

        UserApi userApi = retrofit.create(UserApi.class);
        Call<NotificationSuccess> call = userApi.notification(sessionManager.getUserCode());
        call.enqueue(new Callback<NotificationSuccess>() {
            @Override
            public void onResponse(Call<NotificationSuccess> call, Response<NotificationSuccess> response) {
                if (response.raw().isSuccessful()) {
                    notificationList = response.body().getNotifications();

                    swipeContainer.setRefreshing(false);

                    HashMap<String, List<String>> listDataChild = new HashMap<String, List<String>>();
                    for (Notification order: notificationList) {
                        List<String> description = new ArrayList<String>();
                        description.add(order.getName());
                        listDataChild.put(String.valueOf(order.getId()), description); // Header, Child data
                    }

                    notificationExpandableListAdapter = new NotificationExpandableListAdapter(getApplicationContext(), notificationList, listDataChild);
                    orderListView.setAdapter(notificationExpandableListAdapter);
                    setListViewHeightBasedOnChildren(orderListView);
                }

                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<NotificationSuccess> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Unable to load, please try again", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
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

    private void setListViewHeight(ExpandableListView listView,
                                   int group) {
        NotificationExpandableListAdapter listAdapter = (NotificationExpandableListAdapter) listView.getExpandableListAdapter();
        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(),
                View.MeasureSpec.EXACTLY);
        for (int i = 0; i < listAdapter.getGroupCount(); i++) {
            View groupItem = listAdapter.getGroupView(i, false, null, listView);
            groupItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

            totalHeight += groupItem.getMeasuredHeight();

            if (((listView.isGroupExpanded(i)) && (i != group))
                    || ((!listView.isGroupExpanded(i)) && (i == group))) {
                for (int j = 0; j < listAdapter.getChildrenCount(i); j++) {
                    View listItem = listAdapter.getChildView(i, j, false, null,
                            listView);
                    listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

                    totalHeight += listItem.getMeasuredHeight();

                }
            }
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        int height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getGroupCount() - 1));
        if (height < 10)
            height = 200;
        params.height = height;
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    public static void setListViewHeightBasedOnChildren(ExpandableListView listView) {
        NotificationExpandableListAdapter listAdapter = (NotificationExpandableListAdapter) listView.getExpandableListAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getGroupCount(); i++) {
            View listItem = listAdapter.getGroupView(i, false, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getGroupCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
}
