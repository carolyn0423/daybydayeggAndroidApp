package com.hamels.daybydayegg.MemberCenter.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.hamels.daybydayegg.Repository.Model.Address;
import com.hamels.daybydayegg.Repository.Model.OftenMobile;

import java.util.List;

public class AddressAdapter extends ArrayAdapter<Address> {
    private List<Address> itemList;
    private Context context;

    public AddressAdapter(Context context, int resource, List<Address> itemList) {
        super(context, resource, itemList);
        this.itemList = itemList;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        TextView textView = (TextView) super.getView(position, convertView, parent);
        textView.setText(itemList.get(position).toData());
        return textView;
    }

    @Override
    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
        TextView textView = (TextView) super.getDropDownView(position, convertView, parent);
        textView.setText(itemList.get(position).toData());
        return textView;
    }
}