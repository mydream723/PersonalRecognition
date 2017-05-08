package com.mydream.project.personalrecognition.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class HistoryItemAdapter extends RecyclerView.Adapter<HistoryItemAdapter.MyViewHolder> {
    private Context mContext;
    private List<HistroyInfo> historyList;

    public HistoryItemAdapter(Context context, List<HistroyInfo> historyList) {
        mContext = context;
        this.historyList = historyList;
    }

    @Override
    public HistoryItemAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                mContext).inflate(R.layout.item_history_detail, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(HistoryItemAdapter.MyViewHolder holder, int position) {
        int headerHolder = historyList.get(position).getPersonalInfo().getGender() == 0 ? R.drawable.ic_header_male : R.drawable.ic_header_female;
        Picasso.with(mContext).load(new File(historyList.get(position).getPersonalInfo().getHeader())).placeholder(R.drawable.ic_loading).error(headerHolder).into(holder.headerImageView);
        holder.nameTextView.setText(historyList.get(position).getPersonalInfo().getName());
        holder.cardIdTextView.setText(historyList.get(position).getPersonalInfo().getCardId());
        holder.recordDateTextView.setText(historyList.get(position).getScanDate());
        if(historyList.get(position).getIsMarked() == 0){
            //正常
            holder.flagTextView.setText(R.string.historyItem_flag_normal);
            holder.flagTextView.setTextColor(Color.BLACK);
        }else{
            holder.flagTextView.setText(R.string.historyItem_flag_alert);
            holder.flagTextView.setTextColor(Color.RED);
        }
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView headerImageView;
        private TextView nameTextView, cardIdTextView, recordDateTextView, flagTextView;

        public MyViewHolder(View view) {
            super(view);
            headerImageView = (ImageView) view.findViewById(R.id.iv_historyItem_header);
            nameTextView = (TextView) view.findViewById(R.id.tv_historyItem_name);
            cardIdTextView = (TextView) view.findViewById(R.id.tv_historyItem_cardId);
            recordDateTextView = (TextView) view.findViewById(R.id.tv_historyItem_recordDate);
            flagTextView = (TextView) view.findViewById(R.id.tv_historyItem_recognitionFlag);
        }
    }

}
