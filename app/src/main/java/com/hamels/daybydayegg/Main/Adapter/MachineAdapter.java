package com.hamels.daybydayegg.Main.Adapter;

import android.Manifest;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.hamels.daybydayegg.Base.BaseAdapter;
import com.hamels.daybydayegg.Main.Contract.MachineContract;
import com.hamels.daybydayegg.Main.Holder.MachineHolder;
import com.hamels.daybydayegg.R;
import com.hamels.daybydayegg.Repository.Model.Machine;

import java.util.ArrayList;
import java.util.List;

public class MachineAdapter extends BaseAdapter<MachineHolder> {
    public static final String TAG = MachineAdapter.class.getSimpleName();
    private MachineContract.View view;
    private MachineContract.Presenter presenter;
    private List<Machine> machines;

    public MachineAdapter(MachineContract.View view, MachineContract.Presenter presenter) {
        this.view = view;
        this.presenter = presenter;
        machines = new ArrayList<>();
    }

    @NonNull
    @Override
    public MachineHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_machine_list, viewGroup, false);
        return new MachineHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MachineHolder machineHolder, final int position) {
        machineHolder.setStore(machines.get(position));

        if(presenter.getUserLogin()) {
            if (machines.get(position).geOftenID().equals("")) {
                machineHolder.tv_favorite.setImageResource(R.drawable.favoritesgr);
            } else {
                machineHolder.tv_favorite.setImageResource(R.drawable.favorites_1);
            }

            machineHolder.tv_favorite.setVisibility(View.VISIBLE);
        }else{
            machineHolder.tv_favorite.setVisibility(View.GONE);
        }

        machineHolder.tv_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uid = "";
                String machine_id = "";

                if (machines.get(position).geOftenID().equals("")) {
                    uid = machines.get(position).geOftenID();
                    machine_id = machines.get(position).getMachineID();
                }

                presenter.setStoreOften(machine_id, uid);
            }
        });

        machineHolder.tv_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.intentToGoogleMap(machines.get(position).getAddress());
            }
        });
    }

    @Override
    public int getItemCount() {
        return machines.size();
    }

    public void setData(List<Machine> machines) {
        this.machines = machines;
        notifyDataSetChanged();
    }
}
