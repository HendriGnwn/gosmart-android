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
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.atc.gosmartlesmagistra.App;
import com.atc.gosmartlesmagistra.R;
import com.atc.gosmartlesmagistra.api.UserApi;
import com.atc.gosmartlesmagistra.model.User;
import com.atc.gosmartlesmagistra.model.request.UpdateTeacherBankRequest;
import com.atc.gosmartlesmagistra.model.request.UpdateTeacherProfileRequest;
import com.atc.gosmartlesmagistra.model.response.LoginSuccess;
import com.atc.gosmartlesmagistra.model.response.UpdateTeacherBankResponse;
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

public class UpdateTeacherBankActivity extends AppCompatActivity {

    @BindView(R.id.action_left) ImageButton actionLeft;
    @BindView(R.id.bank_name) AutoCompleteTextView mBankNameView;
    @BindView(R.id.number) AutoCompleteTextView mNumberView;
    @BindView(R.id.branch) AutoCompleteTextView mBranchView;
    @BindView(R.id.behalf_of) AutoCompleteTextView mBehalfOfView;
    @BindView(R.id.progress_bar) ProgressBar progressBar;

    SessionManager sessionManager;
    DatabaseHelper databaseHelper;
    User user;

    @OnClick(R.id.submit_button)
    protected void doSumbitClicked() {
        attemptSubmit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_teacher_bank);
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
        if (user.getTeacherProfile().getTeacherBank() != null) {
            mBankNameView.setText(user.getTeacherProfile().getTeacherBank().getName());
            mNumberView.setText(user.getTeacherProfile().getTeacherBank().getNumber());
            mBranchView.setText(user.getTeacherProfile().getTeacherBank().getBranch());
            mBehalfOfView.setText(user.getTeacherProfile().getTeacherBank().getBehalfOf());
        }
    }

    private void setActionLeftIcon() {
        final Drawable iconLeft = ContextCompat.getDrawable(this, R.drawable.zzz_arrow_left);
        iconLeft.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        actionLeft.setImageDrawable(iconLeft);
    }

    private void attemptSubmit() {
        App.hideSoftKeyboard(this);

        String bankName = mBankNameView.getText().toString();
        String number = mNumberView.getText().toString();
        String branch = mBranchView.getText().toString();
        String behalfOf = mBehalfOfView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(behalfOf)) {
            mBehalfOfView.setError(getString(R.string.error_field_required));
            focusView = mBehalfOfView;
            cancel = true;
        }

        if (TextUtils.isEmpty(branch)) {
            mBranchView.setError(getString(R.string.error_field_required));
            focusView = mBranchView;
            cancel = true;
        }

        if (TextUtils.isEmpty(number)) {
            mNumberView.setError(getString(R.string.error_field_required));
            focusView = mNumberView;
            cancel = true;
        }

        if (TextUtils.isEmpty(bankName)) {
            mBankNameView.setError(getString(R.string.error_field_required));
            focusView = mBankNameView;
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
            UpdateTeacherBankRequest request = new UpdateTeacherBankRequest(bankName, number, branch, behalfOf);
            Call<LoginSuccess> call = service.updateTeacherBank(sessionManager.getUserCode(), request);
            call.enqueue(new Callback<LoginSuccess>() {
                @Override
                public void onResponse(Call<LoginSuccess> call, Response<LoginSuccess> response) {
                    Log.i("hendrigunawan", response.raw().toString());
                    if (response.raw().isSuccessful()) {

                        databaseHelper.createUser(response.body());

                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), TeacherProfileActivity.class);
                        startActivity(intent);

                    } else if (response.raw().code() == 400) {

                        Gson gson = new GsonBuilder().create();
                        UpdateTeacherBankResponse mError =new UpdateTeacherBankResponse();
                        try {
                            mError = gson.fromJson(response.errorBody().string(), UpdateTeacherBankResponse.class);
                            Toast.makeText(getApplicationContext(), mError.getMessage(), Toast.LENGTH_LONG).show();

//                            if (mError.getTeacherBankError().getBehalfOf() != null) {
//                                mBehalfOfView.setError(mError.getTeacherBankError().getBehalfOf());
//                                mBehalfOfView.requestFocus();
//                            }
//                            if (mError.getTeacherBankError().getBranch() != null) {
//                                mBranchView.setError(mError.getTeacherBankError().getBranch());
//                                mBranchView.requestFocus();
//                            }
//                            if (mError.getTeacherBankError().getNumber() != null) {
//                                mNumberView.setError(mError.getTeacherBankError().getNumber());
//                                mNumberView.requestFocus();
//                            }
//                            if (mError.getTeacherBankError().getName() != null) {
//                                mBankNameView.setError(mError.getTeacherBankError().getName());
//                                mBankNameView.requestFocus();
//                            }

                        } catch (IOException e) {
                            // handle failure to read error
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), "Update Bank Information failed", Toast.LENGTH_LONG).show();
                    }

                    progressBar.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onFailure(Call<LoginSuccess> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Update Bank Information failed, Please try again.", Toast.LENGTH_LONG).show();
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
}
