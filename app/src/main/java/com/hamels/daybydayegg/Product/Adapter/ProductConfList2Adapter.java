package com.hamels.daybydayegg.Product.Adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hamels.daybydayegg.R;
import com.hamels.daybydayegg.Repository.Model.ProductConf;
import com.hamels.daybydayegg.Repository.Model.ProductConfAmout;

import java.util.ArrayList;
import java.util.List;

public class ProductConfList2Adapter extends BaseAdapter {
    public ArrayList<ProductConfAmout> productList;
    public List<ProductConf> SelectConf;

    Activity activity;

    public ProductConfList2Adapter(Activity activity, ArrayList<ProductConfAmout> productList, List<ProductConf> SelectConf) {
        super();
        this.activity = activity;
        this.productList = productList;
        this.SelectConf = SelectConf;
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int position) {
        return productList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {
        TextView btn_del;
        TextView mProduct;
        TextView mCategory;
        TextView mPrice;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        LayoutInflater inflater = activity.getLayoutInflater();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.listview_row, null);
            holder = new ViewHolder();

            holder.btn_del = (TextView) convertView.findViewById(R.id.btn_del);
            holder.btn_del.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    productList.remove(position);
                    ProductConfList2Adapter.this.notifyDataSetChanged();
                }
            });

            holder.mProduct = (TextView) convertView.findViewById(R.id.confname);
            holder.mCategory = (TextView) convertView.findViewById(R.id.qty);
            holder.mPrice = (TextView) convertView.findViewById(R.id.price);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ProductConfAmout item = productList.get(position);
        holder.btn_del.setTag(R.id.btn_del, item.getsIDList());
        holder.mProduct.setText(item.getConfname());
        holder.mCategory.setText(item.getQty());
        holder.mPrice.setText(item.getPrice());

        if (position % 2 == 1) {
            convertView.setBackgroundColor(Color.parseColor("#E4F0E2"));
        } else {
            convertView.setBackgroundColor(Color.WHITE);
        }

        return convertView;
    }
}