package com.atc.gosmartlesmagistra.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.atc.gosmartlesmagistra.R;

import butterknife.BindView;
import butterknife.ButterKnife;

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

    boolean doubleBackToExitPressedOnce = false;

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

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        actionNeedTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });

        actionSignUpStudentTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUpStudentActivity.class);
                startActivity(intent);
                Log.i("atc", "click");
            }
        });

        actionSignUpTeacherTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUpTeacherActivity.class);
                startActivity(intent);
            }
        });

    }

//    @OnClick(R.id.action_register)
//    protected void actionRegister() {
//        Intent intent = new Intent(this, TermAndConditionActivity.class);
//        startActivity(intent);
//    }
//
//    @OnClick(R.id.action_need)
//    protected void actionNeed() {
//        Intent intent = new Intent(this, ForgotPasswordActivity.class);
//        startActivity(intent);
//    }
//
//    public void doActionLeft(View view) {
//        super.onBackPressed();
//    }
//
//    /**
//     * Attempts to sign in or register the account specified by the login form.
//     * If there are form errors (invalid email, missing fields, etc.), the
//     * errors are presented and no actual login attempt is made.
//     */
//    private void attemptLogin() {
//
//        final ProgressBar progressLogin = (ProgressBar) findViewById(R.id.login_progress);
//        App.hideSoftKeyboard(this);
//
//        // Reset errors.
//        mEmailView.setError(null);
//        mPasswordView.setError(null);
//
//        // Store values at the time of the login attempt.
//        String email = mEmailView.getText().toString();
//        String password = mPasswordView.getText().toString();
//
//        boolean cancel = false;
//        View focusView = null;
//
//        // Check for a valid password, if the user entered one.
//        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
//            mPasswordView.setError(getString(R.string.error_invalid_password));
//            focusView = mPasswordView;
//            cancel = true;
//        }
//
//        if (!isValidEmail(email)) {
//            mEmailView.setError(getString(R.string.error_invalid_email));
//            focusView = mEmailView;
//            cancel = true;
//        }
//
//        // Check for a valid email address.
//        if (TextUtils.isEmpty(email)) {
//            mEmailView.setError(getString(R.string.error_field_required));
//            focusView = mEmailView;
//            cancel = true;
//        }
//
//        if (cancel) {
//            // There was an error; don't attempt login and focus the first
//            // form field with an error.
//            focusView.requestFocus();
//        } else {
//
//            launchDialog();
//            // Show a progress spinner, and kick off a background task to
//            // perform the user login attempt.
//            Retrofit retrofit = new Retrofit.Builder()
//                    .baseUrl(App.API)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build();
//
//            UserApi service = retrofit.create(UserApi.class);
//
//            LoginRequest request = new LoginRequest(email, password, getFirebaseToken());
//
//            Call<LoginResponse> call = service.login(request);
//            call.enqueue(new Callback<LoginResponse>() {
//                @Override
//                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
//
//                    if (response.raw().isSuccessful()) {
//                        Toast.makeText(LoginActivity.this, "Login is success", Toast.LENGTH_LONG).show();
//
//                        SessionManager session = new SessionManager(getApplicationContext());
//                        User user = response.body().getUser();
//                        session.setLogin(true, user);
//
//                        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
//                        db.createUser(user);
//
//                        Intent intent;
//                        intent = new Intent(getApplicationContext(), MainActivity.class);
//
//                        startActivity(intent);
//                    } else {
//                        Toast.makeText(LoginActivity.this, "Login is failed", Toast.LENGTH_LONG).show();
//                    }
//                    progressLogin.setVisibility(View.GONE);
//                }
//
//                @Override
//                public void onFailure(Call<LoginResponse> call, Throwable t) {
//                    Toast.makeText(LoginActivity.this, "Login is failed", Toast.LENGTH_LONG).show();
//                    progressLogin.setVisibility(View.GONE);
//                }
//            });
//
//        }
//    }
//
//    private String getFirebaseToken()
//    {
//        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
//        String firebaseToken = pref.getString("regId", null);
//
//        Log.e("cranium", "Firebase reg id: " + firebaseToken);
//
//        return firebaseToken;
//    }
//
//    public void launchDialog() {
//        final ProgressDialog progressDialog = ProgressDialog.show(LoginActivity.this, "Login Credentials", "Please wait ...", true);
//        progressDialog.setCancelable(true);
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    // Here you should write your time consuming task...
//                    // Let the progress ring for 10 seconds...
//                    Thread.sleep(2000);
//                } catch (Exception e) {
//
//                }
//                progressDialog.dismiss();
//            }
//        }).start();
//    }
//
//
//    private boolean isPasswordValid(String password) {
//        //TODO: Replace this with your own logic
//        return password.length() > 4;
//    }
//
//    private boolean isValidEmail(String email) {
//        //TODO: Replace this with your own logic
//        return email.contains("@") && email.contains(".");
//    }

}
