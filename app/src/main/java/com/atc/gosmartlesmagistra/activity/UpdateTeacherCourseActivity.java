package com.atc.gosmartlesmagistra.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.atc.gosmartlesmagistra.App;
import com.atc.gosmartlesmagistra.R;
import com.atc.gosmartlesmagistra.adapter.CourseLevelSpinnerAdapter;
import com.atc.gosmartlesmagistra.adapter.CourseSpinnerAdapter;
import com.atc.gosmartlesmagistra.api.UserApi;
import com.atc.gosmartlesmagistra.model.Course;
import com.atc.gosmartlesmagistra.model.CourseLevel;
import com.atc.gosmartlesmagistra.model.TeacherCourse;
import com.atc.gosmartlesmagistra.model.TeacherProfile;
import com.atc.gosmartlesmagistra.model.request.ChangePasswordRequest;
import com.atc.gosmartlesmagistra.model.response.ChangePasswordResponse;
import com.atc.gosmartlesmagistra.model.response.LoginSuccess;
import com.atc.gosmartlesmagistra.util.DatabaseHelper;
import com.atc.gosmartlesmagistra.util.SessionManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.List;

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

public class UpdateTeacherCourseActivity extends AppCompatActivity {

    @BindView(R.id.action_left) ImageButton actionLeft;
    @BindView(R.id.title_bar) TextView titleBar;
    @BindView(R.id.progress_bar) ProgressBar progressBar;
    @BindView(R.id.course_level) Spinner courseLevelSpinner;
    @BindView(R.id.course) Spinner courseSpinner;
    @BindView(R.id.submit_button) AppCompatButton submitButton;
    @BindView(R.id.delete_button) AppCompatButton deleteButton;
    @BindView(R.id.description) AutoCompleteTextView mDescriptionView;
    @BindView(R.id.expected_cost) EditText mExpectedCostView;

    SessionManager sessionManager;
    DatabaseHelper databaseHelper;
    List<CourseLevel> courseLevelList;
    List<Course> courseList;
    TeacherCourse teacherCourse;
    private Integer selectedCourseLevel;
    private Integer selectedCourse;
    private boolean isNewRecord;
    private Integer positionSelectedCourseLevel = 0;
    private Integer positionSelectedCourse = 0;

    @OnClick(R.id.submit_button)
    protected void submitClick() {
        attemptSubmit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_teacher_course);
        ButterKnife.bind(this);
        setActionLeftIcon();

        sessionManager = new SessionManager(this);
        databaseHelper = new DatabaseHelper(this);
        courseLevelList = databaseHelper.getCourseLevels();
        teacherCourse = (TeacherCourse) getIntent().getSerializableExtra("teacherCourse");
        isNewRecord = getIntent().getBooleanExtra("isNewRecord", false);

        CourseLevelSpinnerAdapter outletCitySpinnerAdapter = new CourseLevelSpinnerAdapter(this, courseLevelList);
        courseLevelSpinner.setAdapter(outletCitySpinnerAdapter);

