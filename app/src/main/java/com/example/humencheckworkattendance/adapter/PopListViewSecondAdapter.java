package com.example.humencheckworkattendance.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.humencheckworkattendance.R;
import com.example.humencheckworkattendance.bean.EmtpRolesBean;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by administration on 2017/9/14.
 */

public class PopListViewSecondAdapter extends BaseAdapter{
    Context context;
    ArrayList<EmtpRolesBean.ListBean> EmtpRolesBeanList;
    public PopListViewSecondAdapter(Context Context, ArrayList<EmtpRolesBean.ListBean> EmtpRolesBeanList) {
        this.context = Context;
        this.EmtpRolesBeanList = EmtpRolesBeanList;
    }
    @Override
    public int getCount() {
        return EmtpRolesBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return EmtpRolesBeanList.get(position);
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
            convertView = inflater.inflate(R.layout.view_popupwindow_listview_samll, null);

            viewHolder.textViewPopupWindowListView = (TextView) convertView.findViewById(R.id.textView_PopupWindow_ListView);


            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.textViewPopupWindowListView.setText(EmtpRolesBeanList.get(position).getName());

        return convertView;
    }
    public class ViewHolder {
        @BindView(R.id.textView_PopupWindow_ListView)
        TextView textViewPopupWindowListView;

    }
}
