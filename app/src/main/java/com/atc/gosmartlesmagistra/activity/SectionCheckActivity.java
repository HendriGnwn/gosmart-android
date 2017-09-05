package com.atc.gosmartlesmagistra.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.atc.gosmartlesmagistra.App;
import com.atc.gosmartlesmagistra.R;
import com.atc.gosmartlesmagistra.api.RequestApi;
import com.atc.gosmartlesmagistra.model.PrivateModel;
import com.atc.gosmartlesmagistra.model.Schedule;
import com.atc.gosmartlesmagistra.model.User;
import com.atc.gosmartlesmagistra.model.request.ReviewRequest;
import com.atc.gosmartlesmagistra.model.response.ReviewSuccess;
import com.atc.gosmartlesmagistra.model.response.UpdateTeacherProfileResponse;
import com.atc.gosmartlesmagistra.util.DatabaseHelper;
import com.atc.gosmartlesmagistra.util.InputFilterMinMax;
import com.atc.gosmartlesmagistra.util.SessionManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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

public class SectionCheckActivity extends AppCompatActivity {

    @BindView(R.id.action_left) ImageButton actionLeft;
    @BindView(R.id.rate) CheckBox mRateView;
    @BindView(R.id.description) AutoCompleteTextView mReviewView;
    @BindView(R.id.progress_bar) ProgressBar progressBar;

    SessionManager sessionManager;
    DatabaseHelper databaseHelper;
    Schedule schedule;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section_check);
        ButterKnife.bind(this);
        setActionLeftIcon();

        sessionManager = new SessionManager(this);
        databaseHelper = new DatabaseHelper(this);

        if (sessionManager.isLoggedIn()) {
            user = databaseHelper.getUser(sessionManager.getUserCode());
        }

        mRateView.setFilters(new InputFilter[] {
                new InputFilterMinMax("1", "5")
        });

        schedule = (Schedule) getIntent().getSerializableExtra("schedule");

    }

    private void setActionLeftIcon() {
        final Drawable iconLeft = ContextCompat.getDrawable(this, R.drawable.zzz_arrow_left);
        iconLeft.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        actionLeft.setImageDrawable(iconLeft);
    }

    private void attemptSubmit() {
//        App.hideSoftKeyboard(this);
//
//        String rate = mRateView.getText().toString();
//        String review = mReviewView.getText().toString();
//
//        boolean cancel = false;
//        View focusView = null;
//
//        if (TextUtils.isEmpty(review)) {
//            mReviewView.setError(getString(R.string.error_field_required));
//            focusView = mReviewView;
//            cancel = true;
//        }
//
//        if (TextUtils.isEmpty(rate)) {
//            mRateView.setError(getString(R.string.error_field_required));
//            focusView = mRateView;
//            cancel = true;
//        }
//
//        if (cancel) {
//            // There was an error; don't attempt login and focus the first
//            // form field with an error.
//            focusView.requestFocus();
//        } else {
//            progressBar.setVisibility(View.VISIBLE);
//
//            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
//                @Override
//                public okhttp3.Response intercept(Chain chain) throws IOException {
//                    Request newRequest  = chain.request().newBuilder()
//                            .addHeader("Authorization", "Bearer " + sessionManager.getUserToken())
//                            .addHeader("Content-Type", "application/json")
//                            .build();
//                    return chain.proceed(newRequest);
//                }
//            }).build();
//            Retrofit retrofit = new Retrofit.Builder()
//                    .client(client)
//                    .baseUrl(App.API)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build();
//            RequestApi service = retrofit.create(RequestApi.class);
//            ReviewRequest request = new ReviewRequest(sessionManager.getUserToken(), privateModel.getId(), rate, review);
//            Call<ReviewSuccess> call = service.review(sessionManager.getUserToken(), request);
//            call.enqueue(new Callback<ReviewSuccess>() {
//                @Override
//                public void onResponse(Call<ReviewSuccess> call, Response<ReviewSuccess> response) {
//                    if (response.raw().isSuccessful()) {
//                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
//                        privateModel = response.body().getPrivateModel();
//                        Intent intent = new Intent(getApplicationContext(), PrivateDetailActivity.class);
//                        intent.putExtra("privateModel", privateModel);
//                        startActivity(intent);
//
//                    } else if (response.raw().code() == 400) {
//
//                        Gson gson = new GsonBuilder().create();
//                        UpdateTeacherProfileResponse mError =new UpdateTeacherProfileResponse();
//                        try {
//                            mError = gson.fromJson(response.errorBody().string(),UpdateTeacherProfileResponse.class);
//                            Toast.makeText(getApplicationContext(), mError.getMessage(), Toast.LENGTH_LONG).show();
//                        } catch (IOException e) {
//                            // handle failure to read error
//                        }
//
//                    } else {
//                        Toast.makeText(getApplicationContext(), "Review failed", Toast.LENGTH_LONG).show();
//                    }
//
//                    progressBar.setVisibility(View.INVISIBLE);
//                }
//
//                @Override
//                public void onFailure(Call<ReviewSuccess> call, Throwable t) {
//                    Toast.makeText(getApplicationContext(), "Review failed, Please try again.", Toast.LENGTH_LONG).show();
//                    progressBar.setVisibility(View.INVISIBLE);
//                }
//            });
//
//        }
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
