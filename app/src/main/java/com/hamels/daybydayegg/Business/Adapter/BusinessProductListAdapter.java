package com.hamels.daybydayegg.Business.Adapter;

import androidx.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hamels.daybydayegg.Base.BaseAdapter;
import com.hamels.daybydayegg.Business.Contract.BusinessProductContract;
import com.hamels.daybydayegg.Business.View.BusinessProductHolder;
import com.hamels.daybydayegg.R;
import com.hamels.daybydayegg.Repository.Model.Product;

import java.util.ArrayList;
import java.util.List;

public class BusinessProductListAdapter extends BaseAdapter<BusinessProductHolder> {
    public static final String TAG = BusinessProductListAdapter.class.getSimpleName();
    private BusinessProductContract.Presenter presenter;

    private List<Product> productleft = new ArrayList<>();
    private List<Product> productright = new ArrayList<>();

    public BusinessProductListAdapter(BusinessProductContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public BusinessProductHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Log.e(TAG,"onCreateViewHolder");
        View viewProduct = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_product, viewGroup, false);
        return new BusinessProductHolder(viewProduct);
    }

    @Override
    public void onBindViewHolder(@NonNull BusinessProductHolder businessProductHolder, final int position) {
        Log.e(TAG,"onBindViewHolder");
        if(productleft.size() == productright.size()){
            businessProductHolder.setImg_product_two(productleft.get(position) , productright.get(position));
        }
        else{
            if(productright.size() == position){
                businessProductHolder.setImg_product_one(productleft.get(position));
            }
            else{
                businessProductHolder.setImg_product_two(productleft.get(position) , productright.get(position));
            }
        }
        businessProductHolder.img_product_left.setOnClickListener(img_OnClick_Evt);
        businessProductHolder.img_product_right.setOnClickListener(img_OnClick_Evt);
        businessProductHolder.layout_left.setOnClickListener(img_OnClick_Evt);
        businessProductHolder.layout_right.setOnClickListener(img_OnClick_Evt);
    }

    @Override
    public int getItemCount() {
        return productleft.size();
    }


    public void setProduct(List<Product> productleft, List<Product> productright) {
        this.productleft = productleft;
        this.productright = productright;
        notifyDataSetChanged();
    }

    View.OnClickListener img_OnClick_Evt = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (id == R.id.img_product_left || id == R.id.img_product_right
            || id == R.id.constraintLayout_left || id == R.id.constraintLayout_right){
                presenter.getProductDetailByID(Integer.parseInt(v.getTag(v.getId()).toString()));
            }
        }
    };
}