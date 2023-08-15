package com.hamels.daybydayegg.Donate.Adapter;

import androidx.annotation.NonNull;
import android.view.ViewGroup;

import com.hamels.daybydayegg.Base.BaseAdapter;
import com.hamels.daybydayegg.Donate.Contract.DonateDetailContract;
import com.hamels.daybydayegg.Donate.Holder.DonateHolder;
import com.hamels.daybydayegg.Repository.Model.Donate;

import java.util.ArrayList;
import java.util.List;

public class DonateHistoryDetailAdapter extends BaseAdapter<DonateHolder> {
    public static final String TAG = DonateHistoryDetailAdapter.class.getSimpleName();
    private DonateDetailContract.Presenter presenter;

    private List<Donate> history = new ArrayList<>();

    public DonateHistoryDetailAdapter(DonateDetailContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public DonateHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull DonateHolder donateHolder, int i) {
        donateHolder.sethistorydetail(history.get(i));
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public void setData() {
        notifyDataSetChanged();
    }

//    @NonNull
//    @Override
//    public DonateHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//        View viewDonate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_donatehistory, viewGroup, false);
//        return new DonateHolder(viewDonate);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull DonateHolder productHolder, @SuppressLint("RecyclerView") final int position) {
////        Log.e(TAG, "onBindViewHolder");
//
//        productHolder.setImg_product_history(history.get(position));
//
//        productHolder.donatehistory_constraintLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                presenter.goPageHistoryDetail(history.get(position).getmeal_no(), history.get(position).getEticketDueDate());
//            }
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return history.size();
//    }
//
//    public void setDonate(List<Donate> history) {
//        this.history = history;
//        notifyDataSetChanged();
//    }
}