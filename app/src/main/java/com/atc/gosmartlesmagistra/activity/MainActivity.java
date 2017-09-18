package com.atc.gosmartlesmagistra.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.atc.gosmartlesmagistra.App;
import com.atc.gosmartlesmagistra.R;
import com.atc.gosmartlesmagistra.adapter.PrivateActiveExpandableListAdapter;
import com.atc.gosmartlesmagistra.adapter.ScheduleExpandableListAdapter;
import com.atc.gosmartlesmagistra.api.PrivateApi;
import com.atc.gosmartlesmagistra.api.UserApi;
import com.atc.gosmartlesmagistra.model.PrivateModel;
import com.atc.gosmartlesmagistra.model.Schedule;
import com.atc.gosmartlesmagistra.model.User;
import com.atc.gosmartlesmagistra.model.response.LogoutSuccess;
import com.atc.gosmartlesmagistra.model.response.PrivateActivesSuccess;
import com.atc.gosmartlesmagistra.model.response.SchedulesSuccess;
import com.atc.gosmartlesmagistra.util.DatabaseHelper;
import com.atc.gosmartlesmagistra.util.SessionManager;
import com.pkmmte.view.CircularImageView;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import butterknife.BindColor;
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
 * Created by hendrigunawan 7/4/2017
 */

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.fab) FloatingActionButton fab;
    @BindView(R.id.drawer_layout) DrawerLayout drawer;
    @BindView(R.id.nav_view) NavigationView navigationView;
    @BindView(R.id.private_list_view) ExpandableListView privateListView;
    @BindView(R.id.schedule_list_view) ExpandableListView scheduleListView;
    @BindView(R.id.progress_bar) ProgressBar progressBar;
    @BindView(R.id.swipeContainer) SwipeRefreshLayout swipeContainer;
    @BindView(R.id.wording) RelativeLayout wordingRelative;
    @BindView(R.id.wording_private) RelativeLayout wordingPrivateRelative;

    @BindColor(R.color.colorWhite) int white;
    @BindColor(R.color.colorAccent) int accent;
    @BindColor(R.color.colorYellow) int yellow;

    boolean doubleBackToExitPressedOnce = false;
    SessionManager sessionManager;
    DatabaseHelper databaseHelper;
    List<PrivateModel> privateModelList;
    List<Schedule> scheduleList;
    ScheduleExpandableListAdapter scheduleExpandableListAdapter;
    PrivateActiveExpandableListAdapter privateActiveExpandableListAdapter;
    Retrofit retrofit;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(null);
        }

        sessionManager = new SessionManager(this);
        databaseHelper = new DatabaseHelper(this);

        setLocaleIndonesianLanguage();

        if (!sessionManager.isLoggedIn()) {
            Toast.makeText(this, "You must be a login", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            return;
        }

        user = databaseHelper.getUser(sessionManager.getUserCode());

        if (user.getRole() == User.roleStudent) {
            fab.setVisibility(View.GONE);
        } else {
            final Drawable iconFab = ContextCompat.getDrawable(this, R.drawable.zzz_book_plus);
            iconFab.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
            fab.setImageDrawable(iconFab);

            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), UpdateTeacherCourseActivity.class);
                    intent.putExtra("isNewRecord", true);
                    startActivity(intent);
                }
            });
        }

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        manageHeaderView();

        if (user.getRole() == User.roleTeacher) {
            navigationView.getMenu().findItem(R.id.nav_order).setVisible(false);
        }

        swipeContainer.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPrivateActives();
                getSchedules();
            }
        });

        getPrivateActives();
        getSchedules();

        scheduleListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                setListViewHeightSchedule(parent, groupPosition);

                return false;
            }
        });
        scheduleListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                ScheduleExpandableListAdapter listAdapter = (ScheduleExpandableListAdapter) scheduleListView.getExpandableListAdapter();
                View groupItem = listAdapter.getGroupView(groupPosition, true, null, scheduleListView);
                RelativeLayout layout = (RelativeLayout) groupItem.findViewById(R.id.group_view);
                layout.setVisibility(View.GONE);
            }
        });
        scheduleListView.setNestedScrollingEnabled(true);

        privateListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                setListViewHeight(parent, groupPosition);

                return false;
            }
        });
        privateListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                PrivateActiveExpandableListAdapter listAdapter = (PrivateActiveExpandableListAdapter) privateListView.getExpandableListAdapter();
                View groupItem = listAdapter.getGroupView(groupPosition, true, null, privateListView);
                RelativeLayout layout = (RelativeLayout) groupItem.findViewById(R.id.group_view);
                layout.setVisibility(View.GONE);
            }
        });
        privateListView.setNestedScrollingEnabled(true);

    }

    private void getSchedules() {
        swipeContainer.setRefreshing(true);
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
        Call<SchedulesSuccess> call = userApi.getSchedules(sessionManager.getUserCode());
        call.enqueue(new Callback<SchedulesSuccess>() {
            @Override
            public void onResponse(Call<SchedulesSuccess> call, Response<SchedulesSuccess> response) {
                if (response.raw().isSuccessful()) {
                    scheduleList = response.body().getSchedules();

                    if (scheduleList == null) {
                        wordingRelative.setVisibility(View.VISIBLE);
                        scheduleListView.setVisibility(View.GONE);
                    } else {
                        scheduleListView.setVisibility(View.VISIBLE);
                        wordingRelative.setVisibility(View.GONE);
                        HashMap<String, List<String>> listDataChild = new HashMap<String, List<String>>();
                        for (Schedule schedule : scheduleList) {
                            List<String> description = new ArrayList<String>();
                            description.add(schedule.getMessage());
                            listDataChild.put(String.valueOf(schedule.getPrivateModel().getId()), description); // Header, Child data
                        }

                        scheduleExpandableListAdapter = new ScheduleExpandableListAdapter(getApplicationContext(), scheduleList, listDataChild);
                        scheduleListView.setAdapter(scheduleExpandableListAdapter);
                        setListViewHeightBasedOnChildrenSchedule(scheduleListView);
                    }
                }
                swipeContainer.setRefreshing(false);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<SchedulesSuccess> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Unable to load, please try again", Toast.LENGTH_SHORT).show();
                swipeContainer.setRefreshing(false);
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void getPrivateActives() {
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

        PrivateApi orderApi = retrofit.create(PrivateApi.class);
        Call<PrivateActivesSuccess> call = orderApi.privateActives(sessionManager.getUserCode());
        call.enqueue(new Callback<PrivateActivesSuccess>() {
            @Override
            public void onResponse(Call<PrivateActivesSuccess> call, Response<PrivateActivesSuccess> response) {
                if (response.raw().isSuccessful()) {
                    privateModelList = response.body().getPrivateModels();

                    if (privateModelList == null) {
                        Log.i("cranium", "test");
                        wordingPrivateRelative.setVisibility(View.VISIBLE);
                        privateListView.setVisibility(View.GONE);
                    } else {
                        privateListView.setVisibility(View.VISIBLE);
                        wordingPrivateRelative.setVisibility(View.GONE);
                        HashMap<String, List<String>> listDataChild = new HashMap<String, List<String>>();
                        for (PrivateModel order: privateModelList) {
                            List<String> description = new ArrayList<String>();
                            description.add(order.getCode());
                            listDataChild.put(String.valueOf(order.getId()), description); // Header, Child data
                        }

                        privateActiveExpandableListAdapter= new PrivateActiveExpandableListAdapter(getApplicationContext(), privateModelList, listDataChild);
                        privateListView.setAdapter(privateActiveExpandableListAdapter);
                        setListViewHeightBasedOnChildren(privateListView);
                    }


                }
                swipeContainer.setRefreshing(false);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<PrivateActivesSuccess> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Unable to load, please try again", Toast.LENGTH_SHORT).show();
                swipeContainer.setRefreshing(false);
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void manageHeaderView() {
        View headerView = navigationView.getHeaderView(0);
        TextView userName = (TextView) headerView.findViewById(R.id.name);
        TextView userUniqueNumber = (TextView) headerView.findViewById(R.id.uniqueNumber);
        TextView userEmail = (TextView) headerView.findViewById(R.id.email);
        TextView userStatus = (TextView) headerView.findViewById(R.id.status) ;
        CircularImageView userPhoto = (CircularImageView) headerView.findViewById(R.id.image);

        userName.setText(user.getFirstName() + " " + user.getLastName());
        userUniqueNumber.setText(user.getUniqueNumber());
        userEmail.setText(user.getEmail());
        userStatus.setText(getResources().getString(R.string.status) + ": " + user.getStatusText());
        Picasso.with(this).load(App.URL + "files/users/" + user.getPhoto()).error(R.drawable.user).into(userPhoto);

        userName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(sessionManager.getKeyUserRole()) == User.roleTeacher) {
                    Intent intent = new Intent(getApplicationContext(), TeacherProfileActivity.class);
                    startActivity(intent);
                } else if (Integer.parseInt(sessionManager.getKeyUserRole()) == User.roleStudent) {
                    Intent intent = new Intent(getApplicationContext(), EditProfileStudentActivity.class);
                    startActivity(intent);
                }
                drawer.closeDrawer(GravityCompat.START);
            }
        });

        userPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(sessionManager.getKeyUserRole()) == User.roleTeacher) {
                    Intent intent = new Intent(getApplicationContext(), TeacherProfileActivity.class);
                    startActivity(intent);
                } else if (Integer.parseInt(sessionManager.getKeyUserRole()) == User.roleStudent) {
                    Intent intent = new Intent(getApplicationContext(), EditProfileStudentActivity.class);
                    startActivity(intent);
                }
                drawer.closeDrawer(GravityCompat.START);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (doubleBackToExitPressedOnce) {
                finish();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click back again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce=false;
                }
            }, 2000);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        Drawable notificationNotAvailable = ContextCompat.getDrawable(this, R.drawable.zzz_clock);
        notificationNotAvailable.setColorFilter(white, PorterDuff.Mode.SRC_ATOP);
        Drawable notificationAvailable = ContextCompat.getDrawable(this, R.drawable.zzz_clock_alert);
        notificationAvailable.setColorFilter(yellow, PorterDuff.Mode.SRC_ATOP);
        Drawable cartDrawable = ContextCompat.getDrawable(this, R.drawable.zzz_cart);
        if (sessionManager.getHaveAnOrder()) {
            cartDrawable.setColorFilter(yellow, PorterDuff.Mode.SRC_ATOP);
        } else {
            cartDrawable.setColorFilter(white, PorterDuff.Mode.SRC_ATOP);
        }

        menu.findItem(R.id.action_cart)
                .setIcon(cartDrawable)
                .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        Intent intent = new Intent(getApplicationContext(), PrivateOrderActivity.class);
                        startActivity(intent);

                        return false;
                    }
                });
        menu.findItem(R.id.action_notification)
                .setIcon(notificationAvailable)
                .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Intent intent = new Intent(getApplicationContext(), NotificationActivity.class);
                        startActivity(intent);
                        return false;
                    }
                });

        if (!sessionManager.isLoggedIn()) {
            return true;
        }

        if (user.getRole() == User.roleTeacher) {
            menu.findItem(R.id.action_cart)
                    .setVisible(false);
        } else {
            menu.findItem(R.id.action_cart)
                    .setVisible(true);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_cart) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_cource) {
            drawer.closeDrawer(GravityCompat.START);
            Intent intent = new Intent(this, CoursesActivity.class);
            startActivity(intent);
            // Handle the camera action
        } else if (id == R.id.nav_private) {
            Intent intent = new Intent(this, PrivateOrderHistoryActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_order) {
            Intent intent = new Intent(this, OrderHistoryActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_sign_out) {
            drawer.closeDrawer(GravityCompat.START);
            attemptLogout();
        } else if (id == R.id.nav_contact) {
            Intent intent = new Intent(this, SendFeedbackActivity.class);
            startActivity(intent);
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    protected void attemptLogout() {
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

        Call<LogoutSuccess> call = service.logout();
        call.enqueue(new Callback<LogoutSuccess>() {
            @Override
            public void onResponse(Call<LogoutSuccess> call, Response<LogoutSuccess> response) {

                if (response.raw().isSuccessful()) {
                    sessionManager.setLogout();
                    Toast.makeText(MainActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                } else {
                    sessionManager.setLogout();
                    Toast.makeText(MainActivity.this, "Your login is expired.", Toast.LENGTH_LONG).show();
                }

                DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);

                finish();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<LogoutSuccess> call, Throwable t) {
                Log.i("cranium:save", "failed");
            }
        });
    }

    private void setListViewHeight(ExpandableListView listView,
                                   int group) {
        PrivateActiveExpandableListAdapter listAdapter = (PrivateActiveExpandableListAdapter) listView.getExpandableListAdapter();
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
        PrivateActiveExpandableListAdapter listAdapter = (PrivateActiveExpandableListAdapter) listView.getExpandableListAdapter();
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

    private void setListViewHeightSchedule(ExpandableListView listView,
                                   int group) {
        ScheduleExpandableListAdapter listAdapter = (ScheduleExpandableListAdapter) listView.getExpandableListAdapter();
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
            height = 250;
        params.height = height;
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    public static void setListViewHeightBasedOnChildrenSchedule(ExpandableListView listView) {
        ScheduleExpandableListAdapter listAdapter = (ScheduleExpandableListAdapter) listView.getExpandableListAdapter();
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

    private void setLocaleIndonesianLanguage() {
        Resources resources = getResources();
        Configuration configuration = resources.getConfiguration();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        Locale locale = new Locale("id", "ID");
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration,displayMetrics);
    }
}
