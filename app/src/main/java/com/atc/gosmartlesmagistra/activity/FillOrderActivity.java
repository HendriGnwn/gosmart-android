package com.atc.gosmartlesmagistra.activity;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.atc.gosmartlesmagistra.R;
import com.atc.gosmartlesmagistra.model.TeacherCourse;
import com.atc.gosmartlesmagistra.util.DatabaseHelper;
import com.atc.gosmartlesmagistra.util.SessionManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hendrigunawan on 7/4/17.
 */

public class FillOrderActivity extends AppCompatActivity {

    @BindView(R.id.action_left) ImageButton actionLeft;
    @BindView(R.id.title_bar) TextView titleBar;
    @BindView(R.id.course_name) AutoCompleteTextView mCourseNameView;
    @BindView(R.id.course_description) AutoCompleteTextView mCourseDescriptionView;
    @BindView(R.id.course_section) AutoCompleteTextView mCourseSectionView;
    @BindView(R.id.teacher_name) AutoCompleteTextView mTeacherNameView;
    @BindView(R.id.teacher_phone) AutoCompleteTextView mTeacherPhoneNumberView;
    @BindView(R.id.teacher_address) AutoCompleteTextView mTeacherAddressView;
    @BindView(R.id.teacher_bio) AutoCompleteTextView mTeacherBioView;

    TeacherCourse teacherCourse;
    SessionManager sessionManager;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_order);
        ButterKnife.bind(this);
        setActionLeftIcon();

        sessionManager = new SessionManager(this);
        databaseHelper = new DatabaseHelper(this);
        teacherCourse = (TeacherCourse) getIntent().getSerializableExtra("teacherCourse");
        mCourseNameView.setText(teacherCourse.getCourse().getName());
        mCourseSectionView.setText(teacherCourse.getCourse().getSection() + ", " + teacherCourse.getCourse().getSectionTime() + " " + getString(R.string.time));
        mCourseDescriptionView.setText(teacherCourse.getCourse().getDescription());
        mTeacherNameView.setText(teacherCourse.getUser().getFullName());
        mTeacherPhoneNumberView.setText(teacherCourse.getUser().getPhoneNumber());
        mTeacherAddressView.setText(teacherCourse.getUser().getAddress());
        mTeacherBioView.setText(teacherCourse.getUser().getTeacherProfile().getBio());
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
}
