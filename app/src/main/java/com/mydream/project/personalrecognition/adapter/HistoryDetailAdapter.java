package com.mydream.project.personalrecognition.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mydream.project.personalrecognition.R;
import com.mydream.project.personalrecognition.entity.HistroyInfo;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

/**
 * Created by MX on 2017/5/8.
 */

public class HistoryDetailAdapter extends BaseAdapter {
    private Context mContext;
    private List<HistroyInfo> historyList;
    public HistoryDetailAdapter(Context context, List<HistroyInfo> historyList){
        mContext = context;
        this.historyList = historyList;
    }

    @Override
    public int getCount() {
        return historyList.size();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public HistroyInfo getItem(int i) {
        return historyList.get(i);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if(view == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.item_history_detail,null);
            holder = new ViewHolder();
            holder.headerImageView = (ImageView)view.findViewById(R.id.iv_historyItem_header);
            holder.nameTextView = (TextView)view.findViewById(R.id.tv_historyItem_name);
            holder.cardIdTextView = (TextView)view.findViewById(R.id.tv_historyItem_cardId);
            holder.recordDateTextView = (TextView)view.findViewById(R.id.tv_historyItem_recordDate);
            holder.flagTextView = (TextView)view.findViewById(R.id.tv_historyItem_recognitionFlag);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        int headerHolder = getItem(i).getPersonalInfo().getGender() == 0 ? R.drawable.ic_header_male : R.drawable.ic_header_female;
        Picasso.with(mContext).load(new File(getItem(i).getPersonalInfo().getHeader())).placeholder(R.drawable.ic_loading).error(headerHolder).into(holder.headerImageView);
        holder.nameTextView.setText(getItem(i).getPersonalInfo().getName());
        holder.cardIdTextView.setText(getItem(i).getPersonalInfo().getCardId());
        holder.recordDateTextView.setText(getItem(i).getScanDate());
        if(getItem(i).getIsMarked() == 0){
            //正常
            holder.flagTextView.setText(R.string.historyItem_flag_normal);
            holder.flagTextView.setTextColor(Color.BLACK);
        }else{
            holder.flagTextView.setText(R.string.historyItem_flag_alert);
            holder.flagTextView.setTextColor(Color.RED);
        }

        return view;
    }

    class ViewHolder{
        ImageView headerImageView;
        TextView nameTextView, cardIdTextView, recordDateTextView, flagTextView;
    }
}
