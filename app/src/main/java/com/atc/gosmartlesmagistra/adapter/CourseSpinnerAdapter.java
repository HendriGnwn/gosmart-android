package com.atc.gosmartlesmagistra.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.atc.gosmartlesmagistra.R;
import com.atc.gosmartlesmagistra.model.Course;

import java.util.List;

/**
 * Created by hendrigunwan on 6/7/17.
 */

public class CourseSpinnerAdapter extends BaseAdapter implements SpinnerAdapter {

    private final Context activity;
    private List<Course> courses;

    LayoutInflater flater;

    public CourseSpinnerAdapter(Context context, List<Course> courses) {
        this.courses = courses;
        activity = context;
        flater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount()
    {
        return courses.size();
    }

    public Object getItem(int i)
    {
        return courses.get(i);
    }

    public long getItemId(int i)
    {
        return (long)i;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        RelativeLayout rtlContainer = new RelativeLayout(activity);
        View rowview = flater.inflate(R.layout.spinner_item_dialog, null,true);
        TextView txt = (TextView) rowview.findViewById(R.id.name);
        txt.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        Course course = courses.get(position);
        txt.setText(course.getName());
        txt.setTextColor(activity.getResources().getColor(R.color.colorBlackSecondary));
        rtlContainer.addView(rowview);
        rtlContainer.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        return rtlContainer;
    }

    public View getView(int i, View view, ViewGroup viewgroup) {
        RelativeLayout rtlContainer = new RelativeLayout(activity);
        View rowview = flater.inflate(R.layout.spinner_item, null,true);
        TextView txt = (TextView) rowview.findViewById(R.id.name);
        Course course = courses.get(i);
        txt.setText(course.getName());
        txt.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        txt.setTextColor(activity.getResources().getColor(R.color.colorBlackSecondary));
        rtlContainer.addView(rowview);
        rtlContainer.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        return rtlContainer;
    }
}