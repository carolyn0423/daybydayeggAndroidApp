package com.hamels.daybydayegg.Product.Adapter;

import androidx.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hamels.daybydayegg.Base.BaseAdapter;
import com.hamels.daybydayegg.Product.Contract.ProductMerchantContract;
import com.hamels.daybydayegg.Product.Holder.ProductMerchantHolder;
import com.hamels.daybydayegg.R;
import com.hamels.daybydayegg.Repository.Model.Merchant;
import com.hamels.daybydayegg.Repository.Model.Product;

import java.util.ArrayList;
import java.util.List;

public class MerchantListAdapter extends BaseAdapter<ProductMerchantHolder> {
    public static final String TAG = MerchantListAdapter.class.getSimpleName();
    private ProductMerchantContract.Presenter presenter;
    private List<Merchant> merchants = new ArrayList<>();
//    private List<Merchant> merchantsleft = new ArrayList<>();
//    private List<Merchant> merchantsright = new ArrayList<>();

    private List<Product> productleft = new ArrayList<>();
    private List<Product> productright = new ArrayList<>();
    private boolean cleanType = false;

    public MerchantListAdapter(ProductMerchantContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public ProductMerchantHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Log.e(TAG,"onCreateViewHolder");
        View viewProduct;
        if(merchants.size() > 0) {
            viewProduct = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_location, viewGroup, false);
        }else{
            viewProduct = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_product, viewGroup, false);
        }
        return new ProductMerchantHolder(viewProduct);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductMerchantHolder productmerchantHolder, final int position) {
        if(cleanType){
            productmerchantHolder.clean();
            cleanType = false;
        } else {
            if(productleft.size() > 0){
                if(productleft.size() == productright.size()){
                    productmerchantHolder.setImg_product_two(productleft.get(position) , productright.get(position));
                }
                else{
                    if(productright.size() == position){
                        productmerchantHolder.setImg_product_one(productleft.get(position));
                    }
                    else{
                        productmerchantHolder.setImg_product_two(productleft.get(position) , productright.get(position));
                    }
                }

                productmerchantHolder.img_product_left.setOnClickListener(img_OnClick_Evt);
                productmerchantHolder.img_product_right.setOnClickListener(img_OnClick_Evt);
            }else{
                if(merchants.size() > 0){
                    productmerchantHolder.setLocationMerchant(merchants.get(position));
                }

                productmerchantHolder.item_location.setOnClickListener(img_OnClick_Evt);
            }
        }
        //productmerchantHolder.img_merchant_right.setOnClickListener(img_OnClick_Evt);
    }

    @Override
    public int getItemCount() {
        if(productleft.size() > 0){
            return productleft.size();
        }
        else{
            return merchants.size();
        }
    }

    public void setMerchant(List<Merchant> merchants) {
        this.cleanType = false;
        this.merchants = merchants;
        notifyDataSetChanged();
    }

    public void setProduct(List<Product> productleft, List<Product> productright) {
        this.cleanType = false;
        this.productleft = productleft;
        this.productright = productright;
        this.merchants.clear();
        notifyDataSetChanged();
    }

    public void clean(){
        this.cleanType = true;
    }

    View.OnClickListener img_OnClick_Evt = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (id == R.id.item_location){
                presenter.getProductMainTypeListByID(Integer.parseInt(v.getTag(v.getId()).toString()));
            }else if (id == R.id.img_product_left || id == R.id.img_product_right){
                presenter.getProductDetailByID(Integer.parseInt(v.getTag(v.getId()).toString()));
            }
        }
    };
}