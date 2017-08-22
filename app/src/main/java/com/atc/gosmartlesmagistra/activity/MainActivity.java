package com.atc.gosmartlesmagistra.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
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
import android.widget.TextView;
import android.widget.Toast;

import com.atc.gosmartlesmagistra.App;
import com.atc.gosmartlesmagistra.R;
import com.atc.gosmartlesmagistra.api.UserApi;
import com.atc.gosmartlesmagistra.model.User;
import com.atc.gosmartlesmagistra.model.response.LogoutSuccess;
import com.atc.gosmartlesmagistra.util.DatabaseHelper;
import com.atc.gosmartlesmagistra.util.SessionManager;
import com.pkmmte.view.CircularImageView;
import com.squareup.picasso.Picasso;

import java.io.IOException;

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

    @BindColor(R.color.colorWhite) int white;
    @BindColor(R.color.colorAccent) int accent;
    @BindColor(R.color.colorYellow) int yellow;

    boolean doubleBackToExitPressedOnce = false;
    SessionManager sessionManager;
    DatabaseHelper databaseHelper;
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
    }

    private void manageHeaderView() {
        View headerView = navigationView.getHeaderView(0);
        TextView userName = (TextView) headerView.findViewById(R.id.name);
        TextView userEmail = (TextView) headerView.findViewById(R.id.email);
        CircularImageView userPhoto = (CircularImageView) headerView.findViewById(R.id.image);

        userName.setText(user.getFirstName() + " " + user.getLastName());
        userEmail.setText(user.getEmail());
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
}
