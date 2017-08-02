package com.atc.gosmartlesmagistra.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.atc.gosmartlesmagistra.App;
import com.atc.gosmartlesmagistra.R;
import com.atc.gosmartlesmagistra.model.Course;
import com.atc.gosmartlesmagistra.model.TeacherCourse;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ACER on 01/08/2017.
 */

public class CourseAvailabilityListAdapter extends RecyclerView.Adapter<CourseAvailabilityListAdapter.MyViewHolder> {

    private Context mContext;
    private List<TeacherCourse> list;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, price, address, phone;
        public ImageView userImage;
        public AppCompatButton orderButton;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            phone = (TextView) view.findViewById(R.id.phone);
            price = (TextView) view.findViewById(R.id.price);
            address = (TextView) view.findViewById(R.id.address);
            userImage = (ImageView) view.findViewById(R.id.image);
            orderButton = (AppCompatButton) view.findViewById(R.id.order_button);

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

    public CourseAvailabilityListAdapter(Context mContext, List<TeacherCourse> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.course_availability_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        TeacherCourse teacherCourse = list.get(position);
        holder.name.setText(teacherCourse.getUser().getFullName());
        holder.address.setText(teacherCourse.getUser().getShortAddress());
        holder.price.setText(teacherCourse.getFinalCost());
        holder.phone.setText(teacherCourse.getUser().getPhoneNumber());
        Picasso.with(mContext).load(App.URL + "files/users/" + teacherCourse.getUser().getPhoto()).error(R.drawable.user).into(holder.userImage);
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
    public void addAll(List<TeacherCourse> list) {
        list.addAll(list);
        notifyDataSetChanged();
    }

    public void add(TeacherCourse course) {
        list.add(course);
        notifyDataSetChanged();
    }
}
