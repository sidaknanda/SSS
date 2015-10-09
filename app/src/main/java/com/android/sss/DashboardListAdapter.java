package com.android.sss;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DashboardListAdapter extends BaseExpandableListAdapter {

    private List<String> list;
    private HashMap<String, List<String>> map;
    private Context context;
    private int[] icons = {R.drawable.students, R.drawable.contactschool, R.drawable.changepassword, R.drawable.logout, R.drawable.reportbug};

    public DashboardListAdapter(Context context, List<String> list, HashMap<String, List<String>> map) {
        this.context = context;
        this.list = list;
        this.map = map;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return map.get(list.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        String temp = (String) getChild(groupPosition, childPosition);
        LayoutInflater inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflator.inflate(R.layout.e_list_dashboard_child, null);

        TextView child = (TextView) convertView.findViewById(R.id.textViewDashboardChild);
        child.setText(temp);
        ImageView childIcon = (ImageView) convertView.findViewById(R.id.imageViewChildIcon);
        childIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.student));
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return map.get(list.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return (String) list.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return list.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String temp = (String) getGroup(groupPosition);
        LayoutInflater inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflator.inflate(R.layout.e_list_dashboard_parent, null);

        TextView parent_tv = (TextView) convertView.findViewById(R.id.textViewDashboardParent);
        parent_tv.setText(temp);
        ImageView parentIcon = (ImageView) convertView.findViewById(R.id.imageViewParentIcon);
        parentIcon.setImageDrawable(ContextCompat.getDrawable(context, icons[groupPosition]));
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}
