package com.hamels.daybydayegg.Product.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.hamels.daybydayegg.R;
import com.hamels.daybydayegg.Repository.Model.ProductConf;

import java.util.HashMap;
import java.util.List;

public class ProductConfListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> expandableListTitle;
    private HashMap<String, List<ProductConf>> expandableListDetail;
    public List<ProductConf> SelectConf;

    public ProductConfListAdapter(Context context, List<String> expandableListTitle, HashMap<String, List<ProductConf>> expandableListDetail, List<ProductConf> SelectConf) {
        this.context = context;
        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail = expandableListDetail;
        this.SelectConf = SelectConf;
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition)).get(expandedListPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(final int listPosition, final int expandedListPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final Context context = this.context;

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_product_subconf_gridviewitem, null);
        }

        GridView gridView = (GridView) convertView;
        // Create GridView adapter
        ProductConfSubListAdapter gridViewAdapter = new ProductConfSubListAdapter(context, this.expandableListDetail.get(this.expandableListTitle.get(listPosition)));
        gridView.setAdapter(gridViewAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if ("1" == view.findViewById(R.id.btn_conf).getTag(R.id.btn_conf)) {
                    // 已選擇
                    view.findViewById(R.id.btn_conf).setTag(R.id.btn_conf, "0");

                    view.findViewById(R.id.btn_conf).setBackgroundResource(R.drawable.rec_solid_white_tab);
                    ((TextView) view.findViewById(R.id.conf_content)).setTextColor(R.color.greyDark);

                    SelectConf.remove(expandableListDetail.get(expandableListTitle.get(listPosition)).get(position));
                }
                else {
                    // 未選擇
                    view.findViewById(R.id.btn_conf).setTag(R.id.btn_conf, "1");

                    view.findViewById(R.id.btn_conf).setBackgroundResource(R.drawable.rec_solid_orange_tab);
                    ((TextView) view.findViewById(R.id.conf_content)).setTextColor(Color.WHITE);

                    SelectConf.add(expandableListDetail.get(expandableListTitle.get(listPosition)).get(position));
                }

//                Toast.makeText(context, "Clicked the first" + (listPosition + 1) + "Group, Section" +
//                        (position + 1) + "item", Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition) {
//        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition)).size();
        return 1;
    }

    @Override
    public Object getGroup(int listPosition) {
        return this.expandableListTitle.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return this.expandableListTitle.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String listTitle = (String) getGroup(listPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_product_mainconf, null);
        }
        TextView listTitleTextView = (TextView) convertView
                .findViewById(R.id.listTitle);
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(listTitle);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }
}