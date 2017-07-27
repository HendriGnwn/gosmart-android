package com.atc.gosmartlesmagistra.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.atc.gosmartlesmagistra.R;

import butterknife.ButterKnife;

/**
 * Created by cranium-01 on 27/07/17.
 */

public class TeacherHistoryFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_teacher_history, container, false);
        ButterKnife.bind(this, view);

        return view;
    }
}
