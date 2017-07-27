package com.atc.gosmartlesmagistra.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.atc.gosmartlesmagistra.R;
import com.atc.gosmartlesmagistra.activity.TeacherProfileActivity;
import com.atc.gosmartlesmagistra.model.User;
import com.atc.gosmartlesmagistra.util.DatabaseHelper;
import com.atc.gosmartlesmagistra.util.SessionManager;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cranium-01 on 27/07/17.
 */

public class TeacherInfoFragment extends Fragment {

    @BindView(R.id.first_name) TextView firstName;
    @BindView(R.id.last_name) TextView lastName;

    DatabaseHelper databaseHelper;
    SessionManager sessionManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_teacher_info, container, false);
        ButterKnife.bind(this, view);

        sessionManager = new SessionManager(getActivity());
        databaseHelper = new DatabaseHelper(getActivity());

        User user = databaseHelper.getUser(sessionManager.getUserCode());

        firstName.setText(user.getFirstName());
        lastName.setText(user.getLastName());

        return view;
    }
}
