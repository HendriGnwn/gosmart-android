package com.atc.gosmartlesmagistra.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.atc.gosmartlesmagistra.fragment.TeacherCourseFragment;
import com.atc.gosmartlesmagistra.fragment.TeacherHistoryFragment;
import com.atc.gosmartlesmagistra.fragment.TeacherInfoFragment;


/**
 * Created by hendrigunawan on 5/31/17.
 */
public class PagerAdapter extends FragmentStatePagerAdapter {

    int numOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.numOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        super.getPageTitle(position);

        switch (position) {
            case 0:
                TeacherInfoFragment teacherInfoFragment = new TeacherInfoFragment();
                return teacherInfoFragment;
            case 1:
                TeacherCourseFragment teacherCourseFragment = new TeacherCourseFragment();
                return teacherCourseFragment;
            case 2:
                TeacherHistoryFragment teacherHistoryFragment = new TeacherHistoryFragment();
                return teacherHistoryFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }


}
