package com.hamels.daybydayegg.Donate.Adapter;

import android.annotation.SuppressLint;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hamels.daybydayegg.Base.BaseAdapter;
import com.hamels.daybydayegg.Donate.Contract.DonateCartContract;
import com.hamels.daybydayegg.Donate.Holder.DonateCartHolder;
import com.hamels.daybydayegg.R;
import com.hamels.daybydayegg.Repository.Model.DonateCart;

import java.util.ArrayList;
import java.util.List;

public class DonateCartAdapter extends BaseAdapter<DonateCartHolder> {
    public static final String TAG = DonateCartAdapter.class.getSimpleName();
    private DonateCartContract.View view;
    private DonateCartContract.Presenter presenter;

    private List<DonateCart> donatecart = new ArrayList<>();
    private int layouttype;

    public DonateCartAdapter(DonateCartContract.View view, DonateCartContract.Presenter presenter, int layouttype) {
        this.view = view;
        this.presenter = presenter;
        this.layouttype = layouttype;
    }

    @NonNull
    @Override
    public DonateCartHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//        Log.e(TAG, "onCreateViewHolder");
        View viewDonate = LayoutInflater.from(viewGroup.getContext()).inflate(layouttype, viewGroup, false);
        return new DonateCartHolder(viewDonate);
    }

    @Override
    public void onBindViewHolder(@NonNull final DonateCartHolder donatecartHolder, @SuppressLint("RecyclerView") final int position) {
//        Log.e(TAG, "onBindViewHolder");

        if (layouttype == R.layout.item_donatecart1){
            donatecartHolder.setImg_donatecart1(donatecart.get(position));

            donatecartHolder.btn_minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (donatecartHolder.setcart_count(donatecart.get(position), "minus")) {
                        presenter.updateTicket("minus", donatecart.get(position).getproduct_id(), donatecart.get(position).getspec_id(), donatecart.get(position).getgive_date());
                    }
                }
            });
            donatecartHolder.btn_plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (donatecartHolder.setcart_count(donatecart.get(position), "add")) {
                        presenter.updateTicket("add", donatecart.get(position).getproduct_id(), donatecart.get(position).getspec_id(), donatecart.get(position).getgive_date());
                    }
                }
            });
        }else if (layouttype == R.layout.item_donatecart2){
            donatecartHolder.setImg_donatecart2(donatecart.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return donatecart.size();
    }

    public void setDonate(List<DonateCart> donatecart) {
        this.donatecart = donatecart;
        notifyDataSetChanged();
    }
}