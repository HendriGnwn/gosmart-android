package com.atc.gosmartlesmagistra.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.atc.gosmartlesmagistra.R;
import com.atc.gosmartlesmagistra.model.User;
import com.atc.gosmartlesmagistra.util.DatabaseHelper;
import com.atc.gosmartlesmagistra.util.SessionManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hendrigunawan on 7/4/17.
 */

public class EditProfileStudentActivity extends AppCompatActivity {

    @BindView(R.id.action_left) ImageButton actionLeft;
    @BindView(R.id.change_password_button) AppCompatButton changePasswordButton;
    @BindView(R.id.first_name) AutoCompleteTextView mFirstNameView;
    @BindView(R.id.last_name) AutoCompleteTextView mLastNameView;
    @BindView(R.id.mobile_phone) AutoCompleteTextView mMobilePhoneView;
    @BindView(R.id.address) AutoCompleteTextView mAddressView;
    @BindView(R.id.email) AutoCompleteTextView mEmailView;

    SessionManager sessionManager;
    DatabaseHelper databaseHelper;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_student);
        ButterKnife.bind(this);
        setActionLeftIcon();

        sessionManager = new SessionManager(this);
        databaseHelper = new DatabaseHelper(this);

        if (!sessionManager.isLoggedIn()) {
            Toast.makeText(this, "You must login", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

        user = databaseHelper.getUserByUniqueNumber(sessionManager.getUserCode());

        mFirstNameView.setText(user.getFirstName());
        mLastNameView.setText(user.getLastName());
        mMobilePhoneView.setText(user.getPhoneNumber());
        mAddressView.setText(user.getAddress());
        mEmailView.setText(user.getEmail());
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

    @OnClick(R.id.change_password_button)
    protected void changePassword() {
        Intent intent = new Intent(getApplicationContext(), ChangePasswordActivity.class);
        startActivity(intent);
    }
}