        if (isNewRecord) {
            submitButton.setText(getString(R.string.action_submit));
            deleteButton.setVisibility(View.GONE);
        } else {
            deleteButton.setVisibility(View.VISIBLE);
            submitButton.setText(getString(R.string.action_update));
            mDescriptionView.setText(teacherCourse.getDescription());
            mExpectedCostView.setText(teacherCourse.getExpectedCost());

            for (int i=0;i<courseLevelList.size();i++) {
                Integer cityId = courseLevelList.get(i).getId();
                Log.i("hendri", cityId + " " + teacherCourse.getCourse().getCourseLevelId() + " " + courseLevelList.size());
                if (cityId.equals(teacherCourse.getCourse().getCourseLevelId())) {
                    Log.i("hendri", "masuk");
                    positionSelectedCourseLevel = i;
                    break;
                }
            }
            courseLevelSpinner.setSelection(positionSelectedCourseLevel);
            //loadCourseSpinner(positionSelectedCourseLevel);
        }
        courseLevelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCourseLevel = courseLevelList.get(position).getId();
                loadCourseSpinner(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });



    }

    private void loadCourseSpinner(Integer position) {
        courseList = courseLevelList.get(position).getCourses();
        CourseSpinnerAdapter outletSpinnerAdapter = new CourseSpinnerAdapter(this, courseList);
        courseSpinner.setAdapter(outletSpinnerAdapter);
        courseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCourse = courseList.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        if (!isNewRecord) {
            for (int i=0;i<courseList.size();i++) {
                Integer cityId = courseList.get(i).getId();
                if (cityId.equals(teacherCourse.getCourseId())) {
                    positionSelectedCourse = i;
                    break;
                }
            }
            courseSpinner.setSelection(positionSelectedCourse);
        }
    }

    private void setActionLeftIcon() {
        final Drawable iconLeft = ContextCompat.getDrawable(this, R.drawable.zzz_arrow_left);
        iconLeft.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        actionLeft.setImageDrawable(iconLeft);
    }

    private void attemptSubmit() {
        App.hideSoftKeyboard(this);

        boolean cancel = false;
        View focusView = null;

//        if (!TextUtils.isEmpty(confirmPassword) && !isPasswordValid(confirmPassword)) {
//            mConfirmPasswordView.setError(getString(R.string.error_invalid_password));
//            focusView = mConfirmPasswordView;
//            cancel = true;
//        }
//
//        if (TextUtils.isEmpty(confirmPassword)) {
//            mConfirmPasswordView.setError(getString(R.string.error_field_required));
//            focusView = mConfirmPasswordView;
//            cancel = true;
//        }
//
//        if (!TextUtils.isEmpty(newPassword) && !isPasswordValid(newPassword)) {
//            mNewPasswordView.setError(getString(R.string.error_invalid_password));
//            focusView = mNewPasswordView;
//            cancel = true;
//        }
//
//        if (TextUtils.isEmpty(newPassword)) {
//            mNewPasswordView.setError(getString(R.string.error_field_required));
//            focusView = mNewPasswordView;
//            cancel = true;
//        }
//
//        if (TextUtils.isEmpty(currentPassword)) {
//            mCurrentPasswordView.setError(getString(R.string.error_field_required));
//            focusView = mCurrentPasswordView;
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
//            UserApi service = retrofit.create(UserApi.class);
//            ChangePasswordRequest request = new ChangePasswordRequest(currentPassword, newPassword, confirmPassword);
//            Call<LoginSuccess> call = service.changePassword(sessionManager.getUserCode(), request);
//            call.enqueue(new Callback<LoginSuccess>() {
//                @Override
//                public void onResponse(Call<LoginSuccess> call, Response<LoginSuccess> response) {
//                    if (response.raw().isSuccessful()) {
//
//                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
//                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                        startActivity(intent);
//
//                    } else if (response.raw().code() == 400) {
//
//                        Gson gson = new GsonBuilder().create();
//                        ChangePasswordResponse mError =new ChangePasswordResponse();
//                        try {
//                            mError = gson.fromJson(response.errorBody().string(),ChangePasswordResponse.class);
//                            Toast.makeText(getApplicationContext(), mError.getMessage(), Toast.LENGTH_LONG).show();
//
//                        } catch (IOException e) {
//                            // handle failure to read error
//                        }
//
//                    } else {
//                        Toast.makeText(getApplicationContext(), "Change password failed", Toast.LENGTH_LONG).show();
//                    }
//
//                    progressBar.setVisibility(View.INVISIBLE);
//                }
//
//                @Override
//                public void onFailure(Call<LoginSuccess> call, Throwable t) {
//                    Toast.makeText(getApplicationContext(), "Change password failed, Please try again.", Toast.LENGTH_LONG).show();
//                    progressBar.setVisibility(View.INVISIBLE);
//                }
//            });
//
//        }
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 5;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, TeacherProfileActivity.class);
        intent.putExtra("setToCourse", true);
        startActivity(intent);
    }

    @OnClick(R.id.action_left)
    public void doActionLeft(View view) {
        Intent intent = new Intent(this, TeacherProfileActivity.class);
        intent.putExtra("setToCourse", true);
        startActivity(intent);
    }
}
