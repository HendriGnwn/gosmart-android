package com.atc.gosmartlesmagistra.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.atc.gosmartlesmagistra.R;
import com.atc.gosmartlesmagistra.activity.PrivateDetailActivity;
import com.atc.gosmartlesmagistra.model.Order;
import com.atc.gosmartlesmagistra.model.PrivateModel;
import com.atc.gosmartlesmagistra.model.StudentOnDetail;
import com.atc.gosmartlesmagistra.model.TeacherOnDetail;
import com.atc.gosmartlesmagistra.model.User;
import com.atc.gosmartlesmagistra.util.DatabaseHelper;
import com.atc.gosmartlesmagistra.util.SessionManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * Created by hendrigunawan on 06/16/17.
 */

public class PrivateOrderHistoryExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<PrivateModel> list; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<String>> _listDataChild;

    private User user;

    public PrivateOrderHistoryExpandableListAdapter(Context context, List<PrivateModel> listDataHeader,
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
        return this._listDataChild.get(String.valueOf(this.list.get(groupPosition).getId())).get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);
        final PrivateModel order = (PrivateModel) list.get(groupPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.private_order_history_detail, null);
        }

        LinearLayout linearScheduleView = (LinearLayout) convertView.findViewById(R.id.on_at);
        TextView teacher = (TextView) convertView.findViewById(R.id.teacher);
        TextView course = (TextView) convertView.findViewById(R.id.course);
        TextView status = (TextView) convertView.findViewById(R.id.status);
        TextView startDate = (TextView) convertView.findViewById(R.id.start_date);
        TextView endDate = (TextView) convertView.findViewById(R.id.end_date);
        TextView finish = (TextView) convertView.findViewById(R.id.finish);
        TextView finishAt = (TextView) convertView.findViewById(R.id.finish_at);
        Integer count = 1;
        linearScheduleView.removeAllViews();
        if (user.getRole() == User.roleStudent) {
            for (StudentOnDetail studentOnDetail : order.getPrivateDetails().get(0).getStudentOnDetails()) {
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(dpToPx(4),dpToPx(4), 0, 0);
                TextView textView = new TextView(context);
                textView.setLayoutParams(layoutParams);
                textView.setTextColor(context.getResources().getColor(R.color.colorBlack));
                textView.setAllCaps(true);
                textView.setText(context.getString(R.string.section) + " " + count++ + ": ");
                linearScheduleView.addView(textView);

                TextView onAt = new TextView(context);
                onAt.setLayoutParams(layoutParams);
                onAt.setTextColor(context.getResources().getColor(R.color.colorBlackSecondary));
                onAt.setText(context.getString(R.string.on_at) + " " + studentOnDetail.getFormattedOnAt());
                linearScheduleView.addView(onAt);

                TextView checkView = new TextView(context);
                checkView.setLayoutParams(layoutParams);
                checkView.setTextColor(context.getResources().getColor(R.color.colorBlackSecondary));
                checkView.setText(context.getString(R.string.check) + " " + studentOnDetail.getCheckText());
                linearScheduleView.addView(checkView);

                TextView checkAt = new TextView(context);
                checkAt.setLayoutParams(layoutParams);
                checkAt.setTextColor(context.getResources().getColor(R.color.colorBlackSecondary));
                checkAt.setText(context.getString(R.string.check) + " " + studentOnDetail.getFormattedCheckAt());
                linearScheduleView.addView(checkAt);
            }
            teacher.setText(context.getResources().getString(R.string.teacher) + ": " + order.getTeacher().getFullName());
        } else if (user.getRole() == User.roleTeacher) {
            for (TeacherOnDetail teacherOnDetail : order.getPrivateDetails().get(0).getTeacherOnDetails()) {
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(dpToPx(4),dpToPx(4), 0, 0);
                TextView textView = new TextView(context);
                textView.setLayoutParams(layoutParams);
                textView.setTextColor(context.getResources().getColor(R.color.colorBlack));
                textView.setAllCaps(true);
                textView.setText(context.getString(R.string.section) + " " + count++ + ": ");
                linearScheduleView.addView(textView);

                TextView onAt = new TextView(context);
                onAt.setLayoutParams(layoutParams);
                onAt.setTextColor(context.getResources().getColor(R.color.colorBlackSecondary));
                onAt.setText(context.getString(R.string.on_at) + " " + teacherOnDetail.getFormattedOnAt());
                linearScheduleView.addView(onAt);

                TextView checkView = new TextView(context);
                checkView.setLayoutParams(layoutParams);
                checkView.setTextColor(context.getResources().getColor(R.color.colorBlackSecondary));
                checkView.setText(context.getString(R.string.check) + " " + teacherOnDetail.getCheckText());
                linearScheduleView.addView(checkView);

                TextView checkAt = new TextView(context);
                checkAt.setLayoutParams(layoutParams);
                checkAt.setTextColor(context.getResources().getColor(R.color.colorBlackSecondary));
                checkAt.setText(context.getString(R.string.check) + " " + teacherOnDetail.getFormattedCheckAt());
                linearScheduleView.addView(checkAt);
            }
            teacher.setText(context.getResources().getString(R.string.student) + ": " + order.getStudent().getFullName());
        }

        status.setText("Status: " + order.getStatusText());
        startDate.setText(context.getResources().getString(R.string.start_date) + ": " + order.getFormattedStartDate());
        endDate.setText(context.getResources().getString(R.string.end_date) + ": " + order.getFormattedEndDate());
        finish.setText(context.getResources().getString(R.string.finish) + ": " + order.getPrivateDetails().get(0).getChecklistText());
        finishAt.setText(context.getResources().getString(R.string.finished_at) + ": " + order.getPrivateDetails().get(0).getFormattedChecklistAt());

        course.setText(context.getResources().getString(R.string.course) + ": " + order.getPrivateDetails().get(0).getTeacherCourse().getCourse().getName());
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(String.valueOf(this.list.get(groupPosition).getId()))
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
        final PrivateModel order = (PrivateModel) list.get(groupPosition);
        if (view == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = infalInflater.inflate(R.layout.private_order_history_list, null);
        }

        TextView name = (TextView) view.findViewById(R.id.name);
        TextView date = (TextView) view.findViewById(R.id.date);
        ImageView messageImage = (ImageView) view.findViewById(R.id.order_image);

        name.setText(order.getCode());
        date.setText(order.getCreatedAt());

        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PrivateDetailActivity.class);
                intent.putExtra("privateModel", order);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

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
