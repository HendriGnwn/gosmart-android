package com.atc.gosmartlesmagistra.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.AppCompatButton;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.atc.gosmartlesmagistra.R;
import com.atc.gosmartlesmagistra.activity.PrivateDetailActivity;
import com.atc.gosmartlesmagistra.activity.SectionCheckActivity;
import com.atc.gosmartlesmagistra.model.DateDetail;
import com.atc.gosmartlesmagistra.model.PrivateDetail;
import com.atc.gosmartlesmagistra.model.PrivateModel;
import com.atc.gosmartlesmagistra.model.Schedule;
import com.atc.gosmartlesmagistra.model.StudentOnDetail;
import com.atc.gosmartlesmagistra.model.TeacherOnDetail;
import com.atc.gosmartlesmagistra.model.User;
import com.atc.gosmartlesmagistra.util.DatabaseHelper;
import com.atc.gosmartlesmagistra.util.SessionManager;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by hendrigunawan on 06/16/17.
 */

public class ScheduleExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<Schedule> list; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<String>> _listDataChild;

    private User user;

    public ScheduleExpandableListAdapter(Context context, List<Schedule> listDataHeader,
                                         HashMap<String, List<String>> listChildData) {
        this.context = context;
        this.list = listDataHeader;
        this._listDataChild = listChildData;
        SessionManager sessionManager = new SessionManager(context);
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        user = databaseHelper.getUser(sessionManager.getUserCode());
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(String.valueOf(this.list.get(groupPosition).getPrivateModel().getId())).get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);
        final Schedule schedule = (Schedule) list.get(groupPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.schedule_detail, null);
        }

        TextView description = (TextView) convertView.findViewById(R.id.description);
        TextView code = (TextView) convertView.findViewById(R.id.code);
        TextView student = (TextView) convertView.findViewById(R.id.student);
        TextView studentPhone = (TextView) convertView.findViewById(R.id.student_phone);
        TextView teacher = (TextView) convertView.findViewById(R.id.teacher);
        TextView teacherPhone = (TextView) convertView.findViewById(R.id.teacher_phone);
        TextView course = (TextView) convertView.findViewById(R.id.course_name);
        TextView jadwal = (TextView) convertView.findViewById(R.id.schedule);
        AppCompatButton doneButton = (AppCompatButton) convertView.findViewById(R.id.done_button);

        description.setText(schedule.getMessage());
        code.setText(context.getString(R.string.code) + ": " + schedule.getPrivateModel().getCode());
        student.setText(context.getString(R.string.student) + ": " + schedule.getPrivateModel().getStudent().getFullName());
        studentPhone.setText(context.getString(R.string.student_phone) + ": " + schedule.getPrivateModel().getStudent().getPhoneNumber());
        teacher.setText(context.getString(R.string.teacher) + ": " + schedule.getPrivateModel().getTeacher().getFullName());
        teacherPhone.setText(context.getString(R.string.teacher_phone) + ": " + schedule.getPrivateModel().getTeacher().getPhoneNumber());
        course.setText(context.getString(R.string.course) + ": " + schedule.getPrivateModel().getPrivateDetails().get(0).getTeacherCourse().getCourse().getName());
        jadwal.setText(context.getString(R.string.schedule) + ": " + schedule.getFormattedDate());

        if (schedule.getDateDetail().getCheck().equals(DateDetail.checkTrue)) {
            doneButton.setText(context.getString(R.string.done_label));
            doneButton.setBackground(context.getResources().getDrawable(R.drawable.shape_rectanglebutton_small_secondary));
        } else {
            doneButton.setText(context.getString(R.string.action_set_done));
            doneButton.setBackground(context.getResources().getDrawable(R.drawable.shape_rectanglebutton_small));
        }

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SectionCheckActivity.class);
                intent.putExtra("schedule", schedule);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });


        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(String.valueOf(this.list.get(groupPosition).getPrivateModel().getId()))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.list.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.list.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View view, ViewGroup parent) {
        final Schedule schedule = (Schedule) list.get(groupPosition);
        if (view == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = infalInflater.inflate(R.layout.schedule_list, null);
        }

        TextView name = (TextView) view.findViewById(R.id.name);

        name.setText(schedule.getFormattedDate());

        return view;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = context.getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

}
