package com.hamels.daybydayegg.MemberCenter.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hamels.daybydayegg.Base.BaseAdapter;
import com.hamels.daybydayegg.MemberCenter.Contract.AdminMessageContract;
import com.hamels.daybydayegg.MemberCenter.Holder.AdminMessageHolder;
import com.hamels.daybydayegg.R;
import com.hamels.daybydayegg.Repository.Model.MessageGroup;

import java.util.ArrayList;
import java.util.List;

public class AdminMessageAdapter extends BaseAdapter<AdminMessageHolder> {
    public static final String TAG = AdminMessageAdapter.class.getSimpleName();
    private List<MessageGroup> messageGroups = new ArrayList<>();
    private AdminMessageContract.Presenter presenter;

    public AdminMessageAdapter(AdminMessageContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public AdminMessageHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_message_group, viewGroup, false);
        return new AdminMessageHolder(view);
    }

//    @Override
//    public void onBindViewHolder(@NonNull AdminMessageHolder mHolder, final int position) {
//        MessageGroup messageGroup = messageGroups.get(position);
//
//        mHolder.setMessage(messageGroup);
//
//        mHolder.mLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                presenter.goMessageList(messageGroups.get(position));
//                //presenter.readMessage(memberMessages.get(position));
//            }
//        });
//    }
    @Override
    public void onBindViewHolder(@NonNull AdminMessageHolder mHolder, int position) {
        MessageGroup messageGroup = messageGroups.get(position);
        mHolder.setMessage(messageGroup);

        mHolder.mLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = mHolder.getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION) {
                    presenter.goMessageList(messageGroups.get(pos));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return messageGroups.size();
    }

    public void setMessagesList(List<MessageGroup> Messages) {
        this.messageGroups = Messages;

        notifyDataSetChanged();
    }
}
