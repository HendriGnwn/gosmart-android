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
import com.atc.gosmartlesmagistra.model.Bank;
import com.atc.gosmartlesmagistra.model.CourseLevel;

import java.util.List;

/**
 * Created by hendrigunwan on 6/7/17.
 */

public class BankSpinnerAdapter extends BaseAdapter implements SpinnerAdapter {

    private final Context activity;
    private List<Bank> courseLevels;

    LayoutInflater flater;

    public BankSpinnerAdapter(Context context, List<Bank> courseLevels) {
        this.courseLevels = courseLevels;
        activity = context;
        flater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount()
    {
        return courseLevels.size();
    }

    public Object getItem(int i)
    {
        return courseLevels.get(i);
    }

    public long getItemId(int i)
    {
        return (long)i;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        RelativeLayout rtlContainer = new RelativeLayout(activity);
        rtlContainer.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        View rowview = flater.inflate(R.layout.spinner_item_dialog, null,true);
        TextView txt = (TextView) rowview.findViewById(R.id.name);
        Bank courseLevel = courseLevels.get(position);
        txt.setText(courseLevel.getName());
        txt.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        rtlContainer.addView(rowview);
        return rtlContainer;
    }

    public View getView(int i, View view, ViewGroup viewgroup) {
        RelativeLayout rtlContainer = new RelativeLayout(activity);
        rtlContainer.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        View rowview = flater.inflate(R.layout.spinner_item, null,true);
        TextView txt = (TextView) rowview.findViewById(R.id.name);
        Bank courseLevel = courseLevels.get(i);
        txt.setText(courseLevel.getName());
        txt.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        rtlContainer.addView(rowview);
        return rtlContainer;
    }
}