package com.example.humencheckworkattendance.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.humencheckworkattendance.R;
import com.example.humencheckworkattendance.bean.PlayCardTodayBean;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by administration on 2017/9/14.
 */

public class PlayCardTodayAdapter extends BaseAdapter{
    Context context;
    ArrayList<PlayCardTodayBean.MothListBean> dataList = new ArrayList<PlayCardTodayBean.MothListBean>();
    public PlayCardTodayAdapter(Context Context, ArrayList<PlayCardTodayBean.MothListBean> dataList) {
        this.context = Context;
        this.dataList = dataList;
    }
    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.today_play_card_small, null);
            viewHolder.todayPlayCardSmallName = (TextView) convertView.findViewById(R.id.today_Play_Card_Small_Name);
            viewHolder.todayPlayCardSmallMomning = (TextView) convertView.findViewById(R.id.today_Play_Card_Small_Momning);
            viewHolder.todayPlayCardSmallAfternoon = (TextView) convertView.findViewById(R.id.today_Play_Card_Small_Afternoon);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.todayPlayCardSmallName.setText(dataList.get(position).getStreamName());
        if(dataList.get(position).getAttendanceMorning()==1){
            viewHolder.todayPlayCardSmallMomning.setText("已打卡");
            viewHolder.todayPlayCardSmallMomning.setTextColor(Color.rgb(33,166,93));
        }else if(dataList.get(position).getAttendanceMorning()==2){
            viewHolder.todayPlayCardSmallMomning.setText("未打卡");
            viewHolder.todayPlayCardSmallMomning.setTextColor(Color.rgb(229,69,82));
        }
        if(dataList.get(position).getAttendanceAfternoon()==1){
            viewHolder.todayPlayCardSmallAfternoon.setText("已打卡");
            viewHolder.todayPlayCardSmallAfternoon.setTextColor(Color.rgb(33,166,93));
        }else if(dataList.get(position).getAttendanceMorning()==2){
            viewHolder.todayPlayCardSmallAfternoon.setText("未打卡");
            viewHolder.todayPlayCardSmallAfternoon.setTextColor(Color.rgb(229,69,82));
        }
        return convertView;
    }

    public class ViewHolder {
        @BindView(R.id.today_Play_Card_Small_Name)
        TextView todayPlayCardSmallName;
        @BindView(R.id.today_Play_Card_Small_Momning)
        TextView todayPlayCardSmallMomning;
        @BindView(R.id.today_Play_Card_Small_Afternoon)
        TextView todayPlayCardSmallAfternoon;
    }
}
