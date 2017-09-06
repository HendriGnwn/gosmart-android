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
import android.widget.Toast;

import com.atc.gosmartlesmagistra.App;
import com.atc.gosmartlesmagistra.R;
import com.atc.gosmartlesmagistra.api.RequestApi;
import com.atc.gosmartlesmagistra.api.UserApi;
import com.atc.gosmartlesmagistra.model.User;
import com.atc.gosmartlesmagistra.model.request.SendFeedbackRequest;
import com.atc.gosmartlesmagistra.model.request.UpdateStudentProfileRequest;
import com.atc.gosmartlesmagistra.model.response.LoginSuccess;
import com.atc.gosmartlesmagistra.model.response.ResponseSuccess;
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

public class SendFeedbackActivity extends AppCompatActivity {

    @BindView(R.id.action_left) ImageButton actionLeft;
    @BindView(R.id.first_name) AutoCompleteTextView mFirstNameView;
    @BindView(R.id.last_name) AutoCompleteTextView mLastNameView;
    @BindView(R.id.email) AutoCompleteTextView mEmailView;
    @BindView(R.id.mobile_phone) AutoCompleteTextView mMobilePhoneView;
    @BindView(R.id.message) AutoCompleteTextView mMessageView;
    @BindView(R.id.progress_bar) ProgressBar progressBar;

    SessionManager sessionManager;
    DatabaseHelper databaseHelper;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_feedback);
        ButterKnife.bind(this);
        setActionLeftIcon();

        sessionManager = new SessionManager(this);
        databaseHelper = new DatabaseHelper(this);

        if (sessionManager.isLoggedIn()) {
            user = databaseHelper.getUser(sessionManager.getUserCode());

            mFirstNameView.setText(user.getFirstName());
            mLastNameView.setText(user.getLastName());
            mEmailView.setText(user.getEmail());
            mMobilePhoneView.setText(user.getPhoneNumber());
        }
    }

    private void setActionLeftIcon() {
        final Drawable iconLeft = ContextCompat.getDrawable(this, R.drawable.zzz_arrow_left);
        iconLeft.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        actionLeft.setImageDrawable(iconLeft);
    }

    private void attemptSubmit() {
        App.hideSoftKeyboard(this);

        String firstName = mFirstNameView.getText().toString();
        String lastName = mLastNameView.getText().toString();
        String email = mEmailView.getText().toString();
        String phoneNumber = mMobilePhoneView.getText().toString();
        String message = mMessageView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(message)) {
            mMessageView.setError(getString(R.string.error_field_required));
            focusView = mMessageView;
            cancel = true;
        }

        if (TextUtils.isEmpty(phoneNumber)) {
            mMobilePhoneView.setError(getString(R.string.error_field_required));
            focusView = mMobilePhoneView;
            cancel = true;
        }

        if (!isValidEmail(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        }

        if (TextUtils.isEmpty(firstName)) {
            mFirstNameView.setError(getString(R.string.error_field_required));
            focusView = mFirstNameView;
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
            RequestApi service = retrofit.create(RequestApi.class);
            //UpdateStudentProfileRequest(String firstName, String lastName, String phoneNumber, String latitude, String longitude, String address, String email, String school, String degree, String department, String schoolAddress, String photo) {
            SendFeedbackRequest request = new SendFeedbackRequest(firstName, lastName, email, phoneNumber, message);
            Call<ResponseSuccess> call = service.sendFeedback(request);
            call.enqueue(new Callback<ResponseSuccess>() {
                @Override
                public void onResponse(Call<ResponseSuccess> call, Response<ResponseSuccess> response) {
                    if (response.raw().isSuccessful()) {
                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                        mFirstNameView.setError(null);
                        mLastNameView.setError(null);
                        mEmailView.setError(null);
                        mMobilePhoneView.setError(null);
                        mMessageView.setError(null);

                        mFirstNameView.setText("");
                        mLastNameView.setText("");
                        mEmailView.setText("");
                        mMobilePhoneView.setText("");
                        mMessageView.setText("");

                        if (sessionManager.isLoggedIn()) {
                            user = databaseHelper.getUser(sessionManager.getUserCode());

                            mFirstNameView.setText(user.getFirstName());
                            mLastNameView.setText(user.getLastName());
                            mEmailView.setText(user.getEmail());
                            mMobilePhoneView.setText(user.getPhoneNumber());
                        }

                    } else if (response.raw().code() == 400) {

                        Gson gson = new GsonBuilder().create();
                        UpdateTeacherProfileResponse mError =new UpdateTeacherProfileResponse();
                        try {
                            mError = gson.fromJson(response.errorBody().string(),UpdateTeacherProfileResponse.class);
                            Toast.makeText(getApplicationContext(), mError.getMessage(), Toast.LENGTH_LONG).show();
                        } catch (IOException e) {
                            // handle failure to read error
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), "Send Feedback failed", Toast.LENGTH_LONG).show();
                    }

                    progressBar.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onFailure(Call<ResponseSuccess> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Send Feedback failed, Please try again.", Toast.LENGTH_LONG).show();
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
