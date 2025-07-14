package com.hamels.daybydayegg.MemberCenter.Adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hamels.daybydayegg.Base.BaseAdapter;
import com.hamels.daybydayegg.Base.BaseContract;
import com.hamels.daybydayegg.EOrderApplication;
import com.hamels.daybydayegg.MemberCenter.Contract.MailFileContract;
import com.hamels.daybydayegg.MemberCenter.Holder.MailFileHolder;
import com.hamels.daybydayegg.R;
import com.hamels.daybydayegg.Repository.Model.MemberMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MailFileAdapter extends BaseAdapter<MailFileHolder> {
    public static final String TAG = MailFileAdapter.class.getSimpleName();
    private MailFileContract.Presenter presenter;
    private List<MemberMessage> memberMessages = new ArrayList<>();
    private Map<Integer, Boolean> isReadMap = new HashMap<>();

    public MailFileAdapter(MailFileContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public MailFileHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_mail_file, viewGroup, false);
        return new MailFileHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MailFileHolder mailFileHolder, int position) {
        final MemberMessage message = memberMessages.get(position);

        mailFileHolder.setMessage(message, isReadMap.containsKey(message.getId()) ? isReadMap.get(message.getId()) : false);

        mailFileHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = mailFileHolder.getAdapterPosition();
                if (pos == RecyclerView.NO_POSITION) return;

                MemberMessage msgToDelete = memberMessages.get(pos);
                presenter.deleteMessage(msgToDelete, new BaseContract.ValueCallback<String>() {
                    @Override
                    public void onValueCallback(int task, String type) {
                        memberMessages.remove(pos);
                        notifyItemRemoved(pos);
                        notifyItemRangeChanged(pos, getItemCount());
                    }
                });
            }
        });

        mailFileHolder.mailLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = mailFileHolder.getAdapterPosition();
                if (pos == RecyclerView.NO_POSITION) return;

                MemberMessage msg = memberMessages.get(pos);
                if ("N".equals(msg.getRead_flag())) {
                    EOrderApplication.mailBadgeCount = (Integer.parseInt(EOrderApplication.mailBadgeCount) - 1) + "";
                }
                presenter.readMessage(msg);
            }
        });
    }


//    @Override
//    public void onBindViewHolder(@NonNull MailFileHolder mailFileHolder, final int position) {
//        final MemberMessage message = memberMessages.get(position);
//
//        mailFileHolder.setMessage(message, isReadMap.containsKey(message.getId()) ? isReadMap.get(message.getId()) : false);
//        mailFileHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                presenter.deleteMessage(message, new BaseContract.ValueCallback<String>() {
//                    @Override
//                    public void onValueCallback(int task, String type) {
//                        memberMessages.remove(position);
//                        notifyItemRemoved(position);
//                        notifyItemRangeChanged(position, getItemCount());
//                    }
//                });
//            }
//        });
//
//        mailFileHolder.mailLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(memberMessages.get(position).getRead_flag().equals("N")) {
//                    EOrderApplication.mailBadgeCount = (Integer.parseInt(EOrderApplication.mailBadgeCount) - 1) + "";
//                }
//                presenter.readMessage(memberMessages.get(position));
//            }
//        });
//    }

    @Override
    public int getItemCount() {
        return memberMessages.size();
    }

    public void setMemberMessages(List<MemberMessage> memberMessages, Map<Integer, Boolean> isReadMap) {
        this.memberMessages = memberMessages;
        this.isReadMap = isReadMap;

        notifyDataSetChanged();
    }
}
