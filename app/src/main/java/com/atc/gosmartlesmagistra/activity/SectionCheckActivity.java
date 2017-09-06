package com.atc.gosmartlesmagistra.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.atc.gosmartlesmagistra.App;
import com.atc.gosmartlesmagistra.R;
import com.atc.gosmartlesmagistra.api.PrivateApi;
import com.atc.gosmartlesmagistra.api.RequestApi;
import com.atc.gosmartlesmagistra.model.DateDetail;
import com.atc.gosmartlesmagistra.model.PrivateModel;
import com.atc.gosmartlesmagistra.model.Schedule;
import com.atc.gosmartlesmagistra.model.TeacherCourse;
import com.atc.gosmartlesmagistra.model.User;
import com.atc.gosmartlesmagistra.model.request.ReviewRequest;
import com.atc.gosmartlesmagistra.model.request.SectionCheckRequest;
import com.atc.gosmartlesmagistra.model.response.PrivateOrderHistorySuccess;
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
    @BindView(R.id.private_code) AutoCompleteTextView mPrivateCodeView;
    @BindView(R.id.start_date) AutoCompleteTextView mStartDateView;
    @BindView(R.id.end_date) AutoCompleteTextView mEndDateView;
    @BindView(R.id.status) AutoCompleteTextView mStatusView;
    @BindView(R.id.course_name) AutoCompleteTextView mCourseNameView;
    @BindView(R.id.course_description) AutoCompleteTextView mCourseDescriptionView;
    @BindView(R.id.course_section) AutoCompleteTextView mCourseSectionView;
    @BindView(R.id.student_name) AutoCompleteTextView mStudentNameView;
    @BindView(R.id.student_phone) AutoCompleteTextView mStudentPhoneNumberView;
    @BindView(R.id.student_address) AutoCompleteTextView mStudentAddressView;
    @BindView(R.id.teacher_name) AutoCompleteTextView mTeacherNameView;
    @BindView(R.id.teacher_phone) AutoCompleteTextView mTeacherPhoneNumberView;
    @BindView(R.id.teacher_address) AutoCompleteTextView mTeacherAddressView;
    @BindView(R.id.teacher_bio) AutoCompleteTextView mTeacherBioView;
    @BindView(R.id.section) AutoCompleteTextView mSectionView;
    @BindView(R.id.check_at) TextView mCheckAtView;

    SessionManager sessionManager;
    DatabaseHelper databaseHelper;
    Schedule schedule;
    PrivateModel privateModel;
    TeacherCourse teacherCourse;
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
        privateModel = schedule.getPrivateModel();
        teacherCourse = privateModel.getPrivateDetails().get(0).getTeacherCourse();

        mPrivateCodeView.setText(privateModel.getCode());
        mStartDateView.setText(privateModel.getFormattedStartDate());
        mEndDateView.setText(privateModel.getFormattedEndDate());
        mStatusView.setText(privateModel.getStatusText());

        mCourseNameView.setText(teacherCourse.getCourse().getName());
        mCourseSectionView.setText(teacherCourse.getCourse().getSection() + ", " + teacherCourse.getCourse().getSectionTime() + " " + getString(R.string.time));
        mCourseDescriptionView.setText(teacherCourse.getCourse().getDescription());

        mStudentNameView.setText(privateModel.getStudent().getFullName());
        mStudentAddressView.setText(privateModel.getStudent().getAddress());
        mStudentPhoneNumberView.setText(privateModel.getStudent().getPhoneNumber());

        mTeacherNameView.setText(privateModel.getTeacher().getFullName());
        mTeacherAddressView.setText(privateModel.getTeacher().getAddress());
        mTeacherPhoneNumberView.setText(privateModel.getTeacher().getPhoneNumber());
        mTeacherBioView.setText(privateModel.getTeacher().getTeacherProfile().getBio());

        mSectionView.setText(schedule.getFormattedDate());

        if (schedule.getDateDetail().getCheck().equals(DateDetail.checkTrue)) {
            mRateView.setChecked(true);
            mCheckAtView.setText(getResources().getString(R.string.check_at) + ": " + schedule.getDateDetail().getFormattedCheckAt());
        } else {
            mRateView.setChecked(false);
            mCheckAtView.setText("");
        }

        if (schedule.getDateDetail().getDescription() != null) {
            mReviewView.setText(schedule.getDateDetail().getDescription());
        }
    }

    private void setActionLeftIcon() {
        final Drawable iconLeft = ContextCompat.getDrawable(this, R.drawable.zzz_arrow_left);
        iconLeft.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        actionLeft.setImageDrawable(iconLeft);
    }

    private void attemptSubmit() {
        App.hideSoftKeyboard(this);

        String review = mReviewView.getText().toString();

        Integer checklist;
        if (mRateView.isChecked()) {
            checklist = DateDetail.checkTrue;
        } else {
            checklist = DateDetail.checkFalse;
        }


        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(review)) {
            mReviewView.setError(getString(R.string.error_field_required));
            focusView = mReviewView;
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
            PrivateApi service = retrofit.create(PrivateApi.class);
            SectionCheckRequest request = new SectionCheckRequest(privateModel.getPrivateDetails().get(0).getId(), schedule.getDateDetail().getOnAt(), checklist, review);
            Call<PrivateOrderHistorySuccess> call = service.sectionCheck(sessionManager.getUserCode(), privateModel.getId(), request);
            call.enqueue(new Callback<PrivateOrderHistorySuccess>() {
                @Override
                public void onResponse(Call<PrivateOrderHistorySuccess> call, Response<PrivateOrderHistorySuccess> response) {
                    if (response.raw().isSuccessful()) {
                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
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
                        Toast.makeText(getApplicationContext(), "Section Done failed", Toast.LENGTH_LONG).show();
                    }
                    Log.i("cranium", response.raw().toString());

                    progressBar.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onFailure(Call<PrivateOrderHistorySuccess> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Section Done failed, Please try again.", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.INVISIBLE);
                }
            });

        }
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
