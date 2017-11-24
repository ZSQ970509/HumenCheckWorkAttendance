package com.example.humencheckworkattendance.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.humencheckworkattendance.R;
import com.example.humencheckworkattendance.bean.HistroyBean;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by administration on 2017/9/14.
 */

public class HistroyAdapter extends BaseAdapter{
    Context context;
    ArrayList<HistroyBean.MothListBean> histroyBeanArrayList;
    public HistroyAdapter(Context Context, ArrayList<HistroyBean.MothListBean> histroyBeanArrayList) {
        this.context = Context;
        this.histroyBeanArrayList = histroyBeanArrayList;
    }
    @Override
    public int getCount() {
        return histroyBeanArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return histroyBeanArrayList.get(position);
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
            convertView = inflater.inflate(R.layout.histroy_small, null);

            viewHolder.textviewSignStatus = (TextView) convertView.findViewById(R.id.textview_Sign_Status);
            viewHolder.textviewSignTime = (TextView) convertView.findViewById(R.id.textview_Sign_Time);
            viewHolder.textviewSignAddress = (TextView) convertView.findViewById(R.id.textview_Sign_Address);
            viewHolder.textviewSignErr =  (TextView) convertView.findViewById(R.id.textview_Sign_Err);
            viewHolder.textviewSignProjectLocation =  (TextView) convertView.findViewById(R.id.textview_Sign_Project_Location);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.textviewSignErr.setSelected(true);
        viewHolder.textviewSignErr.setHorizontallyScrolling(true);
        viewHolder.textviewSignProjectLocation.setSelected(true);
        viewHolder.textviewSignProjectLocation.setHorizontallyScrolling(true);
        if(histroyBeanArrayList.get(position).getAttendance().equals("报到成功")){
            viewHolder.textviewSignErr.setVisibility(View.GONE);
            viewHolder.textviewSignStatus.setTextColor(Color.rgb(0,153,68));
        }else{
            viewHolder.textviewSignErr.setVisibility(View.VISIBLE);
            viewHolder.textviewSignStatus.setTextColor(Color.rgb(230,0,18));
        }
        viewHolder.textviewSignStatus.setText(histroyBeanArrayList.get(position).getAttendance());

        viewHolder.textviewSignTime.setText(histroyBeanArrayList.get(position).getTime());
        viewHolder.textviewSignAddress.setText(histroyBeanArrayList.get(position).getAddress()+"");
        viewHolder.textviewSignErr.setText("失败原因："+histroyBeanArrayList.get(position).getTypeString());
        viewHolder.textviewSignProjectLocation.setText(histroyBeanArrayList.get(position).getProjName()+"");
        return convertView;
    }
    public class ViewHolder {
        @BindView(R.id.textview_Sign_Status)
        TextView textviewSignStatus;
        @BindView(R.id.textview_Sign_Time)
        TextView textviewSignTime;
        @BindView(R.id.textview_Sign_Address)
        TextView textviewSignAddress;
        @BindView(R.id.textview_Sign_Err)
        TextView textviewSignErr;
        @BindView(R.id.textview_Sign_Project_Location)
        TextView textviewSignProjectLocation;
    }
}
