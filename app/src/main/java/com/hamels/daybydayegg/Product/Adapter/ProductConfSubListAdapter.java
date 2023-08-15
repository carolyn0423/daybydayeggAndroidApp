package com.hamels.daybydayegg.Product.Adapter;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hamels.daybydayegg.R;
import com.hamels.daybydayegg.Repository.Model.ProductConf;

import java.util.List;

/**
 * GridView adapter
 */
public class ProductConfSubListAdapter extends BaseAdapter {

    private Context mContext;

    /**
     * GridView data collection of each subitem under each group
     */
    private List<ProductConf> vConfNameList;

    public ProductConfSubListAdapter(Context mContext, List<ProductConf> vConfNameList) {
        this.mContext = mContext;
        this.vConfNameList = vConfNameList;
    }

    @Override
    public int getCount() {
        return vConfNameList.size();
    }

    @Override
    public Object getItem(int position) {
        return vConfNameList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null == convertView) {
            convertView = View.inflate(mContext, R.layout.item_product_subconf, null);
        }
        TextView tvConfContent = convertView.findViewById(R.id.conf_content);
        String conf_content = vConfNameList.get(position).getConf_name();
        String conf_price = Integer.toString(vConfNameList.get(position).getprice());
        String conf_foldout = vConfNameList.get(position).getSoldOutQty();

        if (0 < vConfNameList.get(position).getprice()) {
            conf_content += "+$" + conf_price;
        }

        if(conf_foldout != null && conf_foldout.equals("Y")){
            conf_content += " <br /> <font color='#FF3333'>(今日完售)</font>";
        }

        tvConfContent.setText(Html.fromHtml(conf_content));

        return convertView;
    }
}