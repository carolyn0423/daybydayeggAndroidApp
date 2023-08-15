package com.hamels.daybydayegg.MemberCenter.Holder;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.hamels.daybydayegg.R;
import com.hamels.daybydayegg.Repository.Model.Message;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MessageHolder extends RecyclerView.ViewHolder {
    public static final String TAG = MessageHolder.class.getSimpleName();

    public ConstraintLayout layout_self, layout_store;
    public TextView tv_self_message_time, tv_self_message,
            tv_store_message_time, tv_store_message;

    public MessageHolder(@NonNull View itemView) {
        super(itemView);
        layout_self = itemView.findViewById(R.id.layout_self);
        layout_store = itemView.findViewById(R.id.layout_store);
        tv_self_message_time = itemView.findViewById(R.id.tv_self_message_time);
        tv_self_message = itemView.findViewById(R.id.tv_self_message);
        tv_store_message_time = itemView.findViewById(R.id.tv_store_message_time);
        tv_store_message = itemView.findViewById(R.id.tv_store_message);
    }

    public void setMessage(Message message) {

        if(message.is_reply.equals("N")){
            layout_self.setVisibility(View.VISIBLE);
            layout_store.setVisibility(View.GONE);
            tv_self_message.setText(message.content);
            tv_self_message_time.setText(getFormatTime(message.date));
        }else{
            layout_self.setVisibility(View.GONE);
            layout_store.setVisibility(View.VISIBLE);
            tv_store_message.setText(message.content);
            tv_store_message_time.setText(getFormatTime(message.date));
        }
    }

    private String getFormatTime(String itemDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date date = format.parse(itemDate);
            return transFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }
}
