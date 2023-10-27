package com.hamels.daybydayegg.Donate.Adapter;

import android.annotation.SuppressLint;
import androidx.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hamels.daybydayegg.Base.BaseAdapter;
import com.hamels.daybydayegg.Donate.Contract.DonateContract;
import com.hamels.daybydayegg.Donate.Holder.DonateHolder;
import com.hamels.daybydayegg.R;
import com.hamels.daybydayegg.Repository.Model.Donate;

import java.util.ArrayList;
import java.util.List;

public class DonateListHistoryAdapter extends BaseAdapter<DonateHolder> {
    public static final String TAG = DonateListHistoryAdapter.class.getSimpleName();
    private DonateContract.Presenter presenter;

    private List<Donate> history = new ArrayList<>();
    private String type = "";

    public DonateListHistoryAdapter(DonateContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public DonateHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Log.e(TAG, "onCreateViewHolder");

        View viewDonate;

        if(type.equals("A")) {
            viewDonate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_donatehistory, viewGroup, false);
        }
        else {
            viewDonate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_donatehistorydetail, viewGroup, false);
        }

        return new DonateHolder(viewDonate);
    }

    @Override
    public void onBindViewHolder(@NonNull DonateHolder productHolder, @SuppressLint("RecyclerView") final int position) {
        Log.e(TAG, "onBindViewHolder");

        if(type.equals("A")) {
            productHolder.setImg_product_history(history.get(position));

            productHolder.donatehistory_constraintLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(history.get(position).getWriteoff_order_id().indexOf("|||") == -1) {
                        presenter.goPageHistoryDetail(history.get(position).getWriteoff_order_id(), history.get(position).getEticketDueDate(), history.get(position).getmodified_date(), history.get(position).getmeal_no());
                    }
                }
            });

            productHolder.donatehistory_constraintLayout2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.goPageHistoryDetail(history.get(position).getWriteoff_order_id(), history.get(position).getEticketDueDate(), history.get(position).getmodified_date(), history.get(position).getmeal_no());
                }
            });
        }
        else {
            productHolder.sethistorydetail2(history.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return history.size();
    }

    public void setDonate(List<Donate> history) {
        this.history = history;

        type = "A";

        notifyDataSetChanged();
    }

    public void setDonateHistoryDetail(List<Donate> history) {
        this.history = history;

        type = "B";

        notifyDataSetChanged();
    }
}