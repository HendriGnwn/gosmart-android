package com.atc.gosmartlesmagistra.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.atc.gosmartlesmagistra.App;
import com.atc.gosmartlesmagistra.R;
import com.atc.gosmartlesmagistra.activity.EditProfileTeacherActivity;
import com.atc.gosmartlesmagistra.activity.TeacherProfileActivity;
import com.atc.gosmartlesmagistra.activity.UpdateTeacherBankActivity;
import com.atc.gosmartlesmagistra.model.User;
import com.atc.gosmartlesmagistra.util.DatabaseHelper;
import com.atc.gosmartlesmagistra.util.SessionManager;
import com.pkmmte.view.CircularImageView;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by cranium-01 on 27/07/17.
 */

public class TeacherInfoFragment extends Fragment {

    @BindView(R.id.first_name) TextView firstName;
    @BindView(R.id.last_name) TextView lastName;
    @BindView(R.id.title) TextView title;
    @BindView(R.id.phone_number) TextView phoneNumber;
    @BindView(R.id.email) TextView email;
    @BindView(R.id.address) TextView address;
    @BindView(R.id.izajah_number) TextView izajahNumber;
    @BindView(R.id.graduated) TextView graduated;
    @BindView(R.id.total) TextView total;
    @BindView(R.id.total_updated_at) TextView totalUpdatedAt;
    @BindView(R.id.bio) TextView bio;
    @BindView(R.id.image) CircularImageView imageView;

    DatabaseHelper databaseHelper;
    SessionManager sessionManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_teacher_info, container, false);
        ButterKnife.bind(this, view);

        sessionManager = new SessionManager(getActivity());
        databaseHelper = new DatabaseHelper(getActivity());

        User user = databaseHelper.getUser(sessionManager.getUserCode());

        String[] titles = getResources().getStringArray(R.array.title_arrays);

        firstName.setText(user.getFirstName());
        lastName.setText(user.getLastName());
        phoneNumber.setText(user.getPhoneNumber());
        email.setText(user.getEmail());
        address.setText(user.getAddress());

        if (!user.getTeacherProfile().getTitle().equals(null)) {
            title.setText(titles[user.getTeacherProfile().getTitle() - 1]);
        }
        if (user.getTeacherProfile().getIzajahNumber() != null) {
            izajahNumber.setText(user.getTeacherProfile().getIzajahNumber());
        } else {
            izajahNumber.setText(null);
        }
        if (user.getTeacherProfile().getGraduated() != null) {
            graduated.setText(user.getTeacherProfile().getGraduated());
        } else {
            graduated.setText(null);
        }
        total.setText(user.getTeacherProfile().getFormattedTotal());
        totalUpdatedAt.setText(user.getTeacherProfile().getTotalUpdatedAt());
        if (user.getTeacherProfile().getBio() != null) {
            bio.setText(user.getTeacherProfile().getBio());
        } else {
            bio.setText(null);
        }
        Picasso.with(getContext()).load(App.URL + "/files/users/" + user.getPhoto()).error(R.drawable.user).into(imageView);

        return view;
    }

    @OnClick(R.id.update_profile_button)
    protected void updateProfileClick() {
        Intent intent = new Intent(getActivity(), EditProfileTeacherActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.update_bank_button)
    protected void updateBankClick() {
        Intent intent = new Intent(getActivity(), UpdateTeacherBankActivity.class);
        startActivity(intent);
    }
}
