package com.hamels.daybydayegg.Business.Adapter;

import androidx.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hamels.daybydayegg.Base.BaseAdapter;
import com.hamels.daybydayegg.Business.Contract.BusinessProductMerchantContract;
import com.hamels.daybydayegg.Business.View.BusinessProductMerchantHolder;
import com.hamels.daybydayegg.R;
import com.hamels.daybydayegg.Repository.Model.Merchant;
import com.hamels.daybydayegg.Repository.Model.Product;

import java.util.ArrayList;
import java.util.List;

public class BusinessMerchantListAdapter extends BaseAdapter<BusinessProductMerchantHolder> {
    public static final String TAG = BusinessMerchantListAdapter.class.getSimpleName();
    private BusinessProductMerchantContract.Presenter presenter;
    private List<Merchant> merchantsleft = new ArrayList<>();
    private List<Merchant> merchantsright = new ArrayList<>();

    private List<Product> productleft = new ArrayList<>();
    private List<Product> productright = new ArrayList<>();
    private boolean cleanType = false;

    public BusinessMerchantListAdapter(BusinessProductMerchantContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public BusinessProductMerchantHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Log.e(TAG,"onCreateViewHolder");
        View viewProduct = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_product, viewGroup, false);

            return new BusinessProductMerchantHolder(viewProduct);


    }

    @Override
    public void onBindViewHolder(@NonNull BusinessProductMerchantHolder productmerchantHolderBusiness, final int position) {
        Log.e(TAG,"onBindViewHolder cleanType : " + cleanType);
        if(cleanType){
            productmerchantHolderBusiness.clean();
            cleanType = false;
        }
        else{
            if(productleft.size() > 0){
                if(productleft.size() == productright.size()){
                    productmerchantHolderBusiness.setImg_product_two(productleft.get(position) , productright.get(position));
                }
                else{
                    if(productright.size() == position){
                        productmerchantHolderBusiness.setImg_product_one(productleft.get(position));
                    }
                    else{
                        productmerchantHolderBusiness.setImg_product_two(productleft.get(position) , productright.get(position));
                    }
                }
            }


            else{
                if(merchantsleft.size() == merchantsright.size()){
                    productmerchantHolderBusiness.setImg_merchant_two(merchantsleft.get(position) , merchantsright.get(position));
                }
                else{
                    if(merchantsright.size() == position){
                        productmerchantHolderBusiness.setImg_merchant_one(merchantsleft.get(position));
                    }
                    else{
                        productmerchantHolderBusiness.setImg_merchant_two(merchantsleft.get(position) , merchantsright.get(position));
                    }
                }
            }
        }
        productmerchantHolderBusiness.img_product_left.setOnClickListener(img_OnClick_Evt);
        productmerchantHolderBusiness.img_product_right.setOnClickListener(img_OnClick_Evt);
        productmerchantHolderBusiness.img_merchant_left.setOnClickListener(img_OnClick_Evt);
        productmerchantHolderBusiness.img_merchant_right.setOnClickListener(img_OnClick_Evt);
    }

    @Override
    public int getItemCount() {
        if(productleft.size() > 0){
            return productleft.size();
        }
        else{
            return merchantsleft.size();
        }

    }

    public void setMerchant(List<Merchant> merchantsleft,List<Merchant> merchantsright) {
        this.cleanType = false;
        this.merchantsleft = merchantsleft;
        this.merchantsright = merchantsright;
        this.productright.clear();
        this.productleft.clear();
        notifyDataSetChanged();
    }

    public void setProduct(List<Product> productleft, List<Product> productright) {
        this.cleanType = false;
        this.productleft = productleft;
        this.productright = productright;
        this.merchantsleft.clear();
        this.merchantsright.clear();
        notifyDataSetChanged();
    }

    public void clean(){
        this.cleanType = true;
    }

    View.OnClickListener img_OnClick_Evt = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (id == R.id.img_merchant_left || id == R.id.img_merchant_right){
                presenter.getProductListByID(Integer.parseInt(v.getTag(v.getId()).toString()));
            }else if (id == R.id.img_product_left || id == R.id.img_product_right) {
                presenter.getProductDetailByID(Integer.parseInt(v.getTag(v.getId()).toString()));
            }
        }
    };
}
//switch (v.getTag(R.id.img_product_right).toString()){
//        case "Product":
//        Log.e(TAG,"ProductID : " + v.getId());
//        presenter.getProductDetailByID(v.getId());
//        break;
//        case "Merchant":
//        Log.e(TAG,"MerchantID : " + v.getId());
//        presenter.getProductListByID(v.getId());
//        break;
//        }