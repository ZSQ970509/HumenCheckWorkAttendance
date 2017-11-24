package com.example.humencheckworkattendance.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.humencheckworkattendance.R;
import com.example.humencheckworkattendance.bean.SubmitHumenBean;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by administration on 2017/9/14.
 */

public class SubmitHumenMessageAdapter extends BaseAdapter{
    Context context;
    ArrayList<SubmitHumenBean.MothListBean> dataList = new ArrayList<SubmitHumenBean.MothListBean>();
    public SubmitHumenMessageAdapter(Context Context, ArrayList<SubmitHumenBean.MothListBean> dataList) {
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
            convertView = inflater.inflate(R.layout.submit_humen_small, null);
            viewHolder.submitHumenSamllName = (TextView) convertView.findViewById(R.id.submit_humen_samll_name);
            viewHolder.submitHumenSamllStatus = (TextView) convertView.findViewById(R.id.submit_humen_samll_status);
            viewHolder.submitHumenUpdate = (ImageView) convertView.findViewById(R.id.submit_humen_update);
            viewHolder.submitHumenDelete = (ImageView) convertView.findViewById(R.id.submit_humen_delete);
            viewHolder.submitHumenSubmit = (ImageView) convertView.findViewById(R.id.submit_humen_submit);
            viewHolder.submitHumenRollback = (ImageView) convertView.findViewById(R.id.submit_humen_rollback);
            viewHolder.submitHumenError  = (ImageView) convertView.findViewById(R.id.submit_humen_error);
            viewHolder.relativelayoutNotAudited = (RelativeLayout) convertView.findViewById(R.id.relativelayout_not_audited);
            viewHolder.relativelayoutAudited = (RelativeLayout) convertView.findViewById(R.id.relativelayout_audited);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.submitHumenSamllName.setText(dataList.get(position).getStreamName());
        if(dataList.get(position).getStatus()==0){
            viewHolder.relativelayoutAudited.setVisibility(View.GONE);
            viewHolder.relativelayoutNotAudited.setVisibility(View.VISIBLE);
            viewHolder.submitHumenSubmit.setVisibility(View.VISIBLE);
            viewHolder.submitHumenError.setVisibility(View.GONE);
            viewHolder.submitHumenSamllStatus.setText("未审核");
            viewHolder.submitHumenSamllStatus.setTextColor(Color.rgb(191,189,189));
        }else if(dataList.get(position).getStatus()==1){
            viewHolder.relativelayoutNotAudited.setVisibility(View.GONE);
            viewHolder.relativelayoutAudited.setVisibility(View.VISIBLE);
            viewHolder.submitHumenSubmit.setVisibility(View.GONE);
            viewHolder.submitHumenError.setVisibility(View.GONE);
            viewHolder.submitHumenSamllStatus.setText("审核中");
            viewHolder.submitHumenSamllStatus.setTextColor(Color.rgb(235,191,59));
        }else if(dataList.get(position).getStatus()==3){
            viewHolder.relativelayoutAudited.setVisibility(View.GONE);
            viewHolder.relativelayoutNotAudited.setVisibility(View.GONE);
            viewHolder.submitHumenSubmit.setVisibility(View.GONE);
            viewHolder.submitHumenError.setVisibility(View.GONE);
            viewHolder.submitHumenSamllStatus.setText("审核通过");
            viewHolder.submitHumenSamllStatus.setTextColor(Color.rgb(33,161,93));
        }else if(dataList.get(position).getStatus()==2){
            viewHolder.relativelayoutAudited.setVisibility(View.GONE);
            viewHolder.relativelayoutNotAudited.setVisibility(View.VISIBLE);
            viewHolder.submitHumenSamllStatus.setText("审核失败");
            viewHolder.submitHumenSubmit.setVisibility(View.GONE);
            viewHolder.submitHumenError.setVisibility(View.VISIBLE);
            viewHolder.submitHumenSamllStatus.setTextColor(Color.rgb(229,69,82));
        }
        viewHolder.submitHumenUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemChildListener.onUpdateClick(position);
            }
        });
        viewHolder.submitHumenDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemChildListener.onDeleteClick(position);
            }
        });
        viewHolder.submitHumenSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemChildListener.onSubmitClick(position);
            }
        });
        viewHolder.submitHumenRollback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemChildListener.onRollbackClick(position);
            }
        });
        viewHolder.submitHumenError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemChildListener.onErrorClick(position);
            }
        });
        return convertView;
    }
    public interface onItemChildListener {
        void onDeleteClick(int i);
        void onSubmitClick(int i);
        void onUpdateClick(int i);
        void onRollbackClick(int i);
        void onErrorClick(int i);
    }

    private onItemChildListener onItemChildListener;

    public void setOnItemDeleteClickListener(onItemChildListener onItemChildListener) {
        this.onItemChildListener = onItemChildListener;
    }
    public class ViewHolder {
        @BindView(R.id.submit_humen_samll_name)
        TextView submitHumenSamllName;
        @BindView(R.id.submit_humen_samll_status)
        TextView submitHumenSamllStatus;
        @BindView(R.id.submit_humen_update)
        ImageView submitHumenUpdate;
        @BindView(R.id.submit_humen_delete )
        ImageView submitHumenDelete;
        @BindView(R.id.submit_humen_submit )
        ImageView submitHumenSubmit;
        @BindView(R.id.submit_humen_rollback )
        ImageView submitHumenRollback;
        @BindView(R.id.submit_humen_error)
        ImageView submitHumenError;
        @BindView(R.id.relativelayout_not_audited)
        RelativeLayout relativelayoutNotAudited;
        @BindView(R.id.relativelayout_audited)
        RelativeLayout relativelayoutAudited;
    }
}
