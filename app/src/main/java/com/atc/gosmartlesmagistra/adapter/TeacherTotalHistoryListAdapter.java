package com.atc.gosmartlesmagistra.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.atc.gosmartlesmagistra.R;
import com.atc.gosmartlesmagistra.activity.UpdateTeacherCourseActivity;
import com.atc.gosmartlesmagistra.model.TeacherCourse;
import com.atc.gosmartlesmagistra.model.TeacherTotalHistory;

import java.util.List;

/**
 * Created by ACER on 01/08/2017.
 */

public class TeacherTotalHistoryListAdapter extends RecyclerView.Adapter<TeacherTotalHistoryListAdapter.MyViewHolder> {

    private Context mContext;
    private List<TeacherTotalHistory> list;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView total, privateName, created;
        public ImageView operator;
        //public boolean isAdd;

        public MyViewHolder(View view) {
            super(view);
            operator = (ImageView) view.findViewById(R.id.operator);
            total = (TextView) view.findViewById(R.id.total);
            privateName = (TextView) view.findViewById(R.id.private_name);
            created = (TextView) view.findViewById(R.id.created);
            //isAdd = list.get(getAdapterPosition()).getOperation() == 1 ? true : false;

            view.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
//                    Intent intent = new Intent(mContext, UpdateTeacherCourseActivity.class);
//                    TeacherTotalHistory history = list.get(getAdapterPosition());
//                    intent.putExtra("history", history);
//                    mContext.startActivity(intent);
                }
            });
        }
    }

    public TeacherTotalHistoryListAdapter(Context mContext, List<TeacherTotalHistory> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.teacher_total_history_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        Integer primary = mContext.getResources().getColor(R.color.colorPrimary);
        Integer accent = mContext.getResources().getColor(R.color.colorAccent);
        Integer success = mContext.getResources().getColor(R.color.colorGreen);
        TeacherTotalHistory history = list.get(position);
        holder.total.setText(history.getFormattedTotal());
        holder.created.setText(history.getDescriptionOperationTimeAt(mContext));

        if (history.getOperation() == 1) {
            holder.total.setTextColor(primary);
            setOperatorPlus(holder);
        } else {
            holder.total.setTextColor(history.getStatusColor(mContext));
            setOperatorMinus(holder);
            holder.privateName.setText(history.getStatusText());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    // Clean all elements of the recycler
    public void clear() {
        list.clear();
        notifyDataSetChanged();
    }

    // Add a list of items
    public void addAll(List<TeacherTotalHistory> list) {
        list.addAll(list);
        notifyDataSetChanged();
    }

    public void add(TeacherTotalHistory course) {
        list.add(course);
        notifyDataSetChanged();
    }

    private void setOperatorMinus(final MyViewHolder holder) {
        final Drawable minus = ContextCompat.getDrawable(mContext, R.drawable.zzz_minus_circle_outline);
        minus.setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
        holder.operator.setImageDrawable(minus);
    }

    private void setOperatorPlus(final MyViewHolder holder) {
        final Drawable minus = ContextCompat.getDrawable(mContext, R.drawable.zzz_plus_circle_outline);
        minus.setColorFilter(mContext.getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
        holder.operator.setImageDrawable(minus);
    }
}
