package com.hamels.daybydayegg.Base;

import androidx.recyclerview.widget.RecyclerView;

public abstract class BaseAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
