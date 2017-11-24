package com.example.humencheckworkattendance.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.humencheckworkattendance.R;
import com.example.humencheckworkattendance.bean.EmtpBean;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by administration on 2017/9/14.
 */

public class PopListViewAdapter extends BaseAdapter{
    Context context;
    ArrayList<EmtpBean.ListBean> emtpBeanArrayList;
    public PopListViewAdapter(Context Context, ArrayList<EmtpBean.ListBean> emtpBeanArrayList) {
        this.context = Context;
        this.emtpBeanArrayList = emtpBeanArrayList;
    }
    @Override
    public int getCount() {
        return emtpBeanArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return emtpBeanArrayList.get(position);
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

        viewHolder.textViewPopupWindowListView.setText(emtpBeanArrayList.get(position).getName());

        return convertView;
    }
    public class ViewHolder {
        @BindView(R.id.textView_PopupWindow_ListView)
        TextView textViewPopupWindowListView;

    }
}
