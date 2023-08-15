package com.hamels.daybydayegg.DrawLots.Adapter;

import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hamels.daybydayegg.Base.BaseAdapter;
import com.hamels.daybydayegg.DrawLots.Contract.DrawLotsContract;
import com.hamels.daybydayegg.DrawLots.View.DrawLotsHolder;
import com.hamels.daybydayegg.R;
import com.hamels.daybydayegg.Repository.Model.DrawLots;

import java.util.ArrayList;
import java.util.List;

public class DrawLotsListAdapter extends BaseAdapter<DrawLotsHolder> implements View.OnClickListener{
    public static final String TAG = DrawLotsListAdapter.class.getSimpleName();
    private DrawLotsContract.Presenter presenter;
    private DrawLotsHolder drawLotsHolder;

    private List<DrawLots> drawlotslist = new ArrayList<>();

    public DrawLotsListAdapter(DrawLotsContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public DrawLotsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View viewDrawLots = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_drawlots, viewGroup, false);
        return new DrawLotsHolder(viewDrawLots);
    }

    @Override
    public void onBindViewHolder(@NonNull DrawLotsHolder drawLotsHolder, final int position) {
        drawLotsHolder.setLot(drawlotslist.get(position));

        drawLotsHolder.layout_lot.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return drawlotslist.size();
    }


    public void setDrawlotslist(List<DrawLots> drawlotslist) {
        this.drawlotslist = drawlotslist;
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.layout_lot){
            presenter.getLotDetailByID(Integer.parseInt(v.getTag(v.getId()).toString()));
        }
    }
}