package com.atc.gosmartlesmagistra.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.atc.gosmartlesmagistra.R;
import com.atc.gosmartlesmagistra.model.PrivateModel;
import com.atc.gosmartlesmagistra.model.StudentOnDetail;
import com.atc.gosmartlesmagistra.model.TeacherCourse;
import com.atc.gosmartlesmagistra.model.TeacherOnDetail;
import com.atc.gosmartlesmagistra.model.User;
import com.atc.gosmartlesmagistra.util.DatabaseHelper;
import com.atc.gosmartlesmagistra.util.SessionManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Retrofit;

/**
 * Created by hendrigunawan on 7/4/17.
 */

public class PrivateDetailActivity extends AppCompatActivity {

    @BindView(R.id.action_left) ImageButton actionLeft;
    @BindView(R.id.title_bar) TextView titleBar;
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
    @BindView(R.id.linear_student_on_detail) LinearLayout linearStudentOnDetail;
    @BindView(R.id.linear_teacher_on_detail) LinearLayout linearTeacherOnDetail;
    @BindView(R.id.review_button) AppCompatButton reviewButton;

    TeacherCourse teacherCourse;
    SessionManager sessionManager;
    DatabaseHelper databaseHelper;
    Retrofit retrofit;
    PrivateModel privateModel;
    User user;

    int _intLineCount;
    private List<AutoCompleteTextView> autoCompleteTextViewList = new ArrayList<AutoCompleteTextView>();
    private List<LinearLayout> linearLayoutList = new ArrayList<LinearLayout>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_detail);
        ButterKnife.bind(this);
        setActionLeftIcon();

        sessionManager = new SessionManager(this);
        databaseHelper = new DatabaseHelper(this);
        user = databaseHelper.getUser(sessionManager.getUserCode());
        privateModel = (PrivateModel) getIntent().getSerializableExtra("privateModel");
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

        reviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (privateModel.getStatus() != PrivateModel.statusDone) {
                    Toast.makeText(getApplicationContext(), "Anda bisa beri ulasan jika Status sudah Done / Selesai", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(getApplicationContext(), ReviewActivity.class);
                    intent.putExtra("privateModel", privateModel);
                    startActivity(intent);
                }
            }
        });

        manageOnDetails();
    }

    private void manageOnDetails() {
        linearStudentOnDetail.removeAllViews();
        linearTeacherOnDetail.removeAllViews();
        Integer count = 1;
        for (StudentOnDetail studentOnDetail : privateModel.getPrivateDetails().get(0).getStudentOnDetails()) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(dpToPx(4),dpToPx(4), 0, 0);
            TextView textView = new TextView(this);
            textView.setLayoutParams(layoutParams);
            textView.setTextColor(getResources().getColor(R.color.colorBlack));
            textView.setAllCaps(true);
            textView.setText(getString(R.string.section) + " " + count++ + ": ");
            linearStudentOnDetail.addView(textView);

            TextView onAt = new TextView(this);
            onAt.setLayoutParams(layoutParams);
            onAt.setTextColor(this.getResources().getColor(R.color.colorBlackSecondary));
            onAt.setText(this.getString(R.string.on_at) + ": " + studentOnDetail.getFormattedOnAt());
            linearStudentOnDetail.addView(onAt);

            TextView checkView = new TextView(this);
            checkView.setLayoutParams(layoutParams);
            checkView.setTextColor(getResources().getColor(R.color.colorBlackSecondary));
            checkView.setText(getString(R.string.check) + ": " + studentOnDetail.getCheckText());
            linearStudentOnDetail.addView(checkView);

            TextView checkAt = new TextView(this);
            checkAt.setLayoutParams(layoutParams);
            checkAt.setTextColor(getResources().getColor(R.color.colorBlackSecondary));
            checkAt.setText(getString(R.string.check_at) + ": " + studentOnDetail.getFormattedCheckAt());
            linearStudentOnDetail.addView(checkAt);

            TextView description = new TextView(this);
            description.setLayoutParams(layoutParams);
            description.setTextColor(getResources().getColor(R.color.colorBlackSecondary));
            description.setText(getString(R.string.on_at_description) + ": " + studentOnDetail.getDescription());
            linearStudentOnDetail.addView(description);
        }

        count = 1;
        for (TeacherOnDetail teacherOnDetail : privateModel.getPrivateDetails().get(0).getTeacherOnDetails()) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(dpToPx(4),dpToPx(4), 0, 0);
            TextView textView = new TextView(this);
            textView.setLayoutParams(layoutParams);
            textView.setTextColor(getResources().getColor(R.color.colorBlack));
            textView.setAllCaps(true);
            textView.setText(getString(R.string.section) + ": " + count++ + ": ");
            linearTeacherOnDetail.addView(textView);

            TextView onAt = new TextView(this);
            onAt.setLayoutParams(layoutParams);
            onAt.setTextColor(getResources().getColor(R.color.colorBlackSecondary));
            onAt.setText(getString(R.string.on_at) + ": " + teacherOnDetail.getFormattedOnAt());
            linearTeacherOnDetail.addView(onAt);

            TextView checkView = new TextView(this);
            checkView.setLayoutParams(layoutParams);
            checkView.setTextColor(getResources().getColor(R.color.colorBlackSecondary));
            checkView.setText(getString(R.string.check) + ": " + teacherOnDetail.getCheckText());
            linearTeacherOnDetail.addView(checkView);

            TextView checkAt = new TextView(this);
            checkAt.setLayoutParams(layoutParams);
            checkAt.setTextColor(getResources().getColor(R.color.colorBlackSecondary));
            checkAt.setText(getString(R.string.check_at) + ": " + teacherOnDetail.getFormattedCheckAt());
            linearTeacherOnDetail.addView(checkAt);

            TextView description = new TextView(this);
            description.setLayoutParams(layoutParams);
            description.setTextColor(getResources().getColor(R.color.colorBlackSecondary));
            description.setText(getString(R.string.on_at_description) + ": " + teacherOnDetail.getDescription());
            linearTeacherOnDetail.addView(description);
        }
    }

    private void setActionLeftIcon() {
        final Drawable iconLeft = ContextCompat.getDrawable(this, R.drawable.zzz_arrow_left);
        iconLeft.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        actionLeft.setImageDrawable(iconLeft);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @OnClick(R.id.action_left)
    public void doActionLeft(View view) {
        onBackPressed();
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
