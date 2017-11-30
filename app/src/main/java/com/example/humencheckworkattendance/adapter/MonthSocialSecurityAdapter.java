package com.example.humencheckworkattendance.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.humencheckworkattendance.R;
import com.example.humencheckworkattendance.bean.MonthSocialSecurityBean;
import com.example.humencheckworkattendance.bean.PlayCardTodayBean;

import java.util.ArrayList;

import butterknife.BindView;
import cn.cloudwalk.libproject.util.LogUtil;

/**
 * Created by administration on 2017/9/14.
 */

public class MonthSocialSecurityAdapter extends BaseAdapter{
    Context context;
    ArrayList<MonthSocialSecurityBean.MothListBean> dataList = new ArrayList<>();
    private MonthSocialSecurityAdapter.onItemChildListener onItemChildListener;

    public MonthSocialSecurityAdapter(Context Context, ArrayList<MonthSocialSecurityBean.MothListBean> dataList) {
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
            convertView = inflater.inflate(R.layout.month_social_security_small, null);
            viewHolder.monthSocialSecurityName = (TextView) convertView.findViewById(R.id.month_social_security_small_name);
            viewHolder.tmonthSocialSecurityStatus = (TextView) convertView.findViewById(R.id.month_social_security_small_status);
            viewHolder.monthSocialSecurityUpload = (ImageView) convertView.findViewById(R.id.month_social_security_small_upload);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.monthSocialSecurityName.setText(dataList.get(position).getStreamName());
        //status  0未上传  1已上传
        if(dataList.get(position).getStatus()==0){
            viewHolder.tmonthSocialSecurityStatus.setText("否");
            viewHolder.tmonthSocialSecurityStatus.setTextColor(Color.rgb(229,69,82));

            viewHolder.monthSocialSecurityUpload.setVisibility(View.VISIBLE);
            viewHolder.monthSocialSecurityUpload.setTag(position);
            viewHolder.monthSocialSecurityUpload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = (int)v.getTag();
                    onItemChildListener.onUpdateClick(position);
                }
            });


        }else if(dataList.get(position).getStatus()==1){
            viewHolder.tmonthSocialSecurityStatus.setText("是");
            viewHolder.tmonthSocialSecurityStatus.setTextColor(Color.rgb(33,166,93));
            viewHolder.monthSocialSecurityUpload.setVisibility(View.INVISIBLE);
        }

        return convertView;
    }
    public interface onItemChildListener {
        void onUpdateClick(int position);
    }


    public void setOnItemDeleteClickListener(MonthSocialSecurityAdapter.onItemChildListener onItemChildListener) {
        this.onItemChildListener = onItemChildListener;
    }
    public class ViewHolder {
        @BindView(R.id.month_social_security_small_name)
        TextView monthSocialSecurityName;
        @BindView(R.id.month_social_security_small_status)
        TextView tmonthSocialSecurityStatus;
        @BindView(R.id.month_social_security_small_upload)
        ImageView monthSocialSecurityUpload;
    }
}
