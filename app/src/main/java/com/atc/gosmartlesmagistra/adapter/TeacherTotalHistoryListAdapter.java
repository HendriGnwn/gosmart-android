package com.atc.gosmartlesmagistra.adapter;

import android.content.Context;
import android.content.Intent;
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

        public MyViewHolder(View view) {
            super(view);
            operator = (ImageView) view.findViewById(R.id.operator);
            total = (TextView) view.findViewById(R.id.total);
            privateName = (TextView) view.findViewById(R.id.private_name);
            created = (TextView) view.findViewById(R.id.created);

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

        TeacherTotalHistory history = list.get(position);
        holder.total.setText(history.getFormattedTotal());
        holder.created.setText(history.getFormattedCreatedAt());
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
}
