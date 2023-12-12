package com.hamels.daybydayegg.Main.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.hamels.daybydayegg.Base.BaseAdapter;
import com.hamels.daybydayegg.Main.Contract.MachineContract;
import com.hamels.daybydayegg.Main.Contract.MachineMapContract;
import com.hamels.daybydayegg.Main.Holder.MachineHolder;
import com.hamels.daybydayegg.Main.Holder.MachineMapHolder;
import com.hamels.daybydayegg.R;
import com.hamels.daybydayegg.Repository.Model.Machine;

import java.util.ArrayList;
import java.util.List;

public class MachineMapAdapter extends BaseAdapter<MachineMapHolder> {
    public static final String TAG = MachineMapAdapter.class.getSimpleName();
    private MachineMapContract.View view;
    private MachineMapContract.Presenter presenter;
    private List<Machine.Product> products;

    public MachineMapAdapter(MachineMapContract.View view, MachineMapContract.Presenter presenter) {
        this.view = view;
        this.presenter = presenter;
        products = new ArrayList<>();
    }

    @NonNull
    @Override
    public MachineMapHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_google_map_product, viewGroup, false);
        return new MachineMapHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MachineMapHolder machineMapHolder, int position) {
        machineMapHolder.setProduct(products.get(position));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void setData(List<Machine.Product> products) {
        this.products = products;
        notifyDataSetChanged(); // 通知 Adapter 資料已更新
    }
}
