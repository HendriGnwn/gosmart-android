package com.atc.gosmartlesmagistra.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.atc.gosmartlesmagistra.App;
import com.atc.gosmartlesmagistra.R;
import com.atc.gosmartlesmagistra.api.UserApi;
import com.atc.gosmartlesmagistra.model.User;
import com.atc.gosmartlesmagistra.model.request.RegisterStudentRequest;
import com.atc.gosmartlesmagistra.model.response.LoginResponse;
import com.atc.gosmartlesmagistra.model.response.LoginSuccess;
import com.atc.gosmartlesmagistra.model.response.RegisterStudentResponse;
import com.atc.gosmartlesmagistra.util.DatabaseHelper;
import com.atc.gosmartlesmagistra.util.SessionManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by hendrigunawan on 7/4/17.
 */

public class SignUpStudentActivity extends AppCompatActivity {

    @BindView(R.id.action_left) ImageButton actionLeft;
    @BindView(R.id.first_name) AutoCompleteTextView mFirstNameView;
    @BindView(R.id.last_name) AutoCompleteTextView mLastNameView;
    @BindView(R.id.mobile_phone) AutoCompleteTextView mPhoneNumberView;
    @BindView(R.id.address) AutoCompleteTextView mAddressView;
    @BindView(R.id.email) AutoCompleteTextView mEmailView;
    @BindView(R.id.password) EditText mPasswordView;
    @BindView(R.id.progress_bar) ProgressBar progressBar;

    SessionManager sessionManager;

    @OnClick(R.id.sign_up_button)
    protected void doSignUp() {
        attemptSignUp();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_student);
        ButterKnife.bind(this);
        setActionLeftIcon();

        sessionManager = new SessionManager(this);
    }

    private void setActionLeftIcon() {
        final Drawable iconLeft = ContextCompat.getDrawable(this, R.drawable.zzz_arrow_left);
        iconLeft.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        actionLeft.setImageDrawable(iconLeft);
    }

    private void attemptSignUp() {

        App.hideSoftKeyboard(this);

        // Reset errors.
        mFirstNameView.setError(null);
        mLastNameView.setError(null);
        mPhoneNumberView.setError(null);
        mAddressView.setError(null);
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String firstName = mFirstNameView.getText().toString();
        String lastName = mLastNameView.getText().toString();
        String phoneNumber = mPhoneNumberView.getText().toString();
        String address = mAddressView.getText().toString();
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        if (!isValidEmail(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        }

        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        }

        if (TextUtils.isEmpty(address)) {
            mAddressView.setError(getString(R.string.error_field_required));
            focusView = mAddressView;
            cancel = true;
        }

        if (TextUtils.isEmpty(phoneNumber)) {
            mPhoneNumberView.setError(getString(R.string.error_field_required));
            focusView = mPhoneNumberView;
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

            launchDialog();
            progressBar.setVisibility(View.VISIBLE);
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(App.API)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            UserApi service = retrofit.create(UserApi.class);

            RegisterStudentRequest request = new RegisterStudentRequest(firstName, lastName, phoneNumber, address, email, password);

            Call<LoginSuccess> call = service.registerStudent(request);
            call.enqueue(new Callback<LoginSuccess>() {
                @Override
                public void onResponse(Call<LoginSuccess> call, Response<LoginSuccess> response) {

                    if (response.raw().isSuccessful()) {
                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();

                        User user = response.body().getUser();
                        sessionManager.setLogin(true, user);

                        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
                        db.createUser(user);

                        Intent intent;
                        intent = new Intent(getApplicationContext(), MainActivity.class);

                        startActivity(intent);
                    } else if (response.raw().code() == 400) {

                        Gson gson = new GsonBuilder().create();
                        RegisterStudentResponse mError =new RegisterStudentResponse();
                        try {
                            mError = gson.fromJson(response.errorBody().string(),RegisterStudentResponse.class);
                            Toast.makeText(getApplicationContext(), mError.getMessage(), Toast.LENGTH_LONG).show();

                            if (mError.getRegisterStudentError().getFirstName() != null) {
                                mFirstNameView.setError(mError.getRegisterStudentError().getFirstName());
                                mFirstNameView.requestFocus();
                            }
                            if (mError.getRegisterStudentError().getAddress() != null) {
                                mAddressView.setError(mError.getRegisterStudentError().getAddress());
                                mAddressView.requestFocus();
                            }
                            if (mError.getRegisterStudentError().getEmail() != null) {
                                mEmailView.setError(mError.getRegisterStudentError().getEmail());
                                mEmailView.requestFocus();
                            }
                            if (mError.getRegisterStudentError().getPassword() != null) {
                                mPasswordView.setError(mError.getRegisterStudentError().getPassword());
                                mPasswordView.requestFocus();
                            }

                        } catch (IOException e) {
                            // handle failure to read error
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), "Sign up failed", Toast.LENGTH_LONG).show();
                    }
                    progressBar.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onFailure(Call<LoginSuccess> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Sign up failed, please try again", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.INVISIBLE);
                }
            });

        }
    }

    public void launchDialog() {
        final ProgressDialog progressDialog = ProgressDialog.show(this, "Sign Up", "Please wait ...", true);
        progressDialog.setCancelable(true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // Here you should write your time consuming task...
                    // Let the progress ring for 10 seconds...
                    Thread.sleep(2000);
                } catch (Exception e) {

                }
                progressDialog.dismiss();
            }
        }).start();
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 5;
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
}
