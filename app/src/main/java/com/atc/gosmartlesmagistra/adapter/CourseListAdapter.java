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
import com.atc.gosmartlesmagistra.model.Course;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ACER on 01/08/2017.
 */

public class CourseListAdapter extends RecyclerView.Adapter<CourseListAdapter.MyViewHolder> {

    private Context mContext;
    private List<Course> list;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, teacherAvailability, courseLevel, section;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            teacherAvailability = (TextView) view.findViewById(R.id.teacher_availability);
            courseLevel = (TextView) view.findViewById(R.id.course_level);
            section = (TextView) view.findViewById(R.id.section);


            view.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    Intent intent;
//                    intent = new Intent(mContext, WhatsNewDetailActivity.class);
//                    Article article = articleList.get(getAdapterPosition());
//                    intent.putExtra("article", article);
//                    mContext.startActivity(intent);
                }
            });
        }
    }

    public CourseListAdapter(Context mContext, List<Course> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.course_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        Course course = list.get(position);
        holder.name.setText(course.getName());
        holder.section.setText(course.getSection() + " " + mContext.getString(R.string.section));
        holder.courseLevel.setText(course.getCourseLevel().getName());
        holder.teacherAvailability.setText(course.getTeacherAvailability() + " " + mContext.getString(R.string.teacher_availability));
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
    public void addAll(List<Course> list) {
        list.addAll(list);
        notifyDataSetChanged();
    }

    public void add(Course course) {
        list.add(course);
        notifyDataSetChanged();
    }
}
