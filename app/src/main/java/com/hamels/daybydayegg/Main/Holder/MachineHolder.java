package com.hamels.daybydayegg.Main.Holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.hamels.daybydayegg.R;
import com.hamels.daybydayegg.Repository.Model.Machine;
import com.hamels.daybydayegg.Repository.Model.Store;

public class MachineHolder extends RecyclerView.ViewHolder {
    public ConstraintLayout clItemStoreList;
    public TextView tv_title, tv_distance, tv_address;
    public ImageView tv_favorite;

    public MachineHolder(@NonNull View itemView) {
        super(itemView);
        clItemStoreList = itemView.findViewById(R.id.item_machine_list);
        tv_title = itemView.findViewById(R.id.tv_title);
        tv_distance = itemView.findViewById(R.id.tv_distance);
        tv_address = itemView.findViewById(R.id.tv_address);
        tv_favorite = itemView.findViewById(R.id.tv_favorite);
    }

    public void setStore(Machine machine) {
        tv_title.setText(machine.getTitle());
        tv_distance.setText("" + machine.getDistance());
        tv_address.setText(machine.getAddress());

        if(machine.getMachineStatus().equals("N") || machine.getOnline().equals("N")){
            clItemStoreList.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.gray));
            // Disable click and focus
            clItemStoreList.setClickable(false);
            clItemStoreList.setFocusable(false);
        }else{
            clItemStoreList.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.white));
            clItemStoreList.setClickable(true);
            clItemStoreList.setFocusable(true);
        }

//        if(presenter.getUserLogin()) {
//            if (store.getIsOften().equals("1")) {
//                tv_storefavorite.setImageResource(R.drawable.favorites_1);
//            } else {
//                tv_storefavorite.setImageResource(R.drawable.favoritesgr);
//            }
//        }else{
//            tv_storefavorite.setVisibility(View.GONE);
//        }
    }
}
