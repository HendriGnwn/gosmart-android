package com.atc.gosmartlesmagistra.adapter;

import android.content.Context;
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
import com.atc.gosmartlesmagistra.model.Order;
import com.atc.gosmartlesmagistra.model.PrivateModel;

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

    public PrivateOrderHistoryExpandableListAdapter(Context context, List<PrivateModel> listDataHeader,
                                                    HashMap<String, List<String>> listChildData) {
        this.context = context;
        this.list = listDataHeader;
        this._listDataChild = listChildData;
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
            convertView = infalInflater.inflate(R.layout.order_history_detail, null);
        }

        LinearLayout linearScheduleView = (LinearLayout) convertView.findViewById(R.id.on_at);
        TextView status = (TextView) convertView.findViewById(R.id.status);
        Integer count = 1;
        linearScheduleView.removeAllViews();
        for (String onAt : order.getPrivateDetails().get(0).getOnDetails()) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(dpToPx(4),dpToPx(4), 0, 0);
            TextView textView = new TextView(context);
            textView.setLayoutParams(layoutParams);
            String choose = onAt;
            Date date = null;
            try {
                date = new SimpleDateFormat("yyyy-MM-dd H:m:s", new Locale("id", "ID")).parse(choose);
                SimpleDateFormat formatted = new SimpleDateFormat("EEEE, dd MMM yyyy H:00", new Locale("id", "ID"));
                choose = formatted.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            textView.setText(context.getString(R.string.section) + " " + count++ + ": " + choose);
            linearScheduleView.addView(textView);
        }

        status.setText("Status: " + order.getStatus());

////        TextView description = (TextView) convertView.findViewById(R.id.description);
////        TextView readMore = (TextView) convertView.findViewById(R.id.readmore);
////        ImageView image = (ImageView) convertView.findViewById(R.id.image);
////
////        description.setText(message.getShortDescription());
////        Picasso.with(context).load(App.URL + "files/messages/" + message.getImage()).error(R.drawable.image_315x315).into(image);
//
//        convertView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, MessageDetailActivity.class);
//                intent.putExtra("message", message);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(intent);
//            }
//        });

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
            view = infalInflater.inflate(R.layout.activity_private_order, null);
        }

        TextView name = (TextView) view.findViewById(R.id.name);
        TextView date = (TextView) view.findViewById(R.id.date);
        ImageView messageImage = (ImageView) view.findViewById(R.id.order_image);

        name.setText(order.getCode());
        date.setText(order.getCreatedAt());

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
