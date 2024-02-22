package com.hamels.daybydayegg.MemberCenter.Holder;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.hamels.daybydayegg.R;
import com.hamels.daybydayegg.Repository.Model.MessageGroup;

public class AdminMessageHolder extends RecyclerView.ViewHolder {
    public static final String TAG = AdminMessageHolder.class.getSimpleName();
    public ConstraintLayout mLayout;
    public TextView tvTitle, tvContent, tvDate, tvStatus;

    public AdminMessageHolder(@NonNull View itemView) {
        super(itemView);

        mLayout = itemView.findViewById(R.id.layout_lot);
        tvTitle = itemView.findViewById(R.id.tv_title);
        tvContent = itemView.findViewById(R.id.tv_content);
        tvDate = itemView.findViewById(R.id.tv_date);
        tvStatus = itemView.findViewById(R.id.tv_status);
    }

    public void setMessage(MessageGroup messageGroup) {
        tvTitle.setText(messageGroup.getName());
        tvContent.setText(messageGroup.getMessageContent());
        tvDate.setText(messageGroup.getMessageDate());
        tvStatus.setText(messageGroup.getReplyStatus());
        if(messageGroup.getReplyStatus().equals("已回覆")){
            tvStatus.setTextColor(Color.parseColor("#FF0000"));
        }else{
            tvStatus.setTextColor(Color.parseColor("#008800"));
        }
    }
}
