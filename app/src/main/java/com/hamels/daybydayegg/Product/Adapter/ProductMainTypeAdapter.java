package com.hamels.daybydayegg.Product.Adapter;

import androidx.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hamels.daybydayegg.Base.BaseAdapter;
import com.hamels.daybydayegg.EOrderApplication;
import com.hamels.daybydayegg.Product.Contract.ProductMainTypeContract;
import com.hamels.daybydayegg.Product.Holder.ProductMainTypeHolder;
import com.hamels.daybydayegg.Repository.Model.ProductMainType;
import com.hamels.daybydayegg.R;

import java.util.ArrayList;
import java.util.List;

public class ProductMainTypeAdapter extends BaseAdapter<ProductMainTypeHolder> {
    public static final String TAG = ProductMainTypeAdapter.class.getSimpleName();
    private ProductMainTypeContract.Presenter presenter;

    private List<ProductMainType> mainTypeleft = new ArrayList<>();
    private List<ProductMainType> mainTyperight = new ArrayList<>();

    String isETicket = "";

    public ProductMainTypeAdapter(ProductMainTypeContract.Presenter presenter, String isETicket) {
        this.presenter = presenter;
        this.isETicket = isETicket;
    }

    @NonNull
    @Override
    public ProductMainTypeHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Log.e(TAG,"onCreateViewHolder");
        View viewProduct= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_product_main_type, viewGroup, false);
        return new ProductMainTypeHolder(viewProduct);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductMainTypeHolder productMainTypeHolder, final int position) {
        Log.e(TAG,"onBindViewHolder");

        String sImageUrl = presenter.getApiUrl();

        sImageUrl = presenter.getUserLogin() ? EOrderApplication.sApiUrl : sImageUrl;

        if(mainTypeleft.size() == mainTyperight.size()){
            productMainTypeHolder.setImg_ProductMainType_two(mainTypeleft.get(position) , mainTyperight.get(position), sImageUrl, isETicket);
        } else{
            if(mainTyperight.size() == position){
                productMainTypeHolder.setImg_ProductMainType_one(mainTypeleft.get(position), sImageUrl, isETicket);
            }
            else{
                productMainTypeHolder.setImg_ProductMainType_two(mainTypeleft.get(position) , mainTyperight.get(position), sImageUrl, isETicket);
            }
        }

        productMainTypeHolder.img_productMainType_left.setOnClickListener(img_OnClick_Evt);
        productMainTypeHolder.img_productMainType_right.setOnClickListener(img_OnClick_Evt);

        productMainTypeHolder.constraintLayout_left.setOnClickListener(img_OnClick_Evt);
        productMainTypeHolder.constraintLayout_right.setOnClickListener(img_OnClick_Evt);
    }

    @Override
    public int getItemCount() {
        if(mainTypeleft.size() > 0){
            return mainTypeleft.size();
        } else {
            return mainTypeleft.size();
        }
    }

    public void setProductMainType(List<ProductMainType> mainTypeleft, List<ProductMainType> mainTyperight) {
        this.mainTypeleft = mainTypeleft;
        this.mainTyperight = mainTyperight;

        notifyDataSetChanged();
    }

    View.OnClickListener img_OnClick_Evt = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (id == R.id.img_productMainType_left || id == R.id.img_productMainType_right
                 || id == R.id.constraintLayout_left || id == R.id.constraintLayout_right){
                presenter.getProductList(Integer.parseInt(v.getTag(v.getId()).toString()));
            }
        }
    };
}