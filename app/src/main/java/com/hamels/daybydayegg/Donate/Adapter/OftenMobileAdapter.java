package com.hamels.daybydayegg.Donate.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.hamels.daybydayegg.Repository.Model.OftenMobile;
import java.util.List;

public class OftenMobileAdapter extends ArrayAdapter<OftenMobile> {
    private List<OftenMobile> itemList;
    private Context context;

    public OftenMobileAdapter(Context context, int resource, List<OftenMobile> itemList) {
        super(context, resource, itemList);
        this.itemList = itemList;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        TextView textView = (TextView) super.getView(position, convertView, parent);
        textView.setText(itemList.get(position).toString());
        return textView;
    }

    @Override
    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
        TextView textView = (TextView) super.getDropDownView(position, convertView, parent);
        textView.setText(itemList.get(position).toString());
        return textView;
    }
}