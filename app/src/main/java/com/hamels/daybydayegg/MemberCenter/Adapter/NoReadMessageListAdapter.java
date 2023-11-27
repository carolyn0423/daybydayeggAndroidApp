package com.hamels.daybydayegg.MemberCenter.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.hamels.daybydayegg.Base.BaseAdapter;
import com.hamels.daybydayegg.MemberCenter.Holder.MessageHolder;
import com.hamels.daybydayegg.R;
import com.hamels.daybydayegg.Repository.Model.Message;

import java.util.ArrayList;
import java.util.List;

public class NoReadMessageListAdapter extends BaseAdapter<MessageHolder> {
    public static final String TAG = NoReadMessageListAdapter.class.getSimpleName();

    private List<Message> messages = new ArrayList<>();

    @NonNull
    @Override
    public MessageHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_location, viewGroup, false);
        return new MessageHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageHolder messageHolder, int position) {
        messageHolder.setMessage(messages.get(position));
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
        notifyDataSetChanged();
    }
}
