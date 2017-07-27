package com.atc.gosmartlesmagistra.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.atc.gosmartlesmagistra.App;
import com.atc.gosmartlesmagistra.R;
import com.atc.gosmartlesmagistra.api.UserApi;
import com.atc.gosmartlesmagistra.model.User;
import com.atc.gosmartlesmagistra.model.request.LoginRequest;
import com.atc.gosmartlesmagistra.model.response.LoginResponse;
import com.atc.gosmartlesmagistra.model.response.LoginSuccess;
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

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.email) AutoCompleteTextView mEmailView;
    @BindView(R.id.password) EditText mPasswordView;
    @BindView(R.id.sign_in_button) AppCompatButton signInButton;
    @BindView(R.id.action_need) TextView actionNeedTextView;
    @BindView(R.id.action_sign_up_student) TextView actionSignUpStudentTextView;
    @BindView(R.id.action_sign_up_teacher) TextView actionSignUpTeacherTextView;
    @BindView(R.id.progress_bar) ProgressBar progressBar;

    boolean doubleBackToExitPressedOnce = false;

    SessionManager session;

    @Override
    public void onBackPressed() {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        session = new SessionManager(this);
        if (session.isLoggedIn()) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    @OnClick(R.id.sign_in_button)
    protected void actionSignIn() {
        attemptLogin();
    }

    @OnClick(R.id.action_need)
    protected void actionNeed() {
        Intent intent = new Intent(this, ForgotPasswordActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.action_sign_up_student)
    protected void setActionSignUpStudent() {
        Intent intent = new Intent(this, SignUpStudentActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.action_sign_up_teacher)
    protected void setActionSignUpTeacher() {
        Intent intent = new Intent(this, TeacherTermConditionActivity.class);
        startActivity(intent);
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {

        App.hideSoftKeyboard(this);

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
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

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
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

            LoginRequest request = new LoginRequest(email, password, getFirebaseToken());

            Call<LoginSuccess> call = service.login(request);
            call.enqueue(new Callback<LoginSuccess>() {
                @Override
                public void onResponse(Call<LoginSuccess> call, Response<LoginSuccess> response) {

                    if (response.raw().isSuccessful()) {
                        Toast.makeText(LoginActivity.this, "Login success", Toast.LENGTH_LONG).show();

                        User user = response.body().getUser();
                        session.setLogin(true, user);

                        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
                        db.createUser(response.body().getUser());
                        db.createUser(response.body());

                        Intent intent;
                        intent = new Intent(getApplicationContext(), MainActivity.class);

                        startActivity(intent);
                    } else if (response.raw().code() == 400) {

                        Gson gson = new GsonBuilder().create();
                        LoginResponse mError =new LoginResponse();
                        try {
                            mError = gson.fromJson(response.errorBody().string(),LoginResponse.class);
                            Toast.makeText(getApplicationContext(), mError.getMessage(), Toast.LENGTH_LONG).show();

                            if (mError.getLoginError().getEmail() != null) {
                                mEmailView.setError(mError.getLoginError().getEmail());
                                mEmailView.requestFocus();
                            }
                            if (mError.getLoginError().getPassword() != null) {
                                mPasswordView.setError(mError.getLoginError().getPassword());
                                mPasswordView.requestFocus();
                            }

                        } catch (IOException e) {
                            // handle failure to read error
                        }

                    } else {
                        Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_LONG).show();
                    }
                    progressBar.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onFailure(Call<LoginSuccess> call, Throwable t) {
                    Toast.makeText(LoginActivity.this, "Login failed, please try again", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.INVISIBLE);
                }
            });

        }
    }

    private String getFirebaseToken()
    {
//        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
//        String firebaseToken = pref.getString("regId", null);
//
//        Log.e("cranium", "Firebase reg id: " + firebaseToken);

        String firebaseToken = "test";

        return firebaseToken;
    }

    public void launchDialog() {
        final ProgressDialog progressDialog = ProgressDialog.show(LoginActivity.this, "Sign In Credentials", "Please wait ...", true);
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
        return password.length() > 4;
    }

    private boolean isValidEmail(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@") && email.contains(".");
    }

}
