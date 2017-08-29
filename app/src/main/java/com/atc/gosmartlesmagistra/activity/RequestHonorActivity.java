package com.atc.gosmartlesmagistra.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.atc.gosmartlesmagistra.App;
import com.atc.gosmartlesmagistra.R;
import com.atc.gosmartlesmagistra.api.UserApi;
import com.atc.gosmartlesmagistra.model.User;
import com.atc.gosmartlesmagistra.model.request.RequestHonorRequest;
import com.atc.gosmartlesmagistra.model.request.UpdateStudentProfileRequest;
import com.atc.gosmartlesmagistra.model.response.LoginSuccess;
import com.atc.gosmartlesmagistra.model.response.RequestHonorResponse;
import com.atc.gosmartlesmagistra.model.response.UpdateTeacherProfileResponse;
import com.atc.gosmartlesmagistra.util.DatabaseHelper;
import com.atc.gosmartlesmagistra.util.SessionManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pkmmte.view.CircularImageView;
import com.squareup.picasso.Picasso;
import com.sw926.imagefileselector.ErrorResult;
import com.sw926.imagefileselector.ImageFileSelector;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
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

/**
 * Created by hendrigunawan on 7/4/17.
 */

public class RequestHonorActivity extends AppCompatActivity {

    @BindView(R.id.action_left) ImageButton actionLeft;
    @BindView(R.id.total) AutoCompleteTextView mTotalView;
    @BindView(R.id.total_description) TextView totalDescription;
    @BindView(R.id.progress_bar) ProgressBar progressBar;

    SessionManager sessionManager;
    DatabaseHelper databaseHelper;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_honor);
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

        totalDescription.setText(user.getTeacherProfile().getFormattedTotal());
    }

    private void setActionLeftIcon() {
        final Drawable iconLeft = ContextCompat.getDrawable(this, R.drawable.zzz_arrow_left);
        iconLeft.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        actionLeft.setImageDrawable(iconLeft);
    }

    private void attemptSubmit() {
        App.hideSoftKeyboard(this);

        String total = mTotalView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(total)) {
            mTotalView.setError(getString(R.string.error_field_required));
            focusView = mTotalView;
            cancel = true;
            focusView.requestFocus();
            return;
        }

        if (Float.parseFloat(total) > Float.parseFloat(user.getTeacherProfile().getTotal())) {
            mTotalView.setError(getString(R.string.error_invalid_request_honor) + " " + user.getTeacherProfile().getFormattedTotal());
            focusView = mTotalView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
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
            UserApi service = retrofit.create(UserApi.class);
            RequestHonorRequest request = new RequestHonorRequest(Integer.parseInt(total));
            Call<LoginSuccess> call = service.requestHonor(sessionManager.getUserCode(), request);
            call.enqueue(new Callback<LoginSuccess>() {
                @Override
                public void onResponse(Call<LoginSuccess> call, Response<LoginSuccess> response) {
                    if (response.raw().isSuccessful()) {

                        databaseHelper.createUser(response.body());

                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), TeacherProfileActivity.class);
                        intent.putExtra("setToHistories", true);
                        startActivity(intent);

                    } else if (response.raw().code() == 400) {

                        Gson gson = new GsonBuilder().create();
                        RequestHonorResponse mError =new RequestHonorResponse();
                        try {
                            mError = gson.fromJson(response.errorBody().string(),RequestHonorResponse.class);
                            Toast.makeText(getApplicationContext(), mError.getMessage(), Toast.LENGTH_LONG).show();

                            if (mError.getRequestHonorError().getTotal() != null) {
                                mTotalView.setError(mError.getRequestHonorError().getTotal());
                                mTotalView.requestFocus();
                            }

                        } catch (IOException e) {
                            // handle failure to read error
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), "Request Honor failed", Toast.LENGTH_LONG).show();
                    }

                    progressBar.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onFailure(Call<LoginSuccess> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Update Profile failed, Please try again.", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.INVISIBLE);
                }
            });

        }
    }

    private boolean isValidEmail(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@") && email.contains(".");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @OnClick(R.id.action_left)
    public void doActionLeft(View view) {
        onBackPressed();
    }

    @OnClick(R.id.submit_button)
    protected void submitClick() {
        attemptSubmit();
    }
}
