package com.example.humencheckworkattendance.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.humencheckworkattendance.R;
import com.example.humencheckworkattendance.bean.ProjectBean;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by administration on 2017/9/14.
 */

public class SelectProjectAdapter extends BaseAdapter{
    Context context;
    ArrayList<ProjectBean.ListBean> projectBeanArrayList;
    public SelectProjectAdapter(Context Context, ArrayList<ProjectBean.ListBean> projectBeanArrayList) {
        this.context = Context;
        this.projectBeanArrayList = projectBeanArrayList;
    }
    @Override
    public int getCount() {
        return projectBeanArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return projectBeanArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.select_project_small, null);
            viewHolder.selectProjectSamllTextView = (TextView) convertView.findViewById(R.id.select_project_samll_TextView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.selectProjectSamllTextView.setText(projectBeanArrayList.get(position).getName());
        return convertView;
    }
    public class ViewHolder {
        @BindView(R.id.select_project_samll_TextView)
        TextView selectProjectSamllTextView;
    }
}
